package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorePacco extends GestoreBase implements ICRUD {
    @Override
    public Pacco getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        String sql;
        Pacco p = new Pacco();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM pacchi WHERE paccoId = ?"); // creo sempre uno statement sulla
            st.setInt(1,id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                p.mapData(rs);
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
        ArrayList<Pacco> p = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM pacchi;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Pacco a = new Pacco();
                a.mapData(rs);
                p.add(a);
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

                    st = conn.prepareStatement("INSERT INTO progetto_ids.pacchi (`destinatario`, `mittente`,`corriere`, dataPreparazione, dataConsegnaRichiesta) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setInt(1, p.destinatario.id);
                    st.setInt(2, p.mittente.id);
                    st.setNull(3, Types.INTEGER);
                    st.setDate(4, dataPreparazione);
                    st.setDate(5, dataRichiesta);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        p.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    java.sql.Date dataPreparazione = Servizi.dataUtilToSql(p.dataPreparazione);
                    java.sql.Date dataRichiesta = Servizi.dataUtilToSql(p.dataConsegnaRichiesta);

                    st = conn.prepareStatement("UPDATE progetto_ids.pacchi SET `destinatario` = ?, `mittente` = ?, `corriere` = ?, dataPreparazione = ?, dataConsegnaRichiesta = ? WHERE paccoId = ?"); // creo sempre uno statement sulla
                    st.setInt(1, p.destinatario.id);
                    st.setInt(2, p.mittente.id);
                    st.setInt(3, p.corriere.id);
                    st.setDate(4, dataPreparazione);
                    st.setDate(5, dataRichiesta);
                    st.setInt(6, p.id);

                    st.executeUpdate(); // faccio la query su uno statement

                }

                //Gestire lo stato del pacco
                //Elimino tutti gli stati salvati per il pacco
                st = conn.prepareStatement("DELETE FROM progetto_ids.pacco_statipacco WHERE paccoId = ?");
                st.setInt(1,p.id);

                st.executeUpdate();

                //Inserisco le categorie passate nell'oggetto
                for (StatoPacco sp:p.statiPacco
                ) {
                    st = conn.prepareStatement("INSERT INTO progetto_ids.pacco_statipacco (paccoId, statoId) VALUES (?,?)");
                    st.setInt(1,p.id);
                    st.setInt(2,sp.id);

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

            sql = "DELETE FROM `progetto_ids`.`pacchi` WHERE (`paccoId` = '"+id+"');";

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

    public List<Pacco> getByCorriere(String corriere) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Pacco> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT distinct pacchi.paccoId, destinatario, mittente, corriere, dataPreparazione, dataConsegnaRichiesta, denominazione, eMail, telefono FROM pacchi \n" +
                    "INNER JOIN corriere_pacchi ON pacchi.paccoId= corriere_pacchi.paccoId\n" +
                    "INNER JOIN corrieri ON corriere_pacchi.corriereId = corrieri.corriereId\n" +
                    "WHERE corrieri.denominazione LIKE '%" + corriere + "%';");
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
}