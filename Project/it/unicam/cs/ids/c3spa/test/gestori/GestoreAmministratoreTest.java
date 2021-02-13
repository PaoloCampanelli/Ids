package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreAmministratoreTest {

    public static GestoreAmministratore gestoreAmministratore = new GestoreAmministratore();
    List<Amministratore> amministratori = new ArrayList<>();
    public static List<Amministratore> amministratoriSalvati = new ArrayList<>();

    @BeforeAll
    static void creaDataBaseTest() throws SQLException {
        amministratoriSalvati= gestoreAmministratore.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.amministratori;");
        stmt.execute("alter table amministratori AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`amministratori` (`amministratoreId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'ANDREA CATALUFFI', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '1111341111', 'ADMIN@GMAIL.COM', 'ADMIN!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`amministratori` (`amministratoreId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('2', 'ADMIN', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '1111342111', 'ADMIN2@GMAIL.COM', 'ADMIN!!', '0');");
        stmt.close();
        conn.close();
    }

    private List<Amministratore> inseriscoAmministratori() throws SQLException {
        Indirizzo indirizzoAdmin = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO","62032",  "MC");
        Amministratore amministratore = new Amministratore(1, "ANDREA CATALUFFI", indirizzoAdmin, "1111341111", "ADMIN@GMAIL.COM", "ADMIN!!") ;
        Amministratore amministratore2 = new Amministratore(2, "ADMIN", indirizzoAdmin, "1111342111", "ADMIN2@GMAIL.COM", "ADMIN!!") ;
        amministratori.add(amministratore);
        amministratori.add(amministratore2);
        return amministratori;
    }


    @AfterAll
    static void resetCategorie() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.amministratori;");
        for(Amministratore admin : amministratoriSalvati) {
            stmt.execute("INSERT INTO `progetto_ids`.`amministratori` (`amministratoreId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('" + admin.id + "', '" + admin.denominazione + "','" + admin.indirizzo.citta + "', '" + admin.indirizzo.numero + "', " +
                    "'" + admin.indirizzo.cap + "', '" + admin.indirizzo.via + "', '" + admin.indirizzo.provincia + "', '" + admin.telefono + "', '" + admin.eMail + "', '" + admin.password + "'+ '0');");
        }
        stmt.close();
        conn.close();
    }

    @Test
    void getById() throws SQLException {
        inseriscoAmministratori();
        assertEquals(amministratori.stream().filter(a->a.id==1).findAny().orElse(null).toString(), new GestoreAmministratore().getById(1).toString());
        assertEquals(amministratori.stream().filter(a->a.id==2).findAny().orElse(null).toString(), new GestoreAmministratore().getById(2).toString());
    }

    @Test
    void getAll() throws SQLException {
        inseriscoAmministratori();
        assertEquals(amministratori.toString(), gestoreAmministratore.getAll().toString());
    }

    @Test
    void save() throws Exception {
        Indirizzo indirizzoAdmin = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO","62032",  "MC");
        Amministratore admin = new Amministratore("ADMIN", indirizzoAdmin, "3411118962", "ADMIN@MAIL.COM", "ADMIN!!");
        assertTrue(gestoreAmministratore.save(admin).toString()!=null);
    }

    @Test
    void delete() throws SQLException {
        Indirizzo indirizzoAdmin = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO","62032",  "MC");
        Amministratore admin = new Amministratore(3, "ADMIN", indirizzoAdmin, "3411118962", "ADMIN@MAIL.COM", "ADMIN!!");
        gestoreAmministratore.save(admin);
        gestoreAmministratore.delete(admin.id);
        assertNotEquals(gestoreAmministratore.getById(admin.id).toString(), admin);
    }

    @Test
    void getAmministratoreByProvincia() throws SQLException {
        inseriscoAmministratori();
        assertEquals(gestoreAmministratore.getAmministratoreByProvincia("MC").toString(), amministratori.stream().filter(p->p.indirizzo.provincia.equals("MC")).findAny().orElse(null).toString());
    }

    @Test
    void aggiungiToken() {
    }
}