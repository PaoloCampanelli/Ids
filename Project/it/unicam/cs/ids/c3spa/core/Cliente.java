package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;
import it.unicam.cs.ids.c3spa.core.astratto.IMapData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Account implements IMapData<Cliente>, ICRUD<Cliente>
{
    public List<Pacco> pacchi;


    public Cliente(int clienteId, String nomeCognome, Indirizzo indirizzo, String telefono, String eMail, String password) {
        this.id= clienteId;
        this.denominazione = nomeCognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eMail = eMail;
        this.password = password;//super();
    }

    protected Cliente()
    {
        this.indirizzo = new Indirizzo();
    }

    @Override
    public Cliente mapData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("clienteId");
        this.denominazione = rs.getString("denominazione");
        this.indirizzo.citta = rs.getString("indirizzo.citta");
        this.indirizzo.numero = rs.getString("indirizzo.numero");
        this.indirizzo.cap = rs.getString("indirizzo.cap");
        this.indirizzo.via = rs.getString("indirizzo.via");
        this.indirizzo.provincia = rs.getString("indirizzo.provincia");
        this.telefono = rs.getString("telefono");
        this.eMail = rs.getString("eMail");
        this.password = rs.getString("password");
        return this;
    }

    @Override
    public Cliente getById(int id) throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        Cliente c = new Cliente();
        Connection conn = Servizi.ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM clienti WHERE (`clienteId` = "+id+");";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                c.mapData(rs);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        Servizi.ChiudiConnessione(conn);

        return c;
    }

    @Override
    public List<Cliente> getAll() throws SQLException {
        Statement st;
        ResultSet rs;
        String sql;
        ArrayList<Cliente> c = new ArrayList<>();
        Connection conn = Servizi.ApriConnessione();

        try {
            st = conn.createStatement(); // creo sempre uno statement sulla
            sql = "SELECT * FROM clienti;";
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Cliente a = new Cliente();
                a.mapData(rs);
                c.add(a);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        Servizi.ChiudiConnessione(conn);

        return c;
    }

    @Override
    public Cliente save(Cliente obj) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public String toString() {
        return "Cliente{" +
                " id=" + id +
                ", denominazione='" + denominazione + '\'' +
                ", indirizzo=" + indirizzo +
                ", telefono='" + telefono + '\'' +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                "pacchi=" + pacchi +
                '}';
    }
}