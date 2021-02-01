package it.unicam.cs.ids.c3spa.test;


import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class ClienteTest {



    @Test
    public void construttoreCliente() throws SQLException {
        Indirizzo indirizzoSara = new Indirizzo().CreaIndirizzo("BASSO", "14", "URBISAGLIA","62010",  "MC");
        Cliente clienteSara = new Cliente("SARA COMPAGNUCCI", indirizzoSara, "34111111111", "SARA.COMPAGNUCCI@GMAIL.COM", "SARA!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo = new Cliente( "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Cliente clienteSara2 = new Cliente();
        assertNotEquals(clientePaolo, clienteSara);
        assertNotEquals(clienteSara2, clienteSara);
    }

    @Test
    public void Cliente() throws SQLException {
        Indirizzo indirizzoSara = new Indirizzo().CreaIndirizzo("BASSO", "14", "URBISAGLIA","62010",  "MC");
        Cliente clienteSara = new Cliente(1, "SARA COMPAGNUCCI", indirizzoSara, "34111111111", "SARA.COMPAGNUCCI@GMAIL.COM", "SARA!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo = new Cliente( 2, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Cliente clienteSara2 = new Cliente();
        assertNotEquals(clientePaolo, clienteSara);
        assertNotEquals(clienteSara2, clienteSara);
    }

}
