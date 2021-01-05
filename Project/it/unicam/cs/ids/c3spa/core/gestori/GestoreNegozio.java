package it.unicam.cs.ids.c3spa.core.gestori;
import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.*;;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        if(entity instanceof Negozio) {

            Statement st;
            String sql;
            Connection conn = ApriConnessione();
            Negozio n = (Negozio) entity;

            try {

                sql = "INSERT INTO `progetto_ids`.`negozi` (`denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES " +
                        "('" + n.denominazione + "','" + n.indirizzo.citta + "','" + n.indirizzo.numero + "','" + n.indirizzo.cap + "','" + n.indirizzo.via + "','" + n.indirizzo.provincia + "','" + n.telefono + "','" + n.eMail + "','" + n.password
                        + "')";

                st = conn.createStatement(); // creo sempre uno statement sulla
                st.execute(sql); // faccio la query su uno statement
                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return n;
            } catch (SQLException e) {
                System.out.println("errore:" + e.getMessage());
                return null;
            } // fine try-catch
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

            sql = "DELETE FROM `progetto_ids`.`negozi` WHERE (`negozioId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }
}
