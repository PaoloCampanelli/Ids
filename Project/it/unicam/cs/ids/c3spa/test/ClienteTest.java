package it.unicam.cs.ids.c3spa.test;


import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;


public class ClienteTest {



    @Test
    public void Cliente() throws SQLException {
        Indirizzo indirizzoSara = new Indirizzo().CreaIndirizzo("BASSO", "14", "URBISAGLIA","62010",  "MC");
        Cliente clienteSara = new Cliente("SARA COMPAGNUCCI", indirizzoSara, "34111111111", "SARA.COMPAGNUCCI@GMAIL.COM", "SARA!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo = new Cliente( "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        assertEquals(clienteSara, clienteSara.CreaAccount(1, "SARA COMPAGNUCCI", indirizzoSara, "34111111111", "SARA.COMPAGNUCCI@GMAIL.COM", "SARA!!"));
        assertEquals(clientePaolo, clientePaolo.CreaAccount(2, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!"));
    }

}
