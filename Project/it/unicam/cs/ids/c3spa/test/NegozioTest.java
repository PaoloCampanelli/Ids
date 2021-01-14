package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class NegozioTest {

    private Negozio negozio = new Negozio();

    @Test
    public void Negozio() throws SQLException {
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoMercatoDellaCasa = new Indirizzo().CreaIndirizzo("CONVENTO", "10", "URBISAGLIA", "62010", "MC");
        Negozio negozioMercatoDellaCasa = new Negozio( "MERCATO DELLA CASA", indirizzoMercatoDellaCasa, "073333333", "MERCATODELLACASA@GMAIL.COM", "MERCATO DELLA CASA!!");
        assertEquals(negozioFruttivendolo, negozioFruttivendolo.CreaAccount(1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!"));
        assertEquals(negozioMercatoDellaCasa, negozioMercatoDellaCasa.CreaAccount(2, "MERCATO DELLA CASA", indirizzoMercatoDellaCasa, "073333333", "MERCATODELLACASA@GMAIL.COM", "MERCATO DELLA CASA!!"));
    }

    @Test
    void aggiungiCategoria() {
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaAbbigliamento = new CategoriaMerceologica(2, "ABBIGLIAMENTO");
        assertEquals(categoriaFrutta, negozio.aggiungiCategoria(categoriaFrutta));
        assertEquals(categoriaAbbigliamento, negozio.aggiungiCategoria(categoriaAbbigliamento));
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
    void attivaPubblicita() {
    }

}