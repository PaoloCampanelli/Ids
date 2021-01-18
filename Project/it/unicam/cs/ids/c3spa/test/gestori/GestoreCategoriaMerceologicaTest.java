package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreCategoriaMerceologicaTest {

    public static GestoreCategoriaMerceologica gestoreCategoriaMerceologica = new GestoreCategoriaMerceologica();
    List<CategoriaMerceologica> categorieMerceologiche = new ArrayList<>();
    static List<CategoriaMerceologica> categorieSalvate = new ArrayList<>();


    @BeforeAll
    static void creaDataBaseTest() throws SQLException {
        categorieSalvate = gestoreCategoriaMerceologica.getAll();
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


    @AfterAll
    static void resetCategorie() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.categoriemerceologiche;");
        for(CategoriaMerceologica categoria :categorieSalvate){
            stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('"+categoria.idCategoria+"', '"+categoria.nome+"', '0');");
        }
        stmt.close();
        conn.close();
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
    void save() throws SQLException {
        inseriscoCategorieTest();
        CategoriaMerceologica nuovaCategoria = new CategoriaMerceologica(0, "CATEGORIA");
        assertEquals(new GestoreCategoriaMerceologica().save(nuovaCategoria).toString(), gestoreCategoriaMerceologica.getById(nuovaCategoria.idCategoria).toString());
    }

    @Test
    void delete() {
    }
}