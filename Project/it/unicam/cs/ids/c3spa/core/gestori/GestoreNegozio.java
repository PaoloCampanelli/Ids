package it.unicam.cs.ids.c3spa.core.gestori;
import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.*;;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreNegozio extends GestoreBase implements ICRUD{
    @Override
    public Object getById(int id) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        Negozio n = new Negozio();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM negozi WHERE (`negozioId` = "+id+");";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                n.mapData(rs);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return n;
    }

    @Override
    public List<Negozio> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Negozio> n = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM negozi;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio a = new Negozio();
                a.mapData(rs);
                n.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return n;
    }

    @Override
    public Negozio save(Object entity) throws SQLException {
        if (entity instanceof Negozio) {

            PreparedStatement st;
            ResultSet rs;
            String sql;
            Connection conn = ApriConnessione();
            Negozio n = (Negozio) entity;

            try {

                if (n.id == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.negozi (denominazione, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, n.denominazione);
                    st.setString(2, n.indirizzo.citta);
                    st.setString(3, n.indirizzo.numero);
                    st.setString(4, n.indirizzo.cap);
                    st.setString(5, n.indirizzo.via);
                    st.setString(6, n.indirizzo.provincia);
                    st.setString(7, n.telefono);
                    st.setString(8, n.eMail);
                    st.setString(9, n.password);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        n.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.negozi SET denominazione = ?, `indirizzo.citta` = ?, `indirizzo.numero` = ?, `indirizzo.cap` = ?, `indirizzo.via` = ?, `indirizzo.provincia` = ?, telefono = ?, eMail = ?, password = ? WHERE clienteId = ?"); // creo sempre uno statement sulla
                    st.setString(1, n.denominazione);
                    st.setString(2, n.indirizzo.citta);
                    st.setString(3, n.indirizzo.numero);
                    st.setString(4, n.indirizzo.cap);
                    st.setString(5, n.indirizzo.via);
                    st.setString(6, n.indirizzo.provincia);
                    st.setString(7, n.telefono);
                    st.setString(8, n.eMail);
                    st.setString(9, n.password);
                    st.setInt(10, n.id);

                    st.executeUpdate(); // faccio la query su uno statement
                }
                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return n;
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

            sql = "DELETE FROM `progetto_ids`.`negozi` WHERE (`negozioId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }
}
