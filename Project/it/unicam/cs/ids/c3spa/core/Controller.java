package it.unicam.cs.ids.c3spa.core;

public interface Controller {

    boolean executerCliente(String comando);
    boolean executerCorriere(String comando);
    boolean executerCommerciante(String comando);

}
