package it.unicam.cs.ids.c3spa.core.gestori;

import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.astratto.ICRUD;

import java.sql.SQLException;
import java.util.List;

public class GestoreCorriere  extends GestoreBase implements ICRUD {
    @Override
    public Corriere getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Corriere> getAll() throws SQLException {
        return null;
    }

    @Override
    public Corriere save(Object entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
