package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreCategoriaMerceologicaTest {

    public GestoreCategoriaMerceologica gestoreCategoriaMerceologica = new GestoreCategoriaMerceologica();
    List<CategoriaMerceologica> categorieMerceologiche = new ArrayList<>();
    CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica();
    CategoriaMerceologica categoriaAbbigliamento = new CategoriaMerceologica();

    @BeforeAll
    static void creaDataBaseTest() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.categoriemerceologiche;");
        stmt.execute("alter table clienti AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('1', 'FRUTTA', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('2', 'ABBIGLIAMENTO', '0');");
        stmt.close();
        conn.close();
    }

    private List<CategoriaMerceologica> inseriscoCategorieTest() throws SQLException {
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaAbbigliamento = new CategoriaMerceologica(2, "ABBIGLIAMENTO");
        categorieMerceologiche.add(categoriaFrutta);
        categorieMerceologiche.add(categoriaAbbigliamento);
        return categorieMerceologiche;
    }

    @Test
    void getById() throws SQLException {
        inseriscoCategorieTest();
        assertEquals(categorieMerceologiche.stream().filter(i->i.idCategoria == 1).findAny().orElse(null).toString(), new GestoreCategoriaMerceologica().getById(1).toString());
        assertEquals(categorieMerceologiche.stream().filter(i->i.idCategoria == 2).findAny().orElse(null).toString(), new GestoreCategoriaMerceologica().getById(2).toString());
    }

    @Test
    void getAll() throws SQLException {
        inseriscoCategorieTest();
        assertEquals(categorieMerceologiche.toString(), gestoreCategoriaMerceologica.getAll().toString());
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }
}