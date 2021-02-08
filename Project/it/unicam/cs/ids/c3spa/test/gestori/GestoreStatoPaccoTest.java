package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreStatoPacco;
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

import static it.unicam.cs.ids.c3spa.test.gestori.GestoreClienteTest.gestoreCliente;
import static it.unicam.cs.ids.c3spa.test.gestori.GestoreNegozioTest.gestoreNegozio;
import static org.junit.jupiter.api.Assertions.*;

public class GestoreStatoPaccoTest {

    public static GestoreStatoPacco gestoreStatoPacco = new GestoreStatoPacco();
    List<StatoPacco> statiPacchi = new ArrayList<>();
    static List<StatoPacco> statiPacchiSalvati = new ArrayList<>();

    @BeforeAll
    static void createDataBaseTest() throws SQLException {

        statiPacchiSalvati = gestoreStatoPacco.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.statipacchi");
        stmt.execute("alter table statipacchi AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('1', 'preparato', '"+Servizi.dataUtilToSql(Date.from(Instant.now()))+"', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('2', 'assegnato', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(200000)))+"', '0');");
        stmt.close();
        conn.close();
    }

    private List<StatoPacco> inseriscoStatiPacchi() throws SQLException {
      StatoPacco primoStato = new StatoPacco(1, StatoPaccoEnum.preparato, Servizi.dataUtilToSql(Date.from(Instant.now())));
      StatoPacco secondoStato = new StatoPacco(2, StatoPaccoEnum.assegnato, Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(200000))));
      statiPacchi.add(primoStato);
      statiPacchi.add(secondoStato);
      return statiPacchi;
    }

    @AfterAll
    static void resetPacchi() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.statipacchi");
        for(StatoPacco sp : statiPacchiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('"+sp.id+"', '"+sp.stato+"', '"+sp.dataStatoPacco+"', '0');");
        }

        stmt.close();
        conn.close();
    }

    @Test
    void getById() throws SQLException {
        inseriscoStatiPacchi();
        assertEquals(gestoreStatoPacco.getById(1).toString(), statiPacchi.stream().filter(i->i.id==1).findAny().orElse(null).toString());
    }

    @Test
    void getAll() throws SQLException {
        inseriscoStatiPacchi();
        assertEquals(gestoreStatoPacco.getAll().toString(), statiPacchi.toString());
    }

    @Test
    void save() throws SQLException {
        StatoPacco sp = new StatoPacco(3, StatoPaccoEnum.consegnato, Date.from(Instant.now()));
        assertNotEquals(gestoreStatoPacco.save(sp), null);
    }

    @Test
    void delete() throws SQLException {
        StatoPacco sp = new StatoPacco(3, StatoPaccoEnum.consegnato, Date.from(Instant.now()));
        gestoreStatoPacco.save(sp);
        gestoreStatoPacco.delete(3);
        assertFalse(sp.equals(gestoreStatoPacco.getById(3)));
    }
}