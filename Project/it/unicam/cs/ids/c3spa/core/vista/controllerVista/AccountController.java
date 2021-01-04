package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;

import java.sql.SQLException;
import java.util.List;

public class AccountController{

    private int ID = 0;

    public void creatoreCliente(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) throws SQLException {
        Cliente cliente = new Cliente(ID, denominazione, indirizzo, telefono, email, password);
    }

    public void creatoreCorriere(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        Corriere corriere = new Corriere(ID, denominazione, indirizzo, telefono, email, password);
    }

    public void creatoreCommerciante(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        Negozio negozio = new Negozio(ID, denominazione, indirizzo, telefono, email, password);
    }

    public Cliente checkCliente(String email, String password) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();

        return lc.stream().filter(cliente -> cliente.eMail.equals(email) && cliente.password.equals(password)).findAny().get();
        }
}




