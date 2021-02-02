package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GestoreSconto extends GestoreBase implements ICRUD {
    @Override
    public Sconto getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        String sql;
        Sconto s = new Sconto();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM sconti WHERE scontoId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
            st.setInt(1,id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                s.mapData(rs);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return s;

    }

    @Override
    public List<Sconto> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Sconto> s = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM sconti where isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Sconto a= new Sconto();
                a.mapData(rs);
                s.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return s;
    }


    @Override
    public Sconto save(Object entity) throws SQLException {
        if (entity instanceof Sconto) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            Sconto s = (Sconto) entity;

            try {

                if (s.id == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.sconti (tipo, dataInizio, dataFine, `negozio.id`, `categoria.id`) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, s.tipo);
                    st.setDate(2,(java.sql.Date)s.dataInizio);
                    st.setDate(3, (java.sql.Date)s.dataInizio);
                    st.setInt(4, s.negozio.id);
                    st.setInt(5, s.categoriaMerceologica.idCategoria);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    st.close();
                    if (rs.next()) {
                        s.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.sconti SET tipo = ?, dataInizio = ?, dataFine = ?, `negozio.id` = ?, `categoria.id` = ? WHERE scontoId = ?"); // creo sempre uno statement sulla
                    st.setString(1, s.tipo);
                    st.setDate(2,(java.sql.Date)s.dataInizio);
                    st.setDate(3, (java.sql.Date)s.dataInizio);
                    st.setInt(4, s.negozio.id);
                    st.setInt(5, s.categoriaMerceologica.idCategoria);
                    st.setInt(6, s.id);

                    st.executeUpdate(); // faccio la query su uno statement
                    st.close();
                }

                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return s;
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

            sql = "UPDATE `progetto_ids`.`sconti` SET `isCancellato` = '1' WHERE (`scontoId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch
    }

    public List<Sconto> getScontiAttivi() throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Sconto> s = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM sconti \n" +
                    "where isCancellato = 0 \n" +
                    "AND current_date() <= dataFine\n" +
                    "AND   dataInizio <= current_date()";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Sconto a= new Sconto();
                a.mapData(rs);
                s.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return s;
    }

    public List<Sconto> getByNegozio(Negozio negozio) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Sconto> ls = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM sconti where isCancellato = 0 and negozioId ="+negozio.id+" ;");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Sconto s = new Sconto();
                s.mapData(rs);
                s = new GestoreSconto().getById(s.id);
                s.negozio = new GestoreNegozio().getById(s.negozio.id);
                s.categoriaMerceologica = new GestoreCategoriaMerceologica().getById(s.categoriaMerceologica.idCategoria);
                ls.add(s);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return ls;

    }
}
