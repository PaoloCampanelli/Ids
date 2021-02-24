package it.unicam.cs.ids.c3spa.astratto;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMapData<T> {
        T mapData(ResultSet rs) throws SQLException;
}
