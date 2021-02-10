package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;
import it.unicam.cs.ids.c3spa.core.gestori.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.ids.c3spa.test.gestori.GestoreClienteTest.gestoreCliente;
import static it.unicam.cs.ids.c3spa.test.gestori.GestoreNegozioTest.gestoreNegozio;
import static org.junit.jupiter.api.Assertions.*;

public class GestorePaccoTest{

    public static GestorePacco gestorePacco = new GestorePacco();
    public static  GestoreStatoPacco gestoreStatoPacco = new GestoreStatoPacco();
    public static GestoreCorriere gestoreCorriere = new GestoreCorriere();
    List<Pacco> pacchi = new ArrayList<>();
    static List<Pacco> pacchiSalvati = new ArrayList<>();
    static  List<Negozio> negoziSalvati = new ArrayList<>();
    static List<Cliente> clientiSalvati = new ArrayList<>();
    static List<Corriere> corrieriSalvati = new ArrayList<>();
    static  List<StatoPacco> statiPacchiSalvati = new ArrayList<>();
    Pacco primoPacco = new Pacco();
    Pacco secondoPacco = new Pacco();
    static String pattern = "yyyy-MM-dd";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Cliente clientePaolo = new Cliente();
    Negozio negozioFruttivendolo = new Negozio();
    Corriere corriereDhl = new Corriere();

    @BeforeAll
    static void createDataBaseTest() throws SQLException{
        pacchiSalvati = gestorePacco.getAll();
        negoziSalvati = gestoreNegozio.getAll();
        clientiSalvati= gestoreCliente.getAll();
        statiPacchiSalvati = gestoreStatoPacco.getAll();
        corrieriSalvati = gestoreCorriere.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.pacchi");
        stmt.execute("delete from progetto_ids.statipacchi");
        stmt.execute("delete from progetto_ids.clienti");
        stmt.execute("delete from progetto_ids.corrieri");
        stmt.execute("delete from progetto_ids.negozi");
        stmt.execute("alter table pacchi AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'FRUTTIVENDOLO', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '073733333', 'FRUTTIVENDOLO@GMAIL.COM', 'FRUTTIVENDOLO!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`corrieri`  (`corriereId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'DHL', 'CAMERINO', '10', '62032', 'SETIFICIO', 'MC', '0711341111', 'DHL@GMAIL.COM', 'DHL!!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('1', 'PAOLO CAMPANELLI', 'CAMERINO', '10', '62032', 'GIOVANNI', 'MC', '1111341111', 'PAOLO.CAMPANELLI@GMAIL.COM', 'PAOLO!!');");
        stmt.execute("INSERT INTO `progetto_ids`.`pacchi` (`paccoId`, `destinatario`, `mittente`, `corriere`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `dataPreparazione`, `dataConsegnaRichiesta`, `isCancellato`) VALUES ('1', '1', '1', '1', 'URBISAGLIA', '1', '62010', 'ROMA', 'MC', '"+Servizi.dataUtilToSql(Date.from(Instant.now()))+"', '"+simpleDateFormat.format(Date.from(Instant.now().plusSeconds(400000)))+"', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`pacchi` (`paccoId`, `destinatario`, `mittente`, `corriere`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `dataPreparazione`, `dataConsegnaRichiesta`, `isCancellato`) VALUES ('2', '1', '1', '1', 'CAMERINO', '10', '62032', 'COLLE', 'MC', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(200000)))+"', '"+Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(400000)))+"', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('1', 'preparato', '"+simpleDateFormat.format(Date.from(Instant.now()))+"', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('2', 'assegnato', '"+simpleDateFormat.format(Date.from(Instant.now().plusSeconds(200000)))+"', '0');");
        stmt.close();
        conn.close();
    }

    private List<Pacco> inseriscoPacchi() throws SQLException {
        Indirizzo primoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "URBISAGLIA", "62010", "MC");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        clientePaolo= new Cliente(1,"PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Indirizzo indirizzoDhl = new Indirizzo().CreaIndirizzo("SETIFICIO", "10", "CAMERINO", "62032", "MC");
        corriereDhl = new Corriere( 1,"DHL", indirizzoDhl, "0711341111", "DHL@GMAIL.COM", "DHL!!!");
        primoPacco = new Pacco().CreaPacco(1, clientePaolo, negozioFruttivendolo, Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(400000))), primoIndirizzo);
        secondoPacco = new Pacco().CreaPacco(2, clientePaolo,negozioFruttivendolo, Servizi.dataUtilToSql(Date.from(Instant.now().plusSeconds(400000))), primoIndirizzo);
        gestorePacco.assegnaPacco(primoPacco, corriereDhl);
        gestorePacco.assegnaPacco(secondoPacco, corriereDhl);
        pacchi.add(primoPacco);
        pacchi.add(secondoPacco);
        return pacchi;
    }

    @AfterAll
    static void resetPacchi() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.pacchi");
        stmt.execute("delete from progetto_ids.statipacchi");
        stmt.execute("delete from progetto_ids.clienti");
        stmt.execute("delete from progetto_ids.negozi");
        stmt.execute("delete from progetto_ids.corrieri");
        for(Negozio negozio : negoziSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('"+negozio.id+"', '"+negozio.denominazione+"', '"+negozio.indirizzo.citta+"', '"+negozio.indirizzo.numero+"', " +
                    "'"+negozio.indirizzo.cap+"', '"+negozio.indirizzo.via+"', '"+negozio.indirizzo.provincia+"', '"+negozio.telefono+"', '"+negozio.eMail+"', '"+negozio.password+"', '0');");
        }
        for(Cliente cliente : clientiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('"+cliente.id+"', '"+cliente.denominazione+"','"+cliente.indirizzo.citta+"', '"+cliente.indirizzo.numero+"', " +
                    "'"+cliente.indirizzo.cap+"', '"+ cliente.indirizzo.via+"', '"+cliente.indirizzo.provincia+"', '"+cliente.telefono+"', '"+cliente.eMail+"', '"+cliente.password+"');");
        }
        for(Corriere corriere : corrieriSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`corrieri`  (`corriereId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('"+corriere.id+"', '"+corriere.denominazione+"', '"+corriere.indirizzo.citta+"', '"+corriere.indirizzo.numero+"', " +
                    " '"+corriere.indirizzo.cap+"', '"+corriere.indirizzo.via+"', '"+corriere.indirizzo.provincia+"', '"+corriere.telefono+"', '"+corriere.eMail+"', '"+corriere.password+"', '0');" );
        }
        for(Pacco pacco : pacchiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`pacchi` (`paccoId`, `destinatario`, `mittente`, `corriere`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `dataPreparazione`, `dataConsegnaRichiesta`, `isCancellato`) VALUES ('"+pacco.id+"', '"+pacco.destinatario+"', '"+pacco.mittente+"', '"+pacco.corriere+"'" +
                    ", '"+pacco.indirizzo.citta+"', '"+pacco.indirizzo.numero+"', '"+pacco.indirizzo.cap+"', '"+pacco.indirizzo.via+"', '"+pacco.indirizzo.provincia+"', '"+pacco.dataPreparazione+"', '"+pacco.dataConsegnaRichiesta+"', '0');");

        }
        for(StatoPacco sp : statiPacchiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('"+sp.id+"', '"+sp.stato+"', '"+sp.dataStatoPacco+"', '0');");
        }

        stmt.close();
        conn.close();
    }

    @Test
    void getById() throws SQLException {
        inseriscoPacchi();
        assertEquals(gestorePacco.getById(1).id, 1);
        assertEquals(gestorePacco.getById(1).destinatario.toString(), clientePaolo.toString());
        assertEquals(gestorePacco.getById(1).mittente.toString(), negozioFruttivendolo.toString());
        assertEquals(gestorePacco.getById(1).corriere.toString(), corriereDhl.toString());
        assertEquals(gestorePacco.getById(1).indirizzo.toString(), primoPacco.indirizzo.toString());
        assertEquals(gestorePacco.getById(1).dataPreparazione.toString(), simpleDateFormat.format(java.util.Date.from(Instant.now())));
        assertEquals(gestorePacco.getById(1).dataConsegnaRichiesta.toString(), simpleDateFormat.format(java.util.Date.from(Instant.now().plusSeconds(400000))));
        assertEquals(gestorePacco.getById(2).id, 2);
        assertEquals(gestorePacco.getById(2).destinatario.toString(), clientePaolo.toString());
        assertEquals(gestorePacco.getById(2).mittente.toString(), negozioFruttivendolo.toString());
        assertEquals(gestorePacco.getById(2).corriere.toString(), corriereDhl.toString());
        assertEquals(gestorePacco.getById(2).indirizzo.toString(), primoPacco.indirizzo.toString());
        assertEquals(gestorePacco.getById(2).dataPreparazione.toString(), simpleDateFormat.format(java.util.Date.from(Instant.now())));
        assertEquals(gestorePacco.getById(2).dataConsegnaRichiesta.toString(), simpleDateFormat.format(java.util.Date.from(Instant.now().plusSeconds(400000))));
    }

    @Test
    void getAll() throws SQLException {
        inseriscoPacchi();
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.id==1));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.indirizzo.equals(primoPacco.indirizzo)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.dataPreparazione.equals(primoPacco.dataPreparazione)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.dataConsegnaRichiesta.equals(primoPacco.dataPreparazione)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.corriere.equals(primoPacco.corriere)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.mittente.equals(primoPacco.mittente)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.destinatario.equals(primoPacco.destinatario)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.id==2));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.indirizzo.equals(secondoPacco.indirizzo)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.dataPreparazione.equals(secondoPacco.dataPreparazione)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.dataConsegnaRichiesta.equals(secondoPacco.dataPreparazione)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.corriere.equals(secondoPacco.corriere)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.mittente.equals(secondoPacco.mittente)));
        assertTrue(gestorePacco.getAll().stream().anyMatch(a->a.destinatario.equals(secondoPacco.destinatario)));
    }

    @Test
    void save() throws SQLException {
        Indirizzo nuovoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "MACERATA", "62100", "MC");
        Cliente nuovoCliente = new Cliente("CLIENTE", nuovoIndirizzo, "1", "CLIENTE@", "CLIENTE!!");
        Pacco nuovoPacco = new Pacco().CreaPacco(0, nuovoCliente, negozioFruttivendolo, Date.valueOf("2021-01-30"), nuovoIndirizzo);
        assertNotNull(gestorePacco.save(nuovoPacco).toString());
    }

    @Test
    void delete() throws SQLException {
        Indirizzo nuovoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "MACERATA", "62100", "MC");
        Cliente nuovoCliente = new Cliente("CLIENTE", nuovoIndirizzo, "1", "CLIENTE@", "CLIENTE!!");
        Pacco nuovoPacco = new Pacco().CreaPacco(0, nuovoCliente, negozioFruttivendolo, Date.valueOf("2021-01-30"), nuovoIndirizzo);
        gestorePacco.save(nuovoPacco);
        gestorePacco.delete(3);
        assertNotEquals(gestorePacco.getById(nuovoPacco.id), nuovoPacco);
    }

    @Test
    void assegnaPacco() throws SQLException {
        gestorePacco.assegnaPacco(secondoPacco, corriereDhl);
        assertEquals(corriereDhl, secondoPacco.corriere);

    }

    @Test
    void consegnaPacco() throws SQLException {
        gestorePacco.consegnaPacco(secondoPacco, corriereDhl);
        assertNotNull(secondoPacco.statiPacco.stream().filter(a->a.stato.equals(StatoPaccoEnum.consegnato)).findAny().orElse(null));
    }
}