package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreSconto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GestoreScontoTest {

    public static GestoreSconto gestoreSconto = new GestoreSconto();
    public static GestoreNegozio gestoreNegozio = new GestoreNegozio();
    public static GestoreCategoriaMerceologica gestoreCategorie = new GestoreCategoriaMerceologica();
    List<Sconto> sconti = new ArrayList<>();
    List<Negozio> negozi = new ArrayList<>();
    static List<Sconto> scontiSalvati = new ArrayList<>();
    static List<Negozio> negoziSalvati = new ArrayList<>();
    static List<CategoriaMerceologica> categorieSalvate = new ArrayList<>();
    Sconto sconto = new Sconto();
    Sconto sconto2 = new Sconto();

    @BeforeAll
    static void createDataBaseTest() throws SQLException {
        scontiSalvati = gestoreSconto.getAll();
        negoziSalvati = gestoreNegozio.getAll();
        categorieSalvate = gestoreCategorie.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.sconti;");
        stmt.execute("delete from progetto_ids.negozi;");
        stmt.execute("delete from progetto_ids.categorieMerceologiche;");
        stmt.execute("alter table sconti AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`sconti` (`scontoId`, `tipo`, `dataInizio`, `dataFine`, `negozioId`, `categoriaMerceologicaId`, `isCancellato`) VALUES ('1', '20%', '"+ Servizi.dataUtilToSql(Date.from(Instant.now()))+"', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(432000)))+"', '1', '1', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`sconti` (`scontoId`, `tipo`, `dataInizio`, `dataFine`, `negozioId`, `categoriaMerceologicaId`, `isCancellato`) VALUES ('2', '25%', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(200000)))+"', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(800000)))+"', '1', '2', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'FRUTTIVENDOLO', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '073733333', 'FRUTTIVENDOLO@GMAIL.COM', 'FRUTTIVENDOLO!!', '0');");
        stmt.close();
        conn.close();
    }

    private List<Sconto> inseriscoSconti(){
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1, "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        CategoriaMerceologica categoriaFrutta = new CategoriaMerceologica(1, "FRUTTA");
        CategoriaMerceologica categoriaMele = new CategoriaMerceologica(2, "MELE");
        sconto = new Sconto("20%", Servizi.dataUtilToSql(Date.from(Instant.now())), Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(432000))), negozioFruttivendolo, categoriaFrutta);
        sconto2 = new Sconto("25%", Servizi.dataUtilToSql(Date.from(Instant.now())), Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(432000))), negozioFruttivendolo, categoriaMele);
        sconti.add(sconto);
        sconti.add(sconto2);

        return sconti;
    }

    @AfterAll
    static void resetPubblicita() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.categorieMerceologiche;");
        stmt.execute("delete from progetto_ids.negozi;");
        stmt.execute("delete from progetto_ids.sconti");
        for(CategoriaMerceologica categoria :categorieSalvate){
            stmt.execute("INSERT INTO `progetto_ids`.`categoriemerceologiche` (`categoriaId`, `nome`, `isCancellato`) VALUES ('"+categoria.idCategoria+"', '"+categoria.nome+"', '0');");
        }
        for(Negozio negozio : negoziSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('"+negozio.id+"', '"+negozio.denominazione+"', '"+negozio.indirizzo.citta+"', '"+negozio.indirizzo.numero+"', '"+negozio.indirizzo.cap+"', '"+negozio.indirizzo.via+"', '"+negozio.indirizzo.provincia+"', '"+negozio.telefono+"', '"+negozio.eMail+"', '"+negozio.password+"', '0');");
        }
        for(Sconto sc : scontiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`sconti` ((`scontoId`, `tipo`, `dataInizio`, `dataFine`, `negozioId`, `categoriaMerceologica`, `isCancellato`) VALUES('"+sc.id+"', '"+sc.tipo+"', '"+sc.dataInizio+"', '"+sc.dataFine+"', '"+sc.negozio+"', '"+sc.categoriaMerceologica+"', '0');");
        }
        stmt.close();
        conn.close();
    }

    @Test
    void getById() throws SQLException {
        inseriscoSconti();
      //  assertEquals(sconti.stream().filter(i->i.id== 0).findAny().orElse(null).toString(), gestoreSconto.getById(0).toString());
    }

    @Test
    void getAll() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void getScontiAttivi() {
    }

    @Test
    void getScontiAttiviByNegozio() {
    }
}