package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.SQLException;
import java.util.List;

public class AccountController{

    private int ID = 0;

    public Cliente creatoreCliente(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) throws SQLException {
        Cliente cliente = new Cliente(denominazione, indirizzo, telefono, email, password);
        new GestoreCliente().save(cliente);
        return cliente;
    }

    public Corriere creatoreCorriere(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        Corriere corriere = new Corriere(ID, denominazione, indirizzo, telefono, email, password);
        //new GestoreCorriere().save(corriere);
        return corriere;
    }

    public Negozio creatoreCommerciante(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) throws SQLException {
        Negozio negozio = new Negozio(ID, denominazione, indirizzo, telefono, email, password);
        new GestoreNegozio().save(negozio);
        return negozio;
    }

    //METODO CONTROLLO CLIENTE
    public boolean controllaCliente(String email, String password) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        return lc.stream().anyMatch(cliente -> cliente.eMail.equals(email) && cliente.password.equals(password));
    }

    //METODO GETTER ID CLIENTE ATTRAVERSO EMAIL E PASSWORD
    public int prendiIDCliente(String email, String password) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        if(controllaCliente(email, password)){
            return lc.stream().filter(cliente -> cliente.eMail.equals(email) && cliente.password.equals(password)).findAny().get().id;
        }
        return 0;
    }

    public boolean controllaNegozio(String email, String password) throws SQLException {
        List<Negozio> ln = new GestoreNegozio().getAll();
        return ln.stream().anyMatch(negozio -> negozio.eMail.equals(email) && negozio.password.equals(password));
    }

    public int prendiIDNegozio(String email, String password) throws SQLException {
        List<Cliente> ln = new GestoreCliente().getAll();
        if(controllaCliente(email, password)){
            return ln.stream().filter(negozio -> negozio.eMail.equals(email) && negozio.password.equals(password)).findAny().get().id;
        }
        return 0;
    }

}




