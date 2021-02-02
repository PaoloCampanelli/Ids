package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.Indirizzo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndirizzoTest {

    @Test
    void creaIndirizzo() {
        Indirizzo indirizzoBartolini = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "1", "MACERATA","62100",  "MC");
        Indirizzo indirizzoSara = new Indirizzo().CreaIndirizzo("BASSO", "14", "URBISAGLIA","62010",  "MC");
        Indirizzo indirizzo = new Indirizzo();
        assertNotEquals(indirizzoSara, indirizzoBartolini);
        assertNotEquals(indirizzo, indirizzoBartolini);
    }
}