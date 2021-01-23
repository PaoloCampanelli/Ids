package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class GestorePaccoTest{

    public static GestorePacco gestorePacco = new GestorePacco();
    List<Pacco> pacchi = new ArrayList<>();
    static List<Pacco> pacchiSalvati = new ArrayList<>();

    @BeforeAll
    static void createDataBaseTest() throws SQLException{
        pacchiSalvati = gestorePacco.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.pacchi");
        stmt.execute("alter table pacchi AUTO_INCREMENT = 1;");
        stmt.close();
        conn.close();
    }

}