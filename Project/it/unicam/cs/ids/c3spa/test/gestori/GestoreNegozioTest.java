package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GestoreNegozioTest {
    public static GestoreNegozio gestoreNegozio = new GestoreNegozio();
    static List<Negozio> negoziSalvati = new ArrayList<>();
    static List<CategoriaMerceologica> categorieSalvate = new ArrayList<>();
  //  static List<Integer> negozi_categorieMerceologiche = new ArrayList<>();
    List<Negozio> negozi = new ArrayList<>();
    List<CategoriaMerceologica> categorie = new ArrayList<>();
    Negozio negozioFruttivendolo = new Negozio();
    Negozio negozioMercatoDellaCasa = new Negozio();
    CategoriaMerceologica categoriaVerdura = new CategoriaMerceologica();
    CategoriaMerceologica categoriaVasi = new CategoriaMerceologica();

    @BeforeAll
    static void creaDataBaseTest() throws SQLException {
        negoziSalvati = gestoreNegozio.getAll();
        categorieSalvate = new GestoreCategoriaMerceologica().getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.negozio_categoriemerceologiche;");
        stmt.execute("delete from progetto_ids.categoriemerceologiche");
        stmt.execute("delete from progetto_ids.negozi;");
        stmt.execute("alter table negozi AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'FRUTTIVENDOLO', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '073733333', 'FRUTTIVENDOLO@GMAIL.COM', 'FRUTTIVENDOLO!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('2', 'MERCATO DELLA CASA', 'URBISAGLIA', '10', '62010', 'CONVENTO', 'MC', '073333333', 'MERCATODELLACASA@GMAIL.COM', 'MERCATODELLACASA!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('1', 'VERDURA', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('2', 'VASI', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`negozio_categoriemerceologiche` (`negozioId`, `categoriaId`) VALUES ('1', '1');");
        stmt.execute("INSERT INTO `progetto_ids`.`negozio_categoriemerceologiche` (`negozioId`, `categoriaId`) VALUES ('2', '2');");
        stmt.close();
        conn.close();
    }

    private List<Negozio> inseriscoNegozi(){
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        categoriaVerdura = new CategoriaMerceologica(1, "VERDURA");
        negozioFruttivendolo.categorie.add(categoriaVerdura);
        Indirizzo indirizzoMercatoDellaCasa = new Indirizzo().CreaIndirizzo("CONVENTO", "10", "URBISAGLIA", "62010", "MC");
        negozioMercatoDellaCasa = new Negozio( 2, "MERCATO DELLA CASA", indirizzoMercatoDellaCasa, "073333333", "MERCATODELLACASA@GMAIL.COM", "MERCATODELLACASA!!");
        categoriaVasi = new CategoriaMerceologica(2, "VASI");
        negozioMercatoDellaCasa.categorie.add(categoriaVasi);
        categorie.add(categoriaVasi);
        categorie.add(categoriaVerdura);
        negozi.add(negozioFruttivendolo);
        negozi.add(negozioMercatoDellaCasa);

        return negozi;
    }

    @AfterAll
    static void resetNegozi() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.negozio_categoriemerceologiche;");
        stmt.execute("delete from progetto_ids.negozi;");
        stmt.execute("delete from progetto_ids.categoriemerceologiche");
        for(Negozio negozio : negoziSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('"+negozio.id+"', '"+negozio.denominazione+"', '"+negozio.indirizzo.citta+"', '"+negozio.indirizzo.numero+"', '"+negozio.indirizzo.cap+"', '"+negozio.indirizzo.via+"', '"+negozio.indirizzo.provincia+"', '"+negozio.telefono+"', '"+negozio.eMail+"', '"+negozio.password+"', '0');");
        }
        for(CategoriaMerceologica categoria : categorieSalvate){
            stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('"+categoria.idCategoria+"', '"+categoria.nome+"', '0');");
        }
        stmt.close();
        conn.close();
    }

    @Test
    void getById() throws SQLException{
        inseriscoNegozi();
        assertEquals(negozi.stream().filter(i->i.id == 1).findAny().orElse(null).toString(), gestoreNegozio.getById(1).toString());
        assertEquals(negozi.stream().filter(i->i.id == 2).findAny().orElse(null).toString(), gestoreNegozio.getById(2).toString());

    }

    @Test
    void getAll() throws SQLException {
        inseriscoNegozi();
        assertEquals(negozi.toString(), gestoreNegozio.getAll().toString());
    }

    @Test
    void save() throws SQLException {
        Indirizzo indirizzo = new Indirizzo().CreaIndirizzo("ROMA", "12", "CAMERINO", "62032", "MC");
        Negozio negozio = new Negozio( "ACQUA MARINA", indirizzo, "073733313", "ACQUAMARINA@GMAIL.COM", "ACQUAMARINA!!");
        assertTrue(new GestoreNegozio().save(negozio).toString()!=null);
        }

    @Test
    void delete() throws SQLException {
        Indirizzo indirizzo = new Indirizzo().CreaIndirizzo("ROMA", "2", "CAMERINO", "62032", "MC");
        Negozio negozio = new Negozio( 4,"TECNO", indirizzo, "073733313", "TECNO@GMAIL.COM", "TECNO!!");
        CategoriaMerceologica categoria= new CategoriaMerceologica(4, "COMPUTER");
        negozio.categorie.add(categoria);
        List<Negozio> n= new ArrayList<>();
        n.add(negozio);
        gestoreNegozio.save(negozio);
        gestoreNegozio.delete(4);
        assertFalse(negozio.equals(gestoreNegozio.getById(1)));
    }

    @Test
    void getByEMail() throws SQLException {
        inseriscoNegozi();
        assertEquals(negozi.stream().filter(e->e.eMail.equals("FRUTTIVENDOLO@GMAIL.COM")).collect(Collectors.toList()).toString(), gestoreNegozio.getByEMail("FRUTTIVENDOLO@GMAIL.COM").toString());
        assertEquals(negozi.stream().filter(e->e.eMail.equals("MERCATODELLACASA@GMAIL.COM")).collect(Collectors.toList()).toString(), gestoreNegozio.getByEMail("MERCATODELLACASA@GMAIL.COM").toString());

    }

    @Test
    void getByDenominazione() throws SQLException {
        inseriscoNegozi();
        assertEquals(negozi.stream().filter(d->d.denominazione.equals("FRUTTIVENDOLO")).collect(Collectors.toList()).toString(), new GestoreNegozio().getByDenominazione("FRUTTIVENDOLO").toString());
        assertEquals(negozi.stream().filter(d->d.denominazione.equals("MERCATO DELLA CASA")).collect(Collectors.toList()).toString(), new GestoreNegozio().getByDenominazione("MERCATO DELLA CASA").toString());
    }

    @Test
    void getByIndirizzo() throws SQLException {
        inseriscoNegozi();
        assertEquals(negozi.stream().filter(i->i.indirizzo.citta.equals("CAMERINO")).collect(Collectors.toList()).toString(), new GestoreNegozio().getByIndirizzo("indirizzo.citta", "CAMERINO").toString());
        assertEquals(negozi.stream().filter(i->i.indirizzo.citta.equals("URBISAGLIA")).collect(Collectors.toList()).toString(), new GestoreNegozio().getByIndirizzo("indirizzo.citta", "URBISAGLIA").toString());
    }

    @Test
    void getByCategoria() throws SQLException {
        inseriscoNegozi();
        assertEquals(new GestoreNegozio().getByCategoria("VERDURA").toString(), negozi.stream().filter(a->a.categorie.contains(categoriaVerdura)).collect(Collectors.toList()).toString());
        assertEquals(new GestoreNegozio().getByCategoria("VASI").toString(), negozi.stream().filter(a->a.categorie.contains(categoriaVasi)).collect(Collectors.toList()).toString());
    }

    @Test
    void getByCategoriaAndCitta() throws SQLException {inseriscoNegozi();
    inseriscoNegozi();
    assertEquals(new GestoreNegozio().getByCategoriaAndCitta("VERDURA", "CAMERINO").toString(), negozi.stream().filter(a->a.categorie.contains(categoriaVerdura)).filter(b->b.indirizzo.citta.equals("CAMERINO")).collect(Collectors.toList()).toString());
    assertEquals(new GestoreNegozio().getByCategoriaAndCitta("VASI", "URBISAGLIA").toString(), negozi.stream().filter(a->a.categorie.contains(categoriaVasi)).filter(b->b.indirizzo.citta.equals("URBISAGLIA")).collect(Collectors.toList()).toString());
    }

    @Test
    void getNegoziAndCategorie() throws SQLException {
        inseriscoNegozi();
        assertEquals(new GestoreNegozio().getNegoziAndCategorie().toString(), negozi.toString());
    }
}
