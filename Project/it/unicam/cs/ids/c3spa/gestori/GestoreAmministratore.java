package it.unicam.cs.ids.c3spa.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.astratto.ICRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreAmministratore extends GestoreBase implements ICRUD {

    Amministratore a = new Amministratore();

    @Override
    public Amministratore getById(int id) throws SQLException {
        PreparedStatement st;
        ResultSet rs;
        String sql;
        Connection conn = ApriConnessione();

        try {

            st = conn.prepareStatement("SELECT * FROM amministratori WHERE AmministratoreId = ? AND isCancellato = 0"); // creo sempre uno statement sulla
            st.setInt(1,id);
            rs = st.executeQuery(); // faccio la query su uno statement
            while (rs.next() == true) {
                a.mapData(rs);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return a;
    }

    @Override
    public List<Amministratore> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Amministratore> lc = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM amministratori where isCancellato = 0;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Amministratore a = new Amministratore();
                a.mapData(rs);
                lc.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lc;
    }

    @Override
    public Amministratore save(Object entity) throws SQLException {
        if(entity instanceof Amministratore) {

            PreparedStatement st;
            ResultSet rs;
            Connection conn = ApriConnessione();
            Amministratore c = (Amministratore) entity;

            try {

                if (c.id == 0) { // è un inserimento
                    st = conn.prepareStatement("INSERT INTO progetto_ids.amministratori (denominazione, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, telefono, eMail, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // creo sempre uno statement sulla
                    st.setString(1, c.denominazione);
                    st.setString(2, c.indirizzo.citta);
                    st.setString(3, c.indirizzo.numero);
                    st.setString(4, c.indirizzo.cap);
                    st.setString(5, c.indirizzo.via);
                    st.setString(6, c.indirizzo.provincia);
                    st.setString(7, c.telefono);
                    st.setString(8, c.eMail);
                    st.setString(9, new Servizi().encrypt(c.password));

                    st.executeUpdate(); // faccio la query su uno statement
                    rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        c.id = rs.getInt(1);
                    } else {

                        throw new SQLException("Inserimento non effettuato!");
                    }
                }
                else //é una modifica
                {
                    st = conn.prepareStatement("UPDATE progetto_ids.amministratori SET denominazione = ?, `indirizzo.citta` = ?, `indirizzo.numero` = ?, `indirizzo.cap` = ?, `indirizzo.via` = ?, `indirizzo.provincia` = ?, telefono = ?, eMail = ?, password = ? WHERE amministratoreId = ?"); // creo sempre uno statement sulla
                    st.setString(1, c.denominazione);
                    st.setString(2, c.indirizzo.citta);
                    st.setString(3, c.indirizzo.numero);
                    st.setString(4, c.indirizzo.cap);
                    st.setString(5, c.indirizzo.via);
                    st.setString(6, c.indirizzo.provincia);
                    st.setString(7, c.telefono);
                    st.setString(8, c.eMail);
                    st.setString(9, new Servizi().encrypt(c.password));
                    st.setInt(10,c.id);

                    st.executeUpdate(); // faccio la query su uno statement
                }
                GestoreBase.ChiudiConnessione(conn); // chiusura connessione
                return c;
            } catch (SQLException e) {
                System.out.println("errore:" + e.getMessage());
                return null;
            } // fine try-catch
            catch (Exception e) {
                e.printStackTrace();
            }
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

            sql = "UPDATE `progetto_ids`.`amministratori` SET `isCancellato` = '1' WHERE (`negozioId` = '"+id+"');";

            st = conn.createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            GestoreBase.ChiudiConnessione(conn); // chiusura connessione

        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());

        } // fine try-catch

    }

    public Amministratore getAmministratoreByProvincia(String provincia) throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        List<Amministratore> la = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM amministratori WHERE (`indirizzo.provincia` like '"+provincia+"');";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Amministratore a = new Amministratore();
                la.add(a.mapData(rs));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);
        return la.stream().findFirst().orElse(null);
    }

    public List<Cliente> getClientiCancellati(Amministratore amministratore) throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Cliente> lc = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM clienti where isCancellato = 1 And  `indirizzo.provincia` = \""+amministratore.indirizzo.provincia+"\";";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Cliente a = new Cliente();
                a.mapData(rs);
                lc.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lc;

    }

    public List<Corriere> getCorrieriCancellati(Amministratore amministratore) throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Corriere> lc = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM corrieri where isCancellato = 1 And  `indirizzo.provincia` = \""+amministratore.indirizzo.provincia+"\";";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Corriere c = new Corriere();
                c.mapData(rs);
                lc.add(c);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        ChiudiConnessione(conn);

        return lc;
    }

    public List<Negozio> getNegoziCancellati(Amministratore amministratore) throws SQLException {

        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Negozio> n = new ArrayList<>();
        Connection conn = ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM negozi where isCancellato = 1 And  `indirizzo.provincia` = \""+amministratore.indirizzo.provincia+"\";";
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

    public boolean ripristinaCliente(Cliente cliente){

        PreparedStatement st;
        ResultSet rs;
        Connection conn = ApriConnessione();
        try {

                st = conn.prepareStatement("UPDATE progetto_ids.clienti SET isCancellato = 0 WHERE clienteId = ?"); // creo sempre uno statement sulla
                st.setInt(1, cliente.id);

                st.executeUpdate(); // faccio la query su uno statement
                st.close();


            GestoreBase.ChiudiConnessione(conn); // chiusura connessione
            return true;
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            return false;
        } // fine try-catch
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ripristinaCorriere(Corriere corriere){

        PreparedStatement st;
        ResultSet rs;
        Connection conn = ApriConnessione();
        try {

            st = conn.prepareStatement("UPDATE progetto_ids.corrieri SET isCancellato = 0 WHERE corriereId = ?"); // creo sempre uno statement sulla
            st.setInt(1, corriere.id);

            st.executeUpdate(); // faccio la query su uno statement
            st.close();


            GestoreBase.ChiudiConnessione(conn); // chiusura connessione
            return true;
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            return false;
        } // fine try-catch
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ripristinaNegozio(Negozio negozio){

        PreparedStatement st;
        ResultSet rs;
        Connection conn = ApriConnessione();
        try {

            st = conn.prepareStatement("UPDATE progetto_ids.negozi SET isCancellato = 0 WHERE negozioId = ?"); // creo sempre uno statement sulla
            st.setInt(1, negozio.id);

            st.executeUpdate(); // faccio la query su uno statement
            st.close();


            GestoreBase.ChiudiConnessione(conn); // chiusura connessione
            return true;
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            return false;
        } // fine try-catch
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean aggiungiToken(int quantita, Negozio negozio, Amministratore amministratore) throws SQLException {

        boolean b = amministratore.AggiungiToken(quantita, negozio);
        new GestoreNegozio().save(negozio);
        return b;

    }

    public Amministratore cambiaPassword(Amministratore amministratore) throws Exception {

        PreparedStatement st;
        ResultSet rs;
        Connection conn = ApriConnessione();
        st = conn.prepareStatement("UPDATE progetto_ids.amministratori SET password = ? WHERE amministratoreId = ?"); // creo sempre uno statement sulla
        st.setString(1, new Servizi().encrypt(amministratore.password));
        st.setInt(2,amministratore.id);
        st.executeUpdate(); // faccio la query su uno statement

        GestoreBase.ChiudiConnessione(conn); // chiusura connessione
        return amministratore;
    }
}
