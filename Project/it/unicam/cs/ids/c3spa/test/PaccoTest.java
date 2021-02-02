package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ConcurrentModificationException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaccoTest {

    @Test
    void creaPacco() throws SQLException {
        Indirizzo primoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "URBISAGLIA", "62010", "MC");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo= new Cliente(1, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Pacco primoPacco = new Pacco().CreaPacco(1, clientePaolo, negozioFruttivendolo, java.sql.Date.valueOf("2021-06-01"), primoIndirizzo);
        Pacco secondoPacco = new Pacco().CreaPacco(2, clientePaolo,negozioFruttivendolo, Date.valueOf("2021-01-30"), primoIndirizzo);
        Pacco pacco = new Pacco();
        assertNotEquals(primoPacco, secondoPacco);
        assertNotEquals(pacco, primoPacco);
    }

    @Test
    void setCorriere() throws SQLException {
        Indirizzo primoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "URBISAGLIA", "62010", "MC");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Indirizzo indirizzoBartolini = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "1", "MACERATA","62100",  "MC");
        Corriere corriereBartolini= new Corriere(1, "BARTOLINI", indirizzoBartolini, "07111111111", "BARTOLINI@GMAIL.COM", "BARTOLINI!!");
        Negozio negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo= new Cliente(1, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Pacco primoPacco = new Pacco().CreaPacco(1, clientePaolo, negozioFruttivendolo, java.sql.Date.valueOf("2021-06-01"), primoIndirizzo);
        Pacco secondoPacco = new Pacco().CreaPacco(2, clientePaolo,negozioFruttivendolo, Date.valueOf("2021-01-30"), primoIndirizzo);
        primoPacco.setCorriere(corriereBartolini);
        assertTrue(primoPacco.corriere.equals(corriereBartolini));
        StatoPacco stato = new StatoPacco(StatoPaccoEnum.assegnato, Date.from(Instant.now()));
        secondoPacco.statiPacco.add(stato);
        assertThrows(ConcurrentModificationException.class,
        ()->{  secondoPacco.setCorriere(corriereBartolini);
        });
    }

    @Test
    void setConsegnato() throws SQLException {
        Indirizzo primoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "URBISAGLIA", "62010", "MC");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo= new Cliente(1, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Pacco primoPacco = new Pacco().CreaPacco(1, clientePaolo, negozioFruttivendolo, java.sql.Date.valueOf("2021-06-01"), primoIndirizzo);
        Pacco secondoPacco = new Pacco().CreaPacco(2, clientePaolo,negozioFruttivendolo, Date.valueOf("2021-01-30"), primoIndirizzo);
        primoPacco.setConsegnato();
        assertNotEquals(primoPacco.statiPacco.stream().filter(a->a.stato.equals(StatoPaccoEnum.consegnato)).findAny().orElse(null), null);
        StatoPacco stato = new StatoPacco(StatoPaccoEnum.consegnato, Date.from(Instant.now()));
        secondoPacco.statiPacco.add(stato);
        assertThrows(ConcurrentModificationException.class,
                ()->{  secondoPacco.setConsegnato();
                });
    }
}