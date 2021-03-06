package it.unicam.cs.ids.c3spa.gestori;
import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.astratto.*;;
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
            st = conn.prepareStatement("SELECT * FROM progetto_ids.negozi WHERE negozioId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
            st.setInt(1, id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                n.mapData(rs);
            }

            //Popolo le categorie collegate al negozio
            st = conn.prepareStatement("SELECT  negozio_categoriemerceologiche.negozioId, negozio_categoriemerceologiche.categoriaId  FROM negozio_categoriemerceologiche                   \n" +
                    "INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId\n" +
                    "WHERE negozioId = ? AND isCancellato = 0;"); // creo sempre uno statement sulla
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
            sql = "SELECT * FROM negozi where isCancellato = 0 ;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio a = new Negozio();
                a.mapData(rs);
                Negozio b = new GestoreNegozio().getById(a.id);
                n.add(b);
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
                    st = conn.prepareStatement("INSERT INTO progetto_ids.negozi (denominazione, token, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, n.denominazione);
                    st.setInt(2, n.token);
                    st.setString(3, n.indirizzo.citta);
                    st.setString(4, n.indirizzo.numero);
                    st.setString(5, n.indirizzo.cap);
                    st.setString(6, n.indirizzo.via);
                    st.setString(7, n.indirizzo.provincia);
                    st.setString(8, n.telefono);
                    st.setString(9, n.eMail);
                    st.setString(10, new Servizi().encrypt(n.password));

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        n.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                } else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.negozi SET denominazione = ?, token = ?, `indirizzo.citta` = ?, `indirizzo.numero` = ?, `indirizzo.cap` = ?, `indirizzo.via` = ?, `indirizzo.provincia` = ?, telefono = ?, eMail = ?, password = ? WHERE negozioId = ?"); // creo sempre uno statement sulla
                    st.setString(1, n.denominazione);
                    st.setInt(2, n.token);
                    st.setString(3, n.indirizzo.citta);
                    st.setString(4, n.indirizzo.numero);
                    st.setString(5, n.indirizzo.cap);
                    st.setString(6, n.indirizzo.via);
                    st.setString(7, n.indirizzo.provincia);
                    st.setString(8, n.telefono);
                    st.setString(9, n.eMail);
                    st.setString(10, n.password);
                    st.setInt(11, n.id);

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
            catch (Exception e) {
                e.printStackTrace();
            }
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

            sql = "UPDATE `progetto_ids`.`negozi` SET `isCancellato` = '1' WHERE (`negozioId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }

    private List<Negozio> getByString(String colonna, String stringaDaRicercare) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();
        GestoreCategoriaMerceologica gcm = new GestoreCategoriaMerceologica();


        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM negozi WHERE ("+colonna+" like \""+stringaDaRicercare+"\")" +
                    "AND negozi.isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio n = new Negozio();
                n.mapData(rs);
                ln.add(new GestoreNegozio().getById(n.id));
            }
            //Popolo le categorie collegate al negozio

            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return ln;
    }

    public Negozio getByEMail(String eMail) throws SQLException {
        String colonna= "eMail";
        return getByString(colonna, eMail).stream().findFirst().orElse(null);
    }

    public List<Negozio> getByDenominazione(String denominazione) throws SQLException {
        String colonna= "denominazione";
        return getByString(colonna, denominazione);
    }

    public List<Negozio> getByIndirizzo( String citta) throws SQLException {
        return getByString("`indirizzo.citta`", citta);
    }

    public List<Negozio> getByCategoria(String categoria) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        List<Negozio> ln = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.prepareStatement("SELECT distinct negozi.negozioId, `denominazione`, token,`indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`,`indirizzo.provincia`, telefono, eMail, password FROM negozi\n" +
                    "INNER JOIN negozio_categoriemerceologiche ON negozi.negozioId = negozio_categoriemerceologiche.negozioId\n" +
                    "                    INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId\n" +
                    "                    WHERE nome LIKE '%"+categoria+"%';");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio n = new Negozio().mapData(rs);
                n= new GestoreNegozio().getById(n.id);
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
            st = conn.prepareStatement("SELECT distinct negozi.negozioId, `denominazione`, token,`indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`,`indirizzo.provincia`, telefono, eMail, password FROM negozi\n" +
                    "                    INNER JOIN negozio_categoriemerceologiche ON negozi.negozioId = negozio_categoriemerceologiche.negozioId\n" +
                    "                    INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId\n" +
                    "                    WHERE nome LIKE '%"+categoria+"%' AND `indirizzo.citta` LIKE '%"+citta+"%';");
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio n = new Negozio().mapData(rs);
                n= new GestoreNegozio().getById(n.id);
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

    public List<Negozio> getNegoziAndCategorie() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Negozio> n = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT distinct negozi.negozioId, `denominazione`, token,`indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`,`indirizzo.provincia`, telefono, eMail, password, categoriemerceologiche.categoriaId, nome FROM negozi\n" +
                    "                    INNER JOIN negozio_categoriemerceologiche ON negozi.negozioId = negozio_categoriemerceologiche.negozioId\n" +
                    "                   INNER JOIN categoriemerceologiche ON negozio_categoriemerceologiche.categoriaId = categoriemerceologiche.categoriaId;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Negozio a = new Negozio();
                a.mapData(rs);
                a.categorie.add(new CategoriaMerceologica().mapData(rs));
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

    public boolean creaSconto(Sconto sconto , Negozio negozio){

        try {
            negozio.aggiungiSconto(sconto);
            new GestoreSconto().save(sconto);
            return true;
        }
        catch (Exception e){
            System.out.println("errore:" + e.getMessage());
        }

        return false;
    }

    public boolean creaPubblicita(Pubblicita pubblicita , Negozio negozio){
        try {
            negozio.aggiungiPubblicita(pubblicita);
            new GestorePubblicita().save(pubblicita);
            new GestoreNegozio().save(negozio);
            return true;
        }
        catch (Exception e){
            System.out.println("errore:" + e.getMessage());
        }

        return false;


    }

    public Negozio cambiaPassword(Negozio negozio) throws Exception {

        PreparedStatement st;
        ResultSet rs;
        Connection conn = ApriConnessione();
        st = conn.prepareStatement("UPDATE progetto_ids.clienti SET password = ? WHERE clienteId = ?"); // creo sempre uno statement sulla
        st.setString(1, new Servizi().encrypt(negozio.password));
        st.setInt(2,negozio.id);
        st.executeUpdate(); // faccio la query su uno statement

        GestoreBase.ChiudiConnessione(conn); // chiusura connessione
        return negozio;
    }
}
