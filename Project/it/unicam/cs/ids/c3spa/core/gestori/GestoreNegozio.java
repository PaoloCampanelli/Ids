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
    public List getAll() throws SQLException {
        return null;
    }

    @Override
    public Object save(Object entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
