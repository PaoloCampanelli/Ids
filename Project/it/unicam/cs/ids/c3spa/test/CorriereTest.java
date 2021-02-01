package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CorriereTest {

    @Test
    public void Corriere() throws SQLException {
        Indirizzo indirizzoBartolini = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "1", "MACERATA","62100",  "MC");
        Corriere corriereBartolini= new Corriere(1, "BARTOLINI", indirizzoBartolini, "07111111111", "BARTOLINI@GMAIL.COM", "BARTOLINI!!");
        Indirizzo indirizzoDhl = new Indirizzo().CreaIndirizzo("SETIFICIO", "10", "CAMERINO", "62032", "MC");
        Corriere corriereDhl = new Corriere( 2, "DHL", indirizzoDhl, "0711341111", "DHL@GMAIL.COM", "DHL!!!");
        Corriere corriere = new Corriere();
        assertNotEquals(corriereDhl, corriereBartolini);
        assertNotEquals(corriere, corriereBartolini);
    }

}