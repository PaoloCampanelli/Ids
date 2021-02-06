package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.IMapData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Amministratore  extends Account implements IMapData<Amministratore> {

    public Amministratore(int amministratoreId, String nomeCognome, Indirizzo indirizzo, String telefono, String eMail, String password) throws SQLException {
        this.id= amministratoreId;
        this.denominazione = nomeCognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eMail = eMail;
        this.password = password;
    }

    public Amministratore(String nomeCognome, Indirizzo indirizzo, String telefono, String eMail, String password) throws SQLException {
        this.denominazione = nomeCognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eMail = eMail;
        this.password = password;
        this.id = 0;
    }

    public Amministratore()
    {
        this.indirizzo = new Indirizzo();
    }

    @Override
    public Amministratore mapData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("amministratoreId");
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


    public Boolean AggiungiToken(int quantita, Negozio negozio){

        negozio.token = negozio.token+quantita;
        return true;
    }

    @Override
    public String toString() {
        return "Amministratore{" +
                "id=" + id +
                ", denominazione='" + denominazione + '\'' +
                ", indirizzo=" + indirizzo +
                ", telefono='" + telefono + '\'' +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
