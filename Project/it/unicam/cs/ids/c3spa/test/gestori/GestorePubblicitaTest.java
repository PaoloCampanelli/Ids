package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePubblicita;
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

class GestorePubblicitaTest {

    public static GestorePubblicita gestorePubblicita = new GestorePubblicita();
    public static GestoreNegozio gestoreNegozio = new GestoreNegozio();
    List<Pubblicita> pubblicita = new ArrayList<>();
    List<Negozio> negozi = new ArrayList<>();
    static List<Pubblicita> pubblicitaSalvate = new ArrayList<>();
    static List<Negozio> negoziSalvati = new ArrayList<>();
    Pubblicita pub = new Pubblicita();
    Pubblicita pub2 = new Pubblicita();

    @BeforeAll
    static void createDataBaseTest() throws SQLException {
        pubblicitaSalvate = gestorePubblicita.getAll();
        negoziSalvati = gestoreNegozio.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.pubblicita;");
        stmt.execute("delete from progetto_ids.negozi;");
        stmt.execute("alter table pubblicita AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`pubblicita` (`pubblicitaId`, `dataInizio`, `dataFine`, `negozioId`, `isCancellato`) VALUES ('1', '"+Servizi.dataUtilToSql(Date.from(Instant.now()))+"', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(432000)))+"', '1', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`pubblicita` (`pubblicitaId`, `dataInizio`, `dataFine`, `negozioId`, `isCancellato`) VALUES ('2', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(200000)))+"', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(800000)))+"', '1', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'FRUTTIVENDOLO', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '073733333', 'FRUTTIVENDOLO@GMAIL.COM', 'FRUTTIVENDOLO!!', '0');");
        stmt.close();
        conn.close();
    }

    private List<Pubblicita> inseriscoPubblicita(){
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1, "FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        pub = new Pubblicita(1, Servizi.dataUtilToSql(Date.from(Instant.now())), Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(432000))), negozioFruttivendolo);
        pub2 = new Pubblicita(2, Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(200000))), Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(800000))), negozioFruttivendolo);
        pubblicita.add(pub);
        pubblicita.add(pub2);
        negozi.add(negozioFruttivendolo);

        return pubblicita;
    }

    @AfterAll
    static void resetPubblicita() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.negozi;");
        stmt.execute("delete from progetto_ids.pubblicita");
        for(Negozio negozio : negoziSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('"+negozio.id+"', '"+negozio.denominazione+"', '"+negozio.indirizzo.citta+"', '"+negozio.indirizzo.numero+"', '"+negozio.indirizzo.cap+"', '"+negozio.indirizzo.via+"', '"+negozio.indirizzo.provincia+"', '"+negozio.telefono+"', '"+negozio.eMail+"', '"+negozio.password+"', '0');");
        }
        for(Pubblicita pub : pubblicitaSalvate){
            stmt.execute("INSERT INTO `progetto_ids`.`pubblicita` (`pubblicitaId`, `dataInizio`, `dataFine`, `negozioId`, `isCancellato`) VALUES ('"+pub.id+"', '"+pub.dataInizio+"', '"+pub.dataFine+"', '"+pub.negozio+"', '0');");
        }
        stmt.close();
        conn.close();
    }

    @Test
    void getById() throws SQLException {
        inseriscoPubblicita();
        assertEquals(pubblicita.stream().filter(i->i.id== 1).findAny().orElse(null).toString(), gestorePubblicita.getById(1).toString());
        assertEquals(pubblicita.stream().filter(i->i.id== 2).findAny().orElse(null).toString(), gestorePubblicita.getById(2).toString());
    }

    @Test
    void getAll() {
        inseriscoPubblicita();

    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void getPubblicitaAttive() {
    }

    @Test
    void getNegoziConPubblicitaAttiva() {
    }

    @Test
    void orderByPubblicita() {
    }
}