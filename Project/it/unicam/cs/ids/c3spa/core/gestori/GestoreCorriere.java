package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreCorriere  extends GestoreBase implements ICRUD {
    @Override
    public Corriere getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        Corriere c = new Corriere();
        GestorePacco gp = new GestorePacco();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT * FROM progetto_ids.corrieri WHERE corriereId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
            st.setInt(1, id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                c.mapData(rs);
            }
            st.close();

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch
        ChiudiConnessione(conn);
        return c;
    }

    @Override
    public List<Corriere> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Corriere> lc = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM corrieri where isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Corriere c = new Corriere();
                c.mapData(rs);
                lc.add(c);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lc;
    }

    @Override
    public Corriere save(Object entity) throws SQLException {
        if (entity instanceof Corriere) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            Corriere c = (Corriere) entity;

            try {

                if (c.id == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.corrieri (denominazione, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, c.denominazione);
                    st.setString(2, c.indirizzo.citta);
                    st.setString(3, c.indirizzo.numero);
                    st.setString(4, c.indirizzo.cap);
                    st.setString(5, c.indirizzo.via);
                    st.setString(6, c.indirizzo.provincia);
                    st.setString(7, c.telefono);
                    st.setString(8, c.eMail);
                    st.setString(9, new Servizi().encrypt(c.password));

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        c.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.corrieri SET denominazione = ?, `indirizzo.citta` = ?, `indirizzo.numero` = ?, `indirizzo.cap` = ?, `indirizzo.via` = ?, `indirizzo.provincia` = ?, telefono = ?, eMail = ?, password = ? WHERE corriereId = ?"); // creo sempre uno statement sulla
                    st.setString(1, c.denominazione);
                    st.setString(2, c.indirizzo.citta);
                    st.setString(3, c.indirizzo.numero);
                    st.setString(4, c.indirizzo.cap);
                    st.setString(5, c.indirizzo.via);
                    st.setString(6, c.indirizzo.provincia);
                    st.setString(7, c.telefono);
                    st.setString(8, c.eMail);
                    st.setString(9, c.password);
                    st.setInt(10, c.id);

                    st.executeUpdate(); // faccio la query su uno statement
                    st.close();
                }

                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return c;
            } catch (SQLException e) {
                System.out.println("errore:" + e.getMessage());
                return null;
            } // fine try-catch
            catch (Exception e) {
                e.printStackTrace();
            }
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

            sql = "UPDATE `progetto_ids`.`corrieri` SET `isCancellato` = '1' WHERE (`corriereId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }

    public Corriere cambiaPassword(Corriere corriere) throws Exception {

        PreparedStatement st;
        ResultSet rs;
        Connection conn = ApriConnessione();
        st = conn.prepareStatement("UPDATE progetto_ids.corrieri SET password = ? WHERE corriereId = ?"); // creo sempre uno statement sulla
        st.setString(1, new Servizi().encrypt(corriere.password));
        st.setInt(2, corriere.id);
        st.executeUpdate(); // faccio la query su uno statement

        GestoreBase.ChiudiConnessione(conn); // chiusura connessione
        return corriere;
    }

    private List<Corriere> getByString(String colonna, String stringaDaRicercare) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        List<Corriere> lc = new ArrayList<>();
        Connection conn = ApriConnessione();
        Corriere c = new Corriere();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM corrieri WHERE (`"+colonna+"` like '%"+stringaDaRicercare+"%');";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                lc.add(c.mapData(rs));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return lc;
    }

    public Corriere getByEMail(String eMail) throws SQLException {
        String colonna= "eMail";
        return getByString(colonna, eMail).stream().findFirst().orElse(null);
    }

}
