package it.unicam.cs.ids.c3spa.core.astratto;

import java.sql.SQLException;
import java.util.List;

public interface ICRUD<T> {
    T GetById(int id) throws SQLException;
    List<T> GetAll() throws SQLException;
    T Save(T obj) throws SQLException;
    void Delete(int id) throws SQLException;
}
