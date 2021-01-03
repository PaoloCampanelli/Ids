package it.unicam.cs.ids.c3spa.core.controller;

public interface IController {

    boolean executerCliente(String comando);
    boolean executerCorriere(String comando);
    boolean executerCommerciante(String comando);

}
