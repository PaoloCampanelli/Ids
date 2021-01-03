package it.unicam.cs.ids.c3spa.core.gestori;
import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.*;
import jdk.jshell.spi.ExecutionControl;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestoreCliente extends GestoreBase implements ICRUD {
    @Override
    public Cliente getById(int id) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        Cliente c = new Cliente();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM clienti WHERE (`clienteId` = "+id+");";
            rs = st.executeQuery(sql); // faccio la query su uno statement
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
            sql = "SELECT * FROM clienti;";
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
        //verifica che l'object è castabile a Cliente

        Cliente c = (Cliente) entity;
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    public List<Cliente> getByDenominazione(String nome) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        List<Cliente> lc = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM clienti WHERE (`denominazione` = "+nome+");";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                lc.add(new Cliente().mapData(rs));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lc;
    }
}
