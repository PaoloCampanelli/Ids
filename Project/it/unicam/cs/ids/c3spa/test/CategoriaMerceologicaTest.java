package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaMerceologicaTest {

    private Negozio negozio= new Negozio();

    @Test
    public void CategoriaMerceologica() throws SQLException {
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaAbbigliamento = new CategoriaMerceologica(2, "ABBIGLIAMENTO");
        assertEquals(categoriaFrutta, negozio.aggiungiCategoria(categoriaFrutta));
        assertEquals(categoriaAbbigliamento, negozio.aggiungiCategoria(categoriaAbbigliamento));
    }
}