package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;
import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GestorePacco extends GestoreBase implements ICRUD {
    @Override
    public Pacco getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        Pacco p = new Pacco();
        GestoreStatoPacco gsp = new GestoreStatoPacco();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM pacchi WHERE paccoId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
            st.setInt(1,id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                p.mapData(rs);
            }
            st.close();
            p.corriere = new GestoreCorriere().getById(p.corriere.id);
            p.mittente = new GestoreNegozio().getById(p.mittente.id);
            p.destinatario = new GestoreCliente().getById(p.destinatario.id);

            //Popolo le categorie collegate al negozio
            st = conn.prepareStatement("SELECT * FROM progetto_ids.pacco_statipacco WHERE paccoId = ?"); // creo sempre uno statement sulla
            st.setInt(1, p.id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                p.statiPacco.add(gsp.getById(rs.getInt(2)));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return p;

    }

    @Override
    public List<Pacco> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM pacchi where isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Pacco p = new Pacco();
                p.mapData(rs);
                p.corriere = new GestoreCorriere().getById(p.corriere.id);
                p.mittente = new GestoreNegozio().getById(p.mittente.id);
                p.destinatario = new GestoreCliente().getById(p.destinatario.id);
                lp.add(p);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lp;
    }

    @Override
    public Pacco save(Object entity) throws SQLException {
        if (entity instanceof Pacco) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            Pacco p = (Pacco) entity;

            try {

                if (p.id == 0) { // è un inserimento

                    java.sql.Date dataPreparazione = Servizi.dataUtilToSql(p.dataPreparazione);
                    java.sql.Date dataRichiesta = Servizi.dataUtilToSql(p.dataConsegnaRichiesta);
                    StatoPacco sp = ((Pacco) entity).statiPacco.get(0);

                    st = conn.prepareStatement("INSERT INTO progetto_ids.pacchi (`destinatario`, `mittente`,`corriere`,`indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, dataPreparazione, dataConsegnaRichiesta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setInt(1, p.destinatario.id);
                    st.setInt(2, p.mittente.id);
                    st.setNull(3, Types.INTEGER);
                    st.setString(4, p.indirizzo.citta);
                    st.setString(5, p.indirizzo.numero);
                    st.setString(6, p.indirizzo.cap);
                    st.setString(7, p.indirizzo.via);
                    st.setString(8, p.indirizzo.provincia);
                    st.setDate(9, dataPreparazione);
                    st.setDate(10, dataRichiesta);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    new GestoreStatoPacco().save(sp);

                    if (rs.next()) {
                        p.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    java.sql.Date dataPreparazione = Servizi.dataUtilToSql(p.dataPreparazione);
                    java.sql.Date dataRichiesta = Servizi.dataUtilToSql(p.dataConsegnaRichiesta);

                    st = conn.prepareStatement("UPDATE progetto_ids.pacchi SET `destinatario` = ?, `mittente` = ?, `corriere` = ?,`indirizzo.citta` = ?, `indirizzo.numero` = ?, `indirizzo.cap` = ?, `indirizzo.via` = ?, `indirizzo.provincia` = ?, dataPreparazione = ?, dataConsegnaRichiesta = ? WHERE paccoId = ?"); // creo sempre uno statement sulla
                    st.setInt(1, p.destinatario.id);
                    st.setInt(2, p.mittente.id);
                    st.setInt(3, p.corriere.id);
                    st.setString(4, p.indirizzo.citta);
                    st.setString(5, p.indirizzo.numero);
                    st.setString(6, p.indirizzo.cap);
                    st.setString(7, p.indirizzo.via);
                    st.setString(8, p.indirizzo.provincia);
                    st.setDate(9, dataPreparazione);
                    st.setDate(10, dataRichiesta);
                    st.setInt(11, p.id);

                    st.executeUpdate(); // faccio la query su uno statement

                }


                //Gestire lo stato del pacco
                //Elimino tutti gli stati salvati per il pacco
                st = conn.prepareStatement("DELETE FROM progetto_ids.pacco_statipacco WHERE paccoId = ?");
                st.setInt(1,p.id);

                st.executeUpdate();

                //Inserisco le categorie passate nell'oggetto
                for (StatoPacco stp:p.statiPacco
                ) {
                    new GestoreStatoPacco().save(stp);
                    st = conn.prepareStatement("INSERT INTO progetto_ids.pacco_statipacco (paccoId, statoId) VALUES (?,?)");
                    st.setInt(1,p.id);
                    st.setInt(2,stp.id);


                    st.executeUpdate();
                }
                st.close();

                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return p;
            } catch (SQLException e) {
                System.out.println("errore:" + e.getMessage());
                return null;
            } // fine try-catch
        }

        System.out.println("errore: salvataggio non riuscito");
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

        Statement st;
        String sql;
        Connection conn = ApriConnessione();

        try {

            sql = "UPDATE `progetto_ids`.`categoriemerceologiche` SET `isCancellato` = '1' WHERE (`paccoId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch
    }

    public List<Pacco> getByDestinatario(String denominazioneCliente) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT distinct pacchi.paccoId, `destinatario`, `mittente`, `corriere`, `dataPreparazione`, `dataConsegnaRichiesta`  FROM pacchi\n" +
                    "                    INNER JOIN clienti ON pacchi.destinatario = clienti.clienteId\n" +
                    "                    WHERE denominazione LIKE '%"+denominazioneCliente+"%';");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                lp.add(new Pacco().mapData(rs));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lp;

    }

    public List<Pacco> getByMittente(String denominazioneNegozio) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT distinct pacchi.paccoId, `destinatario`, `mittente`, `corriere`, `dataPreparazione`, `dataConsegnaRichiesta`  FROM pacchi\n" +
                    "                    INNER JOIN negozi ON pacchi.mittente = negozi.negozioId\n" +
                    "                    WHERE denominazione LIKE '%"+denominazioneNegozio+"%';");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                lp.add(new Pacco().mapData(rs));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lp;

    }

    public List<Pacco> getByCorriere(Corriere corriere) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("select  pacchi.paccoId, destinatario, mittente, corriere, pacchi.`indirizzo.citta`, pacchi.`indirizzo.numero`, pacchi.`indirizzo.cap`, pacchi.`indirizzo.via`, pacchi.`indirizzo.provincia`, dataPreparazione, dataConsegnaRichiesta, denominazione, eMail, telefono, count(pacco_statipacco.statoId) as stati\n" +
                    "from pacchi\n" +
                    "                    INNER JOIN corrieri ON corrieri.corriereId = pacchi.corriere\n" +
                    "                    INNER JOIN pacco_statipacco ON pacco_statipacco.paccoid = pacchi.paccoId\n" +
                    "                    INNER JOIN statipacchi ON pacco_statipacco.statoid = statipacchi.statoId\n" +
                    "where pacchi.corriere = " + corriere.id + "\n" +
                    "group by pacco_statipacco.paccoId\n" +
                    "having count(stati)=2;");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Pacco p = new Pacco();
                p.mapData(rs);
                p.corriere = new GestoreCorriere().getById(p.corriere.id);
                p.mittente = new GestoreNegozio().getById(p.mittente.id);
                p.destinatario = new GestoreCliente().getById(p.destinatario.id);
                lp.add(p);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return lp;
    }

    public List<Pacco> getPacchiSenzaCorriere() throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT * FROM progetto_ids.pacchi where corriere is null;");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                lp.add(new GestorePacco().getById(new Pacco().mapData(rs).id));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return lp;

    }

    public Boolean assegnaPacco(Pacco p, Corriere c) throws SQLException {

        try {
            p.setCorriere(c);
            new GestorePacco().save(p);
            new GestoreCorriere().save(c);
            return true;
            }
        catch (Exception e){
            System.out.println("errore:" + e.getMessage());
        }

        return false;
    }

    public Boolean consegnaPacco(Pacco p, Corriere c) throws SQLException {

        try {
            p.setConsegnato();
            new GestorePacco().save(p);
            new GestoreCorriere().save(c);
            return true;
        }
        catch (Exception e){
            System.out.println("errore:" + e.getMessage());
        }

        return false;
    }

    public List<Pacco> storicoByCorriere ( Corriere corriere) throws SQLException {

        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("select  pacchi.paccoId, destinatario, mittente, corriere, pacchi.`indirizzo.citta`, pacchi.`indirizzo.numero`, pacchi.`indirizzo.cap`, pacchi.`indirizzo.via`, pacchi.`indirizzo.provincia`, dataPreparazione, dataConsegnaRichiesta, denominazione, eMail, telefono, count(pacco_statipacco.statoId) as stati\n" +
                    "from pacchi\n" +
                    "                    INNER JOIN corrieri ON corrieri.corriereId = pacchi.corriere\n" +
                    "                    INNER JOIN pacco_statipacco ON pacco_statipacco.paccoid = pacchi.paccoId\n" +
                    "                    INNER JOIN statipacchi ON pacco_statipacco.statoid = statipacchi.statoId\n" +
                    "where pacchi.corriere = " + corriere.id + "\n" +
                    "group by pacco_statipacco.paccoId\n" +
                    "having count(stati)=3;");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Pacco p = new Pacco();
                p.mapData(rs);
                p.corriere = new GestoreCorriere().getById(p.corriere.id);
                p.mittente = new GestoreNegozio().getById(p.mittente.id);
                p.destinatario = new GestoreCliente().getById(p.destinatario.id);
                lp.add(p);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return lp;
    }

    public List<Pacco> storicoByCliente (Cliente cliente) throws SQLException {

        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("select  pacchi.paccoId, destinatario, mittente, corriere, pacchi.`indirizzo.citta`, pacchi.`indirizzo.numero`, pacchi.`indirizzo.cap`, pacchi.`indirizzo.via`, pacchi.`indirizzo.provincia`, dataPreparazione, dataConsegnaRichiesta, denominazione, eMail, telefono, count(pacco_statipacco.statoId) as stati\n" +
                    "from pacchi\n" +
                    "                    INNER JOIN corrieri ON corrieri.corriereId = pacchi.corriere\n" +
                    "                    INNER JOIN pacco_statipacco ON pacco_statipacco.paccoid = pacchi.paccoId\n" +
                    "                    INNER JOIN statipacchi ON pacco_statipacco.statoid = statipacchi.statoId\n" +
                    "where pacchi.destinatario = " + cliente.id + "\n" +
                    "group by pacco_statipacco.paccoId\n" +
                    "having count(stati)=3;");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Pacco p = new Pacco();
                p.mapData(rs);
                p.corriere = new GestoreCorriere().getById(p.corriere.id);
                p.mittente = new GestoreNegozio().getById(p.mittente.id);
                p.destinatario = new GestoreCliente().getById(p.destinatario.id);
                lp.add(p);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return lp;
    }

    public List<Pacco> storicoByNegozio (Negozio negozio) throws SQLException {

        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("select  pacchi.paccoId, destinatario, mittente, corriere, pacchi.`indirizzo.citta`, pacchi.`indirizzo.numero`, pacchi.`indirizzo.cap`, pacchi.`indirizzo.via`, pacchi.`indirizzo.provincia`, dataPreparazione, dataConsegnaRichiesta, denominazione, eMail, telefono, count(pacco_statipacco.statoId) as stati\n" +
                    "from pacchi\n" +
                    "                    INNER JOIN corrieri ON corrieri.corriereId = pacchi.corriere\n" +
                    "                    INNER JOIN pacco_statipacco ON pacco_statipacco.paccoid = pacchi.paccoId\n" +
                    "                    INNER JOIN statipacchi ON pacco_statipacco.statoid = statipacchi.statoId\n" +
                    "where pacchi.mittente = " + negozio.id + "\n" +
                    "group by pacco_statipacco.paccoId\n" +
                    "having count(stati)=3;");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Pacco p = new Pacco();
                p.mapData(rs);
                p.corriere = new GestoreCorriere().getById(p.corriere.id);
                p.mittente = new GestoreNegozio().getById(p.mittente.id);
                p.destinatario = new GestoreCliente().getById(p.destinatario.id);
                lp.add(p);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return lp;
    }
}