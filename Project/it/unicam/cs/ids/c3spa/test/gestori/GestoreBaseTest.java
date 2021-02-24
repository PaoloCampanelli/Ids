package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.gestori.GestoreBase;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class GestoreBaseTest {

    @Test
    void apriConnessione() {
        System.out.println("apriConnessione");
        Connection result = GestoreBase.ApriConnessione();
        assertEquals(result != null, true);
    }

}