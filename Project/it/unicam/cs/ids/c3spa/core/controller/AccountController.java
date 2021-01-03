package it.unicam.cs.ids.c3spa.core.controller;

import it.unicam.cs.ids.c3spa.core.*;

public class AccountController{

    private int ID = 0;
    private Pubblico cliente;
    private Trasportatori corriere;
    private CentroCommerciale cc;

    public void creatoreCliente(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        cliente.CreaAccount(ID, denominazione, indirizzo, telefono, email, password);
    }

    public void creatoreCorriere(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        corriere.CreaAccount(ID, denominazione, indirizzo, telefono, email, password);
    }

    public void creatoreCommerciante(String denominazione, String email, String password, String telefono, Indirizzo indirizzo) {
        corriere.CreaAccount(ID, denominazione, indirizzo, telefono, email, password);
    }



}

