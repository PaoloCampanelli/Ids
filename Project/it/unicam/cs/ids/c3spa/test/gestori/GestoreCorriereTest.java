package it.unicam.cs.ids.c3spa.test.gestori;


import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreCorriereTest {
    public static GestoreCorriere gestoreCorriere = new GestoreCorriere();
    static List<Corriere> corrieriSalvati = new ArrayList<>();
    List<Corriere> corrieri = new ArrayList<>();

    @BeforeAll
    static void creaDataBaseTest() throws SQLException {
        corrieriSalvati = gestoreCorriere.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.corrieri;");
        stmt.execute("alter table clienti AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`corrieri` (`corriereId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'BARTOLINI', 'MACERATA', '1', '62100', 'CORSO CAVOUR', 'MC', '07111111111', 'BARTOLINI@GMAIL.COM', 'BARTOLINI!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`corrieri`  (`corriereId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('2', 'DHL', 'CAMERINO', '10', '62032', 'SETIFICIO', 'MC', '0711341111', 'DHL@GMAIL.COM', 'DHL!!!', '0');");
        stmt.close();
        conn.close();
    }

    @AfterAll
    static void resetCorrieri() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.corrieri;");
        for(Corriere corriere : corrieriSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`corrieri`  (`corriereId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('"+corriere.id+"', '"+corriere.denominazione+"', '"+corriere.indirizzo.citta+"', '"+corriere.indirizzo.numero+"', " +
                    " '"+corriere.indirizzo.cap+"', '"+corriere.indirizzo.via+"', '"+corriere.indirizzo.provincia+"', '"+corriere.telefono+"', '"+corriere.eMail+"', '"+corriere.password+"', '0');" );
        }
        stmt.close();
        conn.close();
    }

    private List<Corriere> inseriscoCorrieri() {
        Indirizzo indirizzoBartolini = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "1", "MACERATA","62100",  "MC");
        Corriere corriereBartolini= new Corriere(1, "BARTOLINI", indirizzoBartolini, "07111111111", "BARTOLINI@GMAIL.COM", "BARTOLINI!!");
        Indirizzo indirizzoDhl = new Indirizzo().CreaIndirizzo("SETIFICIO", "10", "CAMERINO", "62032", "MC");
        Corriere corriereDhl = new Corriere( 2, "DHL", indirizzoDhl, "0711341111", "DHL@GMAIL.COM", "DHL!!!");
        corrieri.add(corriereBartolini);
        corrieri.add(corriereDhl);
        return corrieri;
    }


    @Test
    void getById() throws SQLException {
        inseriscoCorrieri();
        assertEquals(gestoreCorriere.getById(1).toString(), corrieri.stream().filter(i->i.id==1).findAny().orElse(null).toString());
        assertEquals(gestoreCorriere.getById(2).toString(), corrieri.stream().filter(i->i.id==2).findAny().orElse(null).toString());
    }

    @Test
    void getAll() throws SQLException {
        inseriscoCorrieri();
        assertEquals(gestoreCorriere.getAll().toString(), corrieri.toString());
    }

    @Test
    void save() throws SQLException {
        Indirizzo nuovoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "MACERATA", "62100", "MC");
        Corriere nuovoCorriere = new Corriere(3, "CORRIERE", nuovoIndirizzo, "3298665474", "CORRIERE@GMAIL.COM", "CORRIERE!!");
        assertTrue(new GestoreCorriere().save(nuovoCorriere).toString()!=null);
    }

    @Test
    void delete() throws SQLException {
        Indirizzo ind = new Indirizzo().CreaIndirizzo("ROMA", "12", "MACERATA", "62100", "MC");
        Corriere c= new Corriere(4, "CORRIEREDACANCELLARE", ind, "1", "CORRIEREDACANCELLARE@", "CORRIEREDACANCELLARE");
        new GestoreCorriere().save(c);
        new GestoreCorriere().delete(4);
        assertFalse(c.equals(gestoreCorriere.getById(4)));
    }
}