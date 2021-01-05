package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GestoreCategoriaMerceologica extends GestoreBase implements ICRUD {
    @Override
    public CategoriaMerceologica getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        CategoriaMerceologica c = new CategoriaMerceologica();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM CategorieMerceologiche WHERE categoriaId = ?"); // creo sempre uno statement sulla
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
