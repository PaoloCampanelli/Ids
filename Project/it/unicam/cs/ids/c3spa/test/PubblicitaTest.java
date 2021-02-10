package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pubblicita;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PubblicitaTest {

    List<Pubblicita> pubblicita = new ArrayList<>();

    @Test
    public void Pubblicita(){
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1, "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Pubblicita pub = new Pubblicita(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(432000)), negozioFruttivendolo);
        pubblicita.add(pub);
        assertEquals(pub.toString(), pubblicita.stream().filter(p->p.negozio.equals(negozioFruttivendolo)).findAny().orElse(null).toString());
    }

}