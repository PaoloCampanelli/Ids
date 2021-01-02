package it.unicam.cs.ids.c3spa.core.astratto;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMapData<T> {
        T MapData(ResultSet rs) throws SQLException;
}
