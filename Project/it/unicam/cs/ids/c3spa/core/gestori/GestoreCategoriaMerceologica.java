package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreCategoriaMerceologica extends GestoreBase implements ICRUD {
    @Override
    public CategoriaMerceologica getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        CategoriaMerceologica c = new CategoriaMerceologica();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM CategorieMerceologiche WHERE categoriaId = ? AND  isCancellato = 0"); // creo sempre uno statement sulla
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
    public List<CategoriaMerceologica> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<CategoriaMerceologica> c = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM progetto_ids.categoriemerceologiche where isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                CategoriaMerceologica a = new CategoriaMerceologica();
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
    public CategoriaMerceologica save(Object entity) throws SQLException {
        if(entity instanceof CategoriaMerceologica) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            CategoriaMerceologica c = (CategoriaMerceologica) entity;

            try {

                if (c.idCategoria == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.categoriemerceologiche (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, c.nome);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        c.idCategoria = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                }
                else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.categoriemerceologiche SET nome = ? WHERE categoriaId = ?"); // creo sempre uno statement sulla
                    st.setString(1, c.nome);
                    st.setInt(2,c.idCategoria);

                    st.executeUpdate(); // faccio la query su uno statement
                }
                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return c;
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

            sql = "UPDATE `progetto_ids`.`categoriemerceologiche` SET `isCancellato` = '1' WHERE (`categoriaId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }
}
