package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;
import it.unicam.cs.ids.c3spa.core.astratto.IMapData;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class Cliente extends Account implements IMapData<Cliente>
{
    public Cliente(int clienteId, String nomeCognome, Indirizzo indirizzo, String telefono, String eMail, String password) throws SQLException {
        this.id= clienteId;
        this.denominazione = nomeCognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eMail = eMail;
        this.password = password;
        Cliente a = new GestoreCliente().save(this);
    }

    public Cliente()
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
    public String toString() {
        return "Cliente{" +
                " id=" + id +
                ", denominazione='" + denominazione + '\'' +
                ", indirizzo=" + indirizzo +
                ", telefono='" + telefono + '\'' +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}