package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.astratto.StatoPaccoEnum;
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


    @Test
    void prendiPacco() throws SQLException {
        Indirizzo indirizzoBartolini = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "1", "MACERATA","62100",  "MC");
        Corriere corriereBartolini= new Corriere(1, "BARTOLINI", indirizzoBartolini, "07111111111", "BARTOLINI@GMAIL.COM", "BARTOLINI!!");
        Indirizzo primoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "URBISAGLIA", "62010", "MC");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo= new Cliente(1, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Pacco primoPacco = new Pacco().CreaPacco(1, clientePaolo, negozioFruttivendolo, java.sql.Date.valueOf("2021-06-01"), primoIndirizzo);
        corriereBartolini.prendiPacco(primoPacco);
        assertTrue(primoPacco.statiPacco.stream().anyMatch(a->a.stato.equals(StatoPaccoEnum.assegnato)));
    }

    @Test
    void consegnaPacco() throws SQLException {
        Indirizzo indirizzoBartolini = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "1", "MACERATA","62100",  "MC");
        Corriere corriereBartolini= new Corriere(1, "BARTOLINI", indirizzoBartolini, "07111111111", "BARTOLINI@GMAIL.COM", "BARTOLINI!!");
        Indirizzo primoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "URBISAGLIA", "62010", "MC");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo= new Cliente(1, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Pacco primoPacco = new Pacco().CreaPacco(1, clientePaolo, negozioFruttivendolo, java.sql.Date.valueOf("2021-06-01"), primoIndirizzo);
        corriereBartolini.consegnaPacco(primoPacco);
        assertTrue(primoPacco.statiPacco.stream().anyMatch(a->a.stato.equals(StatoPaccoEnum.consegnato)));
    }
}