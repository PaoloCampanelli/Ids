package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GestoreClienteTest {
    public static GestoreCliente gestoreCliente = new GestoreCliente();
    static List<Cliente> clientiSalvati = new ArrayList<>();
    List<Cliente> clienti = new ArrayList<>();
    Cliente clienteSara = new Cliente();
    Cliente clientePaolo= new Cliente();

    @BeforeAll
    static void creaDataBaseTest() throws SQLException {
        clientiSalvati = gestoreCliente.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.clienti;");
        stmt.execute("alter table clienti AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('2', 'PAOLO CAMPANELLI', 'CAMERINO', '10', '62032', 'GIOVANNI', 'MC', '1111341111', 'PAOLO.CAMPANELLI@GMAIL.COM', 'PAOLO!!');");
        stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('1', 'SARA COMPAGNUCCI', 'URBISAGLIA', '14', '62010', 'BASSO', 'MC', '34111111111', 'SARA.COMPAGNUCCI@GMAIL.COM', 'SARA!!');");
        stmt.close();
        conn.close();
    }

    @AfterAll
    static void resetClienti() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.clienti;");
        for(Cliente cliente : clientiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('"+cliente.id+"', '"+cliente.denominazione+"','"+cliente.indirizzo.citta+"', '"+cliente.indirizzo.numero+"', " +
                    "'"+cliente.indirizzo.cap+"', '"+ cliente.indirizzo.via+"', '"+cliente.indirizzo.provincia+"', '"+cliente.telefono+"', '"+cliente.eMail+"', '"+cliente.password+"');");
        }
        stmt.close();
        conn.close();
    }

    private List<Cliente> inseriscoClienti() throws SQLException {
        Indirizzo indirizzoSara = new Indirizzo().CreaIndirizzo("BASSO", "14", "URBISAGLIA","62010",  "MC");
        clienteSara.CreaAccount(1, "SARA COMPAGNUCCI", indirizzoSara, "34111111111", "SARA.COMPAGNUCCI@GMAIL.COM", "SARA!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        clientePaolo.CreaAccount(2, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        clienti.add(clienteSara);
        clienti.add(clientePaolo);
        return clienti;
    }

    @Test
    void getById() throws SQLException {
        inseriscoClienti();
        assertEquals(clienti.stream().filter(i->i.id == 1).findAny().orElse(null).toString(), new GestoreCliente().getById(1).toString());
        assertEquals(clienti.stream().filter(i->i.id == 2).findAny().orElse(null).toString(), new GestoreCliente().getById(2).toString());
    }

    @Test
    void getAll() throws SQLException {
    inseriscoClienti();
        assertEquals(clienti.toString(), gestoreCliente.getAll().toString());
    }

    @Test
    void save() throws SQLException {
        Indirizzo nuovoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "MACERATA", "62100", "MC");
        Cliente nuovoCliente = new Cliente("CLIENTE", nuovoIndirizzo, "1", "CLIENTE@", "CLIENTE!!");
        assertTrue(new GestoreCliente().save(nuovoCliente).toString()!=null);

    }

    @Test
    void delete() throws SQLException {
        Indirizzo ind = new Indirizzo().CreaIndirizzo("ROMA", "12", "MACERATA", "62100", "MC");
        Cliente cl = new Cliente("CLIENTEDACANCELLARE", ind, "1", "CLIENTEDACANCELLARE@", "CLIENTE2!!");
        new GestoreCliente().save(cl);
        new GestoreCliente().delete(cl.id);
        assertFalse(cl.equals(gestoreCliente.getById(cl.id)));
    }

    @Test
    void getByEMail() throws SQLException {
    inseriscoClienti();
    assertEquals(clienti.stream().filter(e->e.eMail.equals("SARA.COMPAGNUCCI@GMAIL.COM")).collect(Collectors.toList()).toString(), new GestoreCliente().getByEMail("SARA.COMPAGNUCCI@GMAIL.COM").toString());
    assertEquals(clienti.stream().filter(e->e.eMail.equals("PAOLO.CAMPANELLI@GMAIL.COM")).collect(Collectors.toList()).toString(), new GestoreCliente().getByEMail("PAOLO.CAMPANELLI@GMAIL.COM").toString());
    }

    @Test
    void getByDenominazione() throws SQLException {
    inseriscoClienti();
        assertEquals(clienti.stream().filter(e->e.denominazione.equals("SARA COMPAGNUCCI")).collect(Collectors.toList()).toString(), new GestoreCliente().getByDenominazione("SARA COMPAGNUCCI").toString());
        assertEquals(clienti.stream().filter(e->e.denominazione.equals("PAOLO CAMPANELLI")).collect(Collectors.toList()).toString(), new GestoreCliente().getByDenominazione("PAOLO CAMPANELLI").toString());

    }
}
