package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AmministratoreTest {
    Amministratore amministratore = new Amministratore();

    @Test
    public void  Amministratore() throws SQLException {
        Indirizzo indirizzoAdmin = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "10", "MACERATA","62100",  "MC");
        amministratore = new Amministratore(1, "amministratore", indirizzoAdmin, "3465987662", "admin@gmail.com", "passadmin") ;
        Amministratore amministratore2 = new Amministratore(2, "amministratore2", indirizzoAdmin, "3465987663", "admin2@gmail.com", "passadmin") ;
        assertNotNull(amministratore);
        assertNotNull(amministratore2);
        assertNotEquals(amministratore, amministratore2);
    }

    @Test
    public void  costruttoreAmministratore() throws SQLException {
        Indirizzo indirizzoAdmin = new Indirizzo().CreaIndirizzo("CORSO CAVOUR", "10", "MACERATA","62100",  "MC");
        amministratore = new Amministratore("amministratore", indirizzoAdmin, "3465987662", "admin@gmail.com", "passadmin") ;
        Amministratore amministratore2 = new Amministratore("amministratore2", indirizzoAdmin, "3465987663", "admin2@gmail.com", "passadmin") ;
        assertNotNull(amministratore);
        assertNotNull(amministratore2);
        assertNotEquals(amministratore, amministratore2);
    }

    @Test
    void aggiungiToken() {
        Indirizzo indirizzo = new Indirizzo().CreaIndirizzo("ROMA", "2", "CAMERINO", "62032", "MC");
        Negozio negozio = new Negozio( 4,"TECNO", indirizzo, "073733313", "TECNO@GMAIL.COM", "TECNO!!");
        amministratore.AggiungiToken(10, negozio);
        assertEquals(negozio.token, 15);
    }
}