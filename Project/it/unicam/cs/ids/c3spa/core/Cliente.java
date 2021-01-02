package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;
import it.unicam.cs.ids.c3spa.core.astratto.IMapData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public Cliente MapData(ResultSet rs) throws SQLException {
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
    public Cliente GetById(int id) {
        Statement st;
        ResultSet rs;
        String sql;
        Cliente c = new Cliente();

        try {
            sql = "SELECT * FROM clienti WHERE (`clienteId` = "+id+");";
            // ________________________________query

            st = Servizi.connessione().createStatement(); // creo sempre uno statement sulla
            // connessione
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {

                c.MapData(rs);

/*                Indirizzo a = new Indirizzo();
                a.CreaIndirizzo(rs.getString("via"), rs.getString("numero"), rs.getString("citta"),
                        rs.getString("cap"), rs.getString("provincia"));
                if(indirizzi.stream().noneMatch(b -> b.via.equals(a.via)))
                    if(indirizzi.stream().noneMatch(b -> b.numero==a.numero))
                        if(indirizzi.stream().noneMatch(b -> b.cap.equals(a.cap)))
                            this.indirizzi.add(a);*/
            }

            Servizi.connessione().close(); // chiusura connessione
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        return c;
    }
}