package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

public interface IController {

    boolean executerCliente(String comando);
    boolean executerCorriere(String comando);
    boolean executerCommerciante(String comando);

}
