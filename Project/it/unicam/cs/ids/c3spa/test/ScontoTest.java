package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Sconto;
import org.junit.Test;

import java.sql.Date;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ScontoTest {

    @Test
    public void Sconto(){
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaMele = new CategoriaMerceologica(2, "MELE");
        Negozio negozioFruttivendolo = new Negozio( "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Sconto sconto =new Sconto("50%", Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(864000)), negozioFruttivendolo, categoriaFrutta);
        Sconto sconto2 = new Sconto("20%", Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(864000)), negozioFruttivendolo, categoriaMele);
        Sconto sconto0 = new Sconto();
        assertNotEquals(sconto, sconto2);
        assertNotEquals(sconto0, sconto2);
    }


}