package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
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
    public GestoreCliente gestoreCliente = new GestoreCliente();
    List<Cliente> clienti = new ArrayList<>();
    Cliente clienteSara = new Cliente();
    Cliente clientePaolo= new Cliente();

@BeforeAll
    static void creaDataBaseTest() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.clienti;");
        stmt.execute("alter table clienti AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('2', 'PAOLO CAMPANELLI', 'CAMERINO', '10', '62032', 'GIOVANNI', 'MC', '1111341111', 'PAOLO.CAMPANELLI@GMAIL.COM', 'PAOLO!!');");
        stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('1', 'SARA COMPAGNUCCI', 'URBISAGLIA', '14', '62010', 'BASSO', 'MC', '34111111111', 'SARA.COMPAGNUCCI@GMAIL.COM', 'SARA!!');");
        stmt.close();
        conn.close();
    }


    private List<Cliente> inseriscoClientiTest() throws SQLException {
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
        inseriscoClientiTest();
        assertEquals(clienti.stream().filter(i->i.id == 1).findAny().orElse(null).toString(), new GestoreCliente().getById(1).toString());
        assertEquals(clienti.stream().filter(i->i.id == 2).findAny().orElse(null).toString(), new GestoreCliente().getById(2).toString());
    }

    @Test
    void getAll() throws SQLException {
    inseriscoClientiTest();
        assertEquals(clienti.toString(), gestoreCliente.getAll().toString());
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void getByEMail() throws SQLException {
    inseriscoClientiTest();
    assertEquals(clienti.stream().filter(e->e.eMail.equals("SARA.COMPAGNUCCI@GMAIL.COM")).collect(Collectors.toList()).toString(), new GestoreCliente().getByEMail("SARA.COMPAGNUCCI@GMAIL.COM").toString());
    assertEquals(clienti.stream().filter(e->e.eMail.equals("PAOLO.CAMPANELLI@GMAIL.COM")).collect(Collectors.toList()).toString(), new GestoreCliente().getByEMail("PAOLO.CAMPANELLI@GMAIL.COM").toString());
    }

    @Test
    void getByDenominazione() throws SQLException {
    inseriscoClientiTest();
        assertEquals(clienti.stream().filter(e->e.denominazione.equals("SARA COMPAGNUCCI")).collect(Collectors.toList()).toString(), new GestoreCliente().getByDenominazione("SARA COMPAGNUCCI").toString());
        assertEquals(clienti.stream().filter(e->e.denominazione.equals("PAOLO CAMPANELLI")).collect(Collectors.toList()).toString(), new GestoreCliente().getByDenominazione("PAOLO CAMPANELLI").toString());

    }
}
