package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class CategoriaMerceologicaTest {

    List<CategoriaMerceologica> c = new ArrayList<>();

    @Test
    public void CategoriaMerceologica() throws SQLException {
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaAbbigliamento = new CategoriaMerceologica(2, "ABBIGLIAMENTO");
        c.add(categoriaFrutta);
        c.add(categoriaAbbigliamento);
        assertEquals(categoriaFrutta.toString(), c.stream().filter(f->f.nome.equals(categoriaFrutta.nome)).findAny().orElse(null).toString());
        assertEquals(categoriaAbbigliamento.toString(), c.stream().filter(f->f.nome.equals(categoriaAbbigliamento.nome)).findAny().orElse(null).toString());
    }

    @Test
    public void costruttoreCategoriaMerceologica() throws SQLException {
        CategoriaMerceologica categoriaLegno = new CategoriaMerceologica("LEGNO");
        CategoriaMerceologica categoriaPenne = new CategoriaMerceologica("PENNE");
        c.add(categoriaLegno);
        c.add(categoriaPenne);
        assertEquals(categoriaLegno.toString(), c.stream().filter(f->f.nome.equals(categoriaLegno.nome)).findAny().orElse(null).toString());
        assertEquals(categoriaPenne.toString(), c.stream().filter(f->f.nome.equals(categoriaPenne.nome)).findAny().orElse(null).toString());
    }
}