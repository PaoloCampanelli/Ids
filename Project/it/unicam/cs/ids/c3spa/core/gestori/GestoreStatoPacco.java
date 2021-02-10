package it.unicam.cs.ids.c3spa.core.gestori;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.StatoPacco;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreStatoPacco extends GestoreBase implements ICRUD {
    @Override
    public StatoPacco getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        StatoPacco sp = new StatoPacco();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM statipacchi WHERE statoId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
            st.setInt(1,id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                sp.mapData(rs);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return sp;
    }

    @Override
    public List<StatoPacco> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        ArrayList<StatoPacco> lsp = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            rs = st.executeQuery("SELECT * FROM statipacchi where isCancellato = 0;"); // faccio la query su uno statement
            while (rs.next() == true) {
                StatoPacco a = new StatoPacco();
                a.mapData(rs);
                lsp.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lsp;
    }

    @Override
    public StatoPacco save(Object entity) throws SQLException {
        if(entity instanceof StatoPacco) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            StatoPacco sp = (StatoPacco) entity;

            try {

                if (sp.id == 0) { // è un inserimento
                    java.sql.Date dataSQL = Servizi.dataUtilToSql(sp.dataStatoPacco);


                    st = conn.prepareStatement("INSERT INTO progetto_ids.statipacchi (stato, data) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, sp.stato.toString());
                    st.setDate(2, dataSQL);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        sp.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                }
                else //é una modifica
                {

                    java.sql.Date dataSQL = Servizi.dataUtilToSql(sp.dataStatoPacco);

                    st = conn.prepareStatement("UPDATE progetto_ids.statipacchi SET stato = ?, data = ? WHERE statoId = ?"); // creo sempre uno statement sulla
                    st.setString(1, sp.stato.toString());
                    st.setDate(2,dataSQL);
                    st.setInt(3,sp.id);

                    st.executeUpdate(); // faccio la query su uno statement
                }
                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return sp;
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

            sql = "UPDATE `progetto_ids`.`statipacchi` SET `isCancellato` = '1' WHERE (`paccoId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }
}
