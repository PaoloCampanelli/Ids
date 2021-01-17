package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.SQLException;
import java.util.List;

public class AccountController{

    public Cliente creatoreCliente(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) throws SQLException {
        Cliente cliente = new Cliente(denominazione, indirizzo, telefono, email, password);
        new GestoreCliente().save(cliente);
        return cliente;
    }

    public Corriere creatoreCorriere(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) throws SQLException {
        Corriere corriere = new Corriere(0, denominazione, indirizzo, telefono, email, password);
        new GestoreCorriere().save(corriere);
        return corriere;
    }

    public Negozio creatoreCommerciante(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) throws SQLException {
        Negozio negozio = new Negozio(denominazione, indirizzo, telefono, email, password);
        new GestoreNegozio().save(negozio);
        return negozio;
    }

    public Indirizzo indirizzoAccount(String via, String numero, String citta, String cap, String provincia) {
        Cliente cliente = new Cliente();
        return cliente.indirizzo.CreaIndirizzo(via, numero, citta, cap, provincia);
    }

    public boolean controllaDati(String tipologia, String email, String password) throws SQLException {
        switch(tipologia){
            case "CLIENTE":{
                List<Cliente> lc = new GestoreCliente().getAll();
                return lc.stream().anyMatch(cliente -> cliente.eMail.equals(email) && cliente.password.equals(password));
            }
            case "COMMERCIANTE":{
                List<Negozio> ln = new GestoreNegozio().getAll();
                return ln.stream().anyMatch(negozio -> negozio.eMail.equals(email) && negozio.password.equals(password));
            }
            case "CORRIERE":{
                List<Corriere> ln = new GestoreCorriere().getAll();
                return ln.stream().anyMatch(corriere -> corriere.eMail.equals(email) && corriere.password.equals(password));
            }
        }
        return false;
    }

    public int prendiID(String tipologia, String email, String password) throws SQLException {
        switch(tipologia){
            case "CLIENTE":{
                List<Cliente> lc = new GestoreCliente().getAll();
                if(controllaDati("CLIENTE", email, password))
                    return lc.stream().filter(cliente -> cliente.eMail.equals(email) && cliente.password.equals(password)).findAny().get().id;
            }
            case "COMMERCIANTE":{
                List<Negozio> ln = new GestoreNegozio().getAll();
                if(controllaDati("COMMERCIANTE", email, password))
                    return ln.stream().filter(negozio -> negozio.eMail.equals(email) && negozio.password.equals(password)).findAny().get().id;
            }
            case "CORRIERE":{
                List<Corriere> lcr = new GestoreCorriere().getAll();
                if(controllaDati("CORRIERE", email, password)){
                    return lcr.stream().filter(corriere -> corriere.eMail.equals(email) && corriere.password.equals(password)).findAny().get().id;
                }
            }
        }
        return 0;
    }

    public boolean controllaCliente(String email) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        return lc.stream().anyMatch(cliente -> cliente.eMail.equals(email));
    }

    public int prendiIDCliente(String email) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        if(controllaCliente(email)){
            return lc.stream().filter(cliente -> cliente.eMail.equals(email)).findAny().get().id;
        }
        return 0;
    }

    public boolean controllaMail(String tipologia, String email) throws SQLException {
        switch(tipologia){
            case "CLIENTE":
                List<Cliente> lc = new GestoreCliente().getAll();
                return lc.stream().anyMatch(cliente -> (cliente.eMail).equals(email));
            case "CORRIERE":{
                List<Corriere> lnc = new GestoreCorriere().getAll();
                return lnc.stream().anyMatch(corriere -> corriere.eMail.equals(email));
            }
            case "COMMERCIANTE": {
                List<Negozio> ln = new GestoreNegozio().getAll();
                return ln.stream().anyMatch(negozio -> negozio.eMail.equals(email));
            }
        }
        return false;
    }

    public boolean controllaID(String tipologia, int identificativo) throws SQLException {
        switch(tipologia){
            case "CLIENTE":
                List<Cliente> lc = new GestoreCliente().getAll();
                return lc.stream().anyMatch(cliente -> cliente.id == identificativo);
            case "CORRIERE":{
                List<Corriere> lnc = new GestoreCorriere().getAll();
                return lnc.stream().anyMatch(corriere -> corriere.id == identificativo);
            }
            case "COMMERCIANTE": {
                List<Negozio> ln = new GestoreNegozio().getAll();
                return ln.stream().anyMatch(negozio -> negozio.id == identificativo);
            }
        }
        return false;
    }

}




