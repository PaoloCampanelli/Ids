package it.unicam.cs.ids.c3spa.astratto;

import java.sql.SQLException;
import java.util.List;

public interface ICRUD<T> {
    T getById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    T save(T entity) throws SQLException;
    void delete(int id) throws SQLException;
}
