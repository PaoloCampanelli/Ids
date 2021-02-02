package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.Pubblicita;
import it.unicam.cs.ids.c3spa.core.Sconto;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorePubblicita extends GestoreBase implements ICRUD {
    @Override
    public Object getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        String sql;
        Pubblicita p = new Pubblicita();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM pubblicita WHERE pubblicitaId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
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
    public List getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Pubblicita> lp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM pubblicita where isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Pubblicita a= new Pubblicita();
                a.mapData(rs);
                lp.add(a);
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
    public Object save(Object entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
