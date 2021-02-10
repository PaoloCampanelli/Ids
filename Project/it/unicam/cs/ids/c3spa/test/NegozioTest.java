package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class NegozioTest {

    private Negozio negozio = new Negozio();

    @Test
    public void Negozio() throws SQLException {
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoMercatoDellaCasa = new Indirizzo().CreaIndirizzo("CONVENTO", "10", "URBISAGLIA", "62010", "MC");
        Negozio negozioMercatoDellaCasa = new Negozio( "MERCATO DELLA CASA", indirizzoMercatoDellaCasa, "073333333", "MERCATODELLACASA@GMAIL.COM", "MERCATO DELLA CASA!!");
        Negozio negozio = new Negozio();
        assertNotEquals(negozioFruttivendolo, negozioMercatoDellaCasa);
        assertNotEquals(negozio, negozioFruttivendolo);
    }

    @Test
    public void construttoreNegozio() throws SQLException {
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1, "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoMercatoDellaCasa = new Indirizzo().CreaIndirizzo("CONVENTO", "10", "URBISAGLIA", "62010", "MC");
        Negozio negozioMercatoDellaCasa = new Negozio( 2, "MERCATO DELLA CASA", indirizzoMercatoDellaCasa, "073333333", "MERCATODELLACASA@GMAIL.COM", "MERCATO DELLA CASA!!");
        Negozio negozio = new Negozio();
        assertNotEquals(negozioFruttivendolo, negozioMercatoDellaCasa);
        assertNotEquals(negozio, negozioFruttivendolo);
    }


    @Test
    void aggiungiCategoria() {
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaAbbigliamento = new CategoriaMerceologica(2, "ABBIGLIAMENTO");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1, "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoMercatoDellaCasa = new Indirizzo().CreaIndirizzo("CONVENTO", "10", "URBISAGLIA", "62010", "MC");
        Negozio negozioMercatoDellaCasa = new Negozio( 2, "MERCATO DELLA CASA", indirizzoMercatoDellaCasa, "073333333", "MERCATODELLACASA@GMAIL.COM", "MERCATO DELLA CASA!!");
        negozioFruttivendolo.aggiungiCategoria(categoriaFrutta);
        negozioMercatoDellaCasa.aggiungiCategoria(categoriaAbbigliamento);
        assertNotEquals(negozioFruttivendolo.categorie.stream().filter(c->c.nome.equals("FRUTTA")).findAny().orElse(null), null);
        assertNotEquals(negozioMercatoDellaCasa.categorie.stream().filter(c->c.nome.equals("ABBIGLIAMENTO")).findAny().orElse(null), null);
    }

    @Test
    void rimuoviCategoria() {
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaAbbigliamento = new CategoriaMerceologica(2, "ABBIGLIAMENTO");
        negozio.rimuoviCategoria(categoriaFrutta);
        negozio.rimuoviCategoria(categoriaAbbigliamento);
        assertFalse(negozio.categorie.contains(categoriaFrutta));
        assertFalse(negozio.categorie.contains(categoriaAbbigliamento));
    }

    @Test
    void aggiungiPubblicita() {
        Pubblicita p = new Pubblicita(4, Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(300000))), Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(625000))), negozio);
        assertEquals(negozio.aggiungiPubblicita(p).toString(), p.toString());
    }
}