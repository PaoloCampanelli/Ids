package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
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
        if (entity instanceof Pubblicita) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            Pubblicita p = (Pubblicita) entity;

            try {

                if (p.id == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.pubblicita (pubblicitaId, dataInizio, dataFine, negozioId) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setInt(1, p.id);
                    st.setDate(2,(java.sql.Date)p.dataInizio);
                    st.setDate(3, (java.sql.Date)p.dataFine);
                    st.setInt(4, p.negozio.id);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        p.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.pubblicita SET dataInizio = ?, dataFine = ?, `negozioId` = ? WHERE pubblicitaId = ?"); // creo sempre uno statement sulla
                    st.setDate(1,(java.sql.Date)p.dataInizio);
                    st.setDate(2, (java.sql.Date)p.dataFine);
                    st.setInt(3, p.negozio.id);
                    st.setInt(4, p.id);

                    st.executeUpdate(); // faccio la query su uno statement

                }

                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                st.close();
                return p;
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

            sql = "UPDATE `progetto_ids`.`pubblicita` SET `isCancellato` = '1' WHERE (`pubblicitaId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch
    }

    public List<Pubblicita> getPubblicitaAttive() throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Pubblicita> p = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM pubblicita \n" +
                    "where isCancellato = 0 \n" +
                    "AND current_date() <= dataFine\n" +
                    "AND   dataInizio <= current_date()";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Pubblicita a = new Pubblicita();
                a.mapData(rs);
                a.negozio = new GestoreNegozio().getById(a.id);
                p.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return p;
    }

    public List<Pubblicita> getPubblicitaByNegozio(Negozio negozio) throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Pubblicita> p = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM pubblicita \n" +
                    "where isCancellato = 0 \n" +
                    "AND   negozioId = "+negozio.id;
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Pubblicita a = new Pubblicita();
                a.mapData(rs);
                a.negozio = new GestoreNegozio().getById(a.id);
                p.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return p;
    }

    public List<Pubblicita> getPubblicitaAttivaByNegozio(Negozio negozio) throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Pubblicita> p = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM pubblicita \n" +
                    "where isCancellato = 0 \n" +
                    "AND current_date() <= dataFine\n" +
                    "AND negozioId = "+negozio.id+
                    " AND   dataInizio <= current_date()";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Pubblicita a = new Pubblicita();
                a.mapData(rs);
                a.negozio = new GestoreNegozio().getById(a.id);
                p.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return p;
    }

    public List<Negozio> getNegoziConPubblicitaAttiva() throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT distinct negozi.negozioId ,denominazione, token, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password \n" +
                    "FROM pubblicita inner join negozi \n" +
                    "on pubblicita.negozioId = negozi.negozioId\n" +
                    "where pubblicita.isCancellato = 0\n" +
                    "AND current_date() <= dataFine\n" +
                    "AND   dataInizio <= current_date()";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio a = new Negozio();
                a.mapData(rs);
                a = new GestoreNegozio().getById(a.id);
                ln.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return ln;
    }

    public List<Negozio> getNegoziConPubblicitaAttivaByString(String colonna, String stringaDaRicercare) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();
        GestoreCategoriaMerceologica gcm = new GestoreCategoriaMerceologica();
        Negozio n = new Negozio();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT distinct negozi.negozioId ,denominazione, token, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password \n" +
                    "FROM pubblicita inner join negozi \n" +
                    "on pubblicita.negozioId = negozi.negozioId\n" +
                    "where pubblicita.isCancellato = 0\n" +
                    "AND current_date() <= dataFine\n" +
                    "AND   dataInizio <= current_date()"+
                    "AND ("+colonna+" like \""+stringaDaRicercare+"\");";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                ln.add(n.mapData(rs));
            }
            //Popolo le categorie collegate al negozio

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM progetto_ids.negozio_categoriemerceologiche WHERE negozioId = ?"); // creo sempre uno statement sulla
            ps.setInt(1, n.id);
            rs = ps.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                n.categorie.add(gcm.getById(rs.getInt(2)));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return ln;
    }

    public List<Negozio> getNegoziConPubblicitaAttivaByCategoria(String categoria) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT distinct negozi.negozioId, `denominazione`, token,`indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`,`indirizzo.provincia`, telefono, eMail, password, categoriemerceologiche.categoriaId, nome \n" +
                    "FROM negozi\n" +
                    "INNER JOIN negozio_categoriemerceologiche ON negozi.negozioId = negozio_categoriemerceologiche.negozioId\n" +
                    "INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId\n" +
                    "inner join pubblicita  on pubblicita.negozioId = negozi.negozioId\n" +
                    "                   \n" +
                    "WHERE nome LIKE \""+ categoria +"\"\n" +
                    "AND pubblicita.isCancellato = 0\n" +
                    "AND current_date() <= dataFine\n" +
                    "AND   dataInizio <= current_date();");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio n = new Negozio().mapData(rs);
                n.categorie.add(new CategoriaMerceologica().mapData(rs));
                ln.add(n);

            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return ln;

    }

    public List<Negozio> getNegoziConPubblicitaAttivaByCategoriaAndString(String categoria, String colonna, String stringaDaRicercare) throws SQLException {

        PreparedStatement st;
        ResultSet rs;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT distinct negozi.negozioId, `denominazione`, token,`indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`,`indirizzo.provincia`, telefono, eMail, password, categoriemerceologiche.categoriaId, nome \n" +
                    "FROM negozi\n" +
                    "INNER JOIN negozio_categoriemerceologiche ON negozi.negozioId = negozio_categoriemerceologiche.negozioId\n" +
                    "INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId\n" +
                    "inner join pubblicita  on pubblicita.negozioId = negozi.negozioId\n" +
                    "                   \n" +
                    "WHERE nome LIKE \""+ categoria +"\"\n" +
                    "AND pubblicita.isCancellato = 0\n" +
                    "AND current_date() <= dataFine\n" +
                    "AND   dataInizio <= current_date()" +
                    "AND ("+colonna+" like \""+stringaDaRicercare+"\");");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio n = new Negozio().mapData(rs);
                n.categorie.add(new CategoriaMerceologica().mapData(rs));
                ln.add(n);

            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return ln;


    }


    public List<Negozio> OrderByPubblicita(List<Negozio> ln, List<Negozio> lp) throws SQLException {

        List<Integer> list1 = new ArrayList<>();
        for (Negozio n: ln) {
            list1.add(n.id);
        }

        List<Integer> list2 = new ArrayList<>();
        for (Negozio n: lp) {
            list2.add(n.id);
        }
        List<Negozio> ret = new ArrayList<Negozio>(lp);
        list1.removeAll(list2);
        for (Integer i: list1) {
            ret.add(new GestoreNegozio().getById(i));
        }

        return ret;

    }
}
