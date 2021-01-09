package it.unicam.cs.ids.c3spa.core.gestori;
import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.*;;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreNegozio extends GestoreBase implements ICRUD{
    @Override
    public Negozio getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        Negozio n = new Negozio();
        GestoreCategoriaMerceologica gcm = new GestoreCategoriaMerceologica();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT * FROM progetto_ids.negozi WHERE negozioId = ?"); // creo sempre uno statement sulla
            st.setInt(1, id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                n.mapData(rs);
            }

            //Popolo le categorie collegate al negozio
            st = conn.prepareStatement("SELECT * FROM progetto_ids.negozio_categoriemerceologiche WHERE negozioId = ?"); // creo sempre uno statement sulla
            st.setInt(1, n.id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                n.categorie.add(gcm.getById(rs.getInt(2)));
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
        if (entity instanceof Negozio) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            Negozio n = (Negozio) entity;

            try {

                if (n.id == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.negozi (denominazione, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, n.denominazione);
                    st.setString(2, n.indirizzo.citta);
                    st.setString(3, n.indirizzo.numero);
                    st.setString(4, n.indirizzo.cap);
                    st.setString(5, n.indirizzo.via);
                    st.setString(6, n.indirizzo.provincia);
                    st.setString(7, n.telefono);
                    st.setString(8, n.eMail);
                    st.setString(9, n.password);

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    st.close();
                    if (rs.next()) {
                        n.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.negozi SET denominazione = ?, `indirizzo.citta` = ?, `indirizzo.numero` = ?, `indirizzo.cap` = ?, `indirizzo.via` = ?, `indirizzo.provincia` = ?, telefono = ?, eMail = ?, password = ? WHERE negozioId = ?"); // creo sempre uno statement sulla
                    st.setString(1, n.denominazione);
                    st.setString(2, n.indirizzo.citta);
                    st.setString(3, n.indirizzo.numero);
                    st.setString(4, n.indirizzo.cap);
                    st.setString(5, n.indirizzo.via);
                    st.setString(6, n.indirizzo.provincia);
                    st.setString(7, n.telefono);
                    st.setString(8, n.eMail);
                    st.setString(9, n.password);
                    st.setInt(10, n.id);

                    st.executeUpdate(); // faccio la query su uno statement
                    st.close();
                }

                //Gestire le categorie
                //Elimino tutte le categorie salvate per il negozio
                st = conn.prepareStatement("DELETE FROM progetto_ids.negozio_categoriemerceologiche WHERE negozioId = ?");
                st.setInt(1,n.id);

                st.executeUpdate();
                st.close();

                //Inserisco le categorie passate nell'oggetto
                for (CategoriaMerceologica c:n.categorie
                     ) {
                    st = conn.prepareStatement("INSERT INTO progetto_ids.negozio_categoriemerceologiche (negozioId, categoriaId) VALUES (?,?)");
                    st.setInt(1,n.id);
                    st.setInt(2,c.idCategoria);

                    st.executeUpdate();
                    st.close();
                }

                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return n;
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

            sql = "DELETE FROM `progetto_ids`.`negozi` WHERE (`negozioId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }

    public List<Negozio> getByString(String colonna, String stringaDaRicercare) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM negozi WHERE (`"+colonna+"` like '%"+stringaDaRicercare+"%');";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                ln.add(new Negozio().mapData(rs));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return ln;
    }

    public List<Negozio> getByEMail(String eMail) throws SQLException {
        String colonna= "eMail";
        return getByString(colonna, eMail);
    }

    public List<Negozio> getByDenominazione(String denominazione) throws SQLException {
        String colonna= "denominazione";
        return getByString(colonna, denominazione);
    }

    public List<Negozio> getByIndirizzo(String colonna, String citta) throws SQLException {
        return getByString(colonna, citta);
    }

    public List<Negozio> getByCategoria(String categoria) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT distinct negozi.negozioId, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`,`indirizzo.provincia`, telefono, eMail, password, categoriemerceologiche.categoriaId, nome FROM negozi\n" +
                    "INNER JOIN negozio_categoriemerceologiche ON negozi.negozioId = negozio_categoriemerceologiche.negozioId\n" +
                    "                    INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId\n" +
                    "                    WHERE nome LIKE '%"+categoria+"%';");
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

    public List<Negozio> getByCategoriaAndCitta(String categoria, String citta) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT distinct negozi.negozioId, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`,`indirizzo.provincia`, telefono, eMail, password, categoriemerceologiche.categoriaId, nome FROM negozi\n" +
                    "                    INNER JOIN negozio_categoriemerceologiche ON negozi.negozioId = negozio_categoriemerceologiche.negozioId\n" +
                    "                    INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId\n" +
                    "                    WHERE nome LIKE '%"+categoria+"%' AND `indirizzo.citta` LIKE '%"+citta+"%';");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                ln.add(new Negozio().mapData(rs));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return ln;
    }
}
