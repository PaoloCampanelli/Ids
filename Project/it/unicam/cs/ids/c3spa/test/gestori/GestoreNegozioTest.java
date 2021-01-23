package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class GestoreNegozioTest {
    public static GestoreNegozio gestoreNegozio = new GestoreNegozio();
    static List<Negozio> negoziSalvati = new ArrayList<>();
    static List<CategoriaMerceologica> categorieSalvate = new ArrayList<>();
   // static List<Integer> negozi_categorieMerceologiche = new ArrayList<>();
    List<Negozio> negozi = new ArrayList<>();

    @BeforeAll
    static void creaDataBaseTest() throws SQLException {
        negoziSalvati = gestoreNegozio.getAll();
        categorieSalvate = new GestoreCategoriaMerceologica().getAll();
        Negozio n = new Negozio();
        GestoreCategoriaMerceologica gcm = new GestoreCategoriaMerceologica();

        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        PreparedStatement ps;

        stmt.execute("delete from progetto_ids.negozio_categoriemerceologiche;");
        stmt.execute("delete from progetto_ids.categoriemerceologiche");
        stmt.execute("delete from progetto_ids.negozi;");
        stmt.execute("alter table negozi AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'FRUTTIVENDOLO', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '073733333', 'FRUTTIVENDOLO@GMAIL.COM', 'FRUTTIVENDOLO!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('2', 'MERCATO DELLA CASA', 'URBISAGLIA', '10', '62010', 'CONVENTO', 'MC', '073333333', 'MERCATODELLACASA@GMAIL.COM', 'MERCATODELLACASA!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('1', 'VERDURA', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('2', 'VASI', '0');");
       /* ps = conn.prepareStatement("SELECT * FROM progetto_ids.negozio_categoriemerceologiche WHERE negozioId = ?"); // creo sempre uno statement sulla
        ps.setInt(1, n.id);
        ResultSet rs = ps.executeQuery(); // faccio la query su uno statement
        while (rs.next() == true) {
            n.categorie.add(gcm.getById(rs.getInt(2)));
        }*/
        stmt.execute("INSERT INTO `progetto_ids`.`negozio_categoriemerceologiche` (`negozioId`, `categoriaId`) VALUES ('1', '1');");
        stmt.execute("INSERT INTO `progetto_ids`.`negozio_categoriemerceologiche` (`negozioId`, `categoriaId`) VALUES ('2', '2');");
        stmt.close();
        conn.close();
    }

    private List<Negozio> inseriscoNegozi(){
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        CategoriaMerceologica categoriaVerdura = new CategoriaMerceologica(1, "VERDURA");
        negozioFruttivendolo.categorie.add(categoriaVerdura);
        Indirizzo indirizzoMercatoDellaCasa = new Indirizzo().CreaIndirizzo("CONVENTO", "10", "URBISAGLIA", "62010", "MC");
        Negozio negozioMercatoDellaCasa = new Negozio( 2, "MERCATO DELLA CASA", indirizzoMercatoDellaCasa, "073333333", "MERCATODELLACASA@GMAIL.COM", "MERCATODELLACASA!!");
        CategoriaMerceologica categoriaVasi = new CategoriaMerceologica(2, "VASI");
        negozioMercatoDellaCasa.categorie.add(categoriaVasi);
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
     /*   Indirizzo nuovoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "MACERATA", "62100", "MC");
        Negozio nuovoNegozio = new Negozio( "NEGOZIO", nuovoIndirizzo, "3473243333", "NEGOZIO@", "NEGOZIO!!" );
        assertEquals(new GestoreNegozio().save(nuovoNegozio).toString(), gestoreNegozio.getById(nuovoNegozio.id).toString());*/
    }

    @Test
    void delete() {
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
      //  assertEquals(categorieMerceologiche.stream().filter(a->a.nome.equals("VERDURA")).collect(Collectors.toList()).toString(), new GestoreNegozio().getByCategoria("VERDURA".toString()));

    }

    @Test
    void getByCategoriaAndCitta() {
    }

    @Test
    void getNegoziAndCategorie() {
    }
}
