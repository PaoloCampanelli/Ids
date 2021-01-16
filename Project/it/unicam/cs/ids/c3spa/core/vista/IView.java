package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.vista.controllerVista.IController;

import java.io.IOException;
import java.sql.SQLException;

public interface IView {

    void start() throws SQLException;

    void arrivederci();
}
