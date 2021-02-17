package it.unicam.cs.ids.c3spa.controller;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;

import java.sql.SQLException;
import java.util.List;

public class AdminController {

    public Amministratore prendiAmministratore(String email) throws SQLException {
        GestoreAmministratore ga = new GestoreAmministratore();
        List<Amministratore> lc = ga.getAll();
        int id = lc.stream().filter(c -> c.eMail.equals(email)).findAny().get().id;
        return ga.getById(id);
    }

}
