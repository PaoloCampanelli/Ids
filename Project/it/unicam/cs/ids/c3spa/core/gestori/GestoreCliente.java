package it.unicam.cs.ids.c3spa.core.gestori;
import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.*;;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreCliente extends GestoreBase implements ICRUD {

    private Cliente c = new Cliente();

    @Override
    public Cliente getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        String sql;
       // Cliente c = new Cliente();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM clienti WHERE clienteId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
            st.setInt(1,id);
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
    public List<Cliente> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Cliente> c = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM clienti where isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Cliente a = new Cliente();
                a.mapData(rs);
                c.add(a);
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
    public Cliente save(Object entity) throws SQLException {
        if(entity instanceof Cliente) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            Cliente c = (Cliente) entity;

            try {

                if (c.id == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.clienti (denominazione, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
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
                }
                else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.clienti SET denominazione = ?, `indirizzo.citta` = ?, `indirizzo.numero` = ?, `indirizzo.cap` = ?, `indirizzo.via` = ?, `indirizzo.provincia` = ?, telefono = ?, eMail = ?, password = ? WHERE clienteId = ?"); // creo sempre uno statement sulla
                    st.setString(1, c.denominazione);
                    st.setString(2, c.indirizzo.citta);
                    st.setString(3, c.indirizzo.numero);
                    st.setString(4, c.indirizzo.cap);
                    st.setString(5, c.indirizzo.via);
                    st.setString(6, c.indirizzo.provincia);
                    st.setString(7, c.telefono);
                    st.setString(8, c.eMail);
                    st.setString(9, new Servizi().encrypt(c.password));
                    st.setInt(10,c.id);

                    st.executeUpdate(); // faccio la query su uno statement
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

        System.out.println("errore: salvataggio non riuscito" );
        return null;

    }

    @Override
    public void delete(int id) throws SQLException {

        Statement st;
        String sql;
        Connection conn = ApriConnessione();

        try {

            sql = "UPDATE `progetto_ids`.`clienti` SET `isCancellato` = '1' WHERE (`clienteId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch


    }

    public List<Cliente> getByString(String colonna, String stringaDaRicercare) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        List<Cliente> lc = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM clienti WHERE (`"+colonna+"` like '%"+stringaDaRicercare+"%');";
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

    public List<Cliente> getByEMail(String eMail) throws SQLException {
        String colonna= "eMail";
        return getByString(colonna, eMail);
    }

    public List<Cliente> getByDenominazione(String denominazione) throws SQLException {
        String colonna= "denominazione";
        return getByString(colonna, denominazione);
    }
}
