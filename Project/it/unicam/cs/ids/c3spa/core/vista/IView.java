package it.unicam.cs.ids.c3spa.core.vista;

import java.io.IOException;
import java.sql.SQLException;

public interface IView {

    void start() throws IOException, SQLException;

    void apriVista(int id) throws IOException, SQLException;

}
