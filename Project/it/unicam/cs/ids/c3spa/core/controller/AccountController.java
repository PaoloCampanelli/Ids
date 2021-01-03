package it.unicam.cs.ids.c3spa.core.controller;

import it.unicam.cs.ids.c3spa.core.*;

public class AccountController{

    private int ID = 0;

    public void creatoreCliente(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        Pubblico cliente = new Pubblico();
        cliente.CreaAccount(ID, denominazione, indirizzo, telefono, email, password);
    }

    public void creatoreCorriere(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        Trasportatori corriere = new Trasportatori();
        corriere.CreaAccount(ID, denominazione, indirizzo, telefono, email, password);
    }

    public void creatoreCommerciante(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        CentroCommerciale cc = new CentroCommerciale();
        cc.CreaAccount(ID, denominazione, indirizzo, telefono, email, password);
    }



}

