package it.unicam.cs.ids.c3spa.test.gestori;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreBase;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.ids.c3spa.test.gestori.GestoreClienteTest.gestoreCliente;
import static it.unicam.cs.ids.c3spa.test.gestori.GestoreNegozioTest.gestoreNegozio;

public class GestorePaccoTest{

    public static GestorePacco gestorePacco = new GestorePacco();
    List<Pacco> pacchi = new ArrayList<>();
    static List<Pacco> pacchiSalvati = new ArrayList<>();
    static  List<Negozio> negoziSalvati = new ArrayList<>();
    static List<Cliente> clientiSalvati = new ArrayList<>();

    @BeforeAll
    static void createDataBaseTest() throws SQLException{
        pacchiSalvati = gestorePacco.getAll();
        negoziSalvati = gestoreNegozio.getAll();
        clientiSalvati= gestoreCliente.getAll();
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.pacco_statipacco");
        stmt.execute("delete from progetto_ids.pacchi");
        //stmt.execute("delete from progetto_ids.statipacchi");
        stmt.execute("delete from progetto_ids.clienti");
        stmt.execute("delete from progetto_ids.negozi");
        stmt.execute("alter table pacchi AUTO_INCREMENT = 1;");
        stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('1', 'FRUTTIVENDOLO', 'CAMERINO', '1', '62032', 'ROMA', 'MC', '073733333', 'FRUTTIVENDOLO@GMAIL.COM', 'FRUTTIVENDOLO!!', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('1', 'PAOLO CAMPANELLI', 'CAMERINO', '10', '62032', 'GIOVANNI', 'MC', '1111341111', 'PAOLO.CAMPANELLI@GMAIL.COM', 'PAOLO!!');");
        stmt.execute("INSERT INTO `progetto_ids`.`pacchi` (`paccoId`, `destinatario`, `mittente`, `corriere`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `dataPreparazione`, `dataConsegnaRichiesta`, `isCancellato`) VALUES ('1', '1', '1', '1', 'URBISAGLIA', '1', '62010', 'ROMA', 'MC', '2021-01-24', '2021-02-01', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`pacchi` (`paccoId`, `destinatario`, `mittente`, `corriere`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `dataPreparazione`, `dataConsegnaRichiesta`, `isCancellato`) VALUES ('2', '1', '1', '1', 'CAMERINO', '10', '62032', 'COLLE', 'MC', '2021-01-23', '2021-01-30', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`pacco_statipacco` (`paccoId`, `statoId`) VALUES ('1', '1);");
        stmt.execute("INSERT INTO `progetto_ids`.`pacco_statipacco` (`paccoId`, `statoId`) VALUES ('2', '1');");
        stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('2', 'preparato', '2021-01-24', '0');");
        stmt.execute("INSERT INTO `progetto_ids`.`statipacchi` (`statoId`, `stato`, `data`, `isCancellato`) VALUES ('2', 'preparato', '2021-01-24', '0');");
        stmt.close();
        conn.close();
    }

    private List<Pacco> inseriscoPacchi() throws SQLException {
        Indirizzo primoIndirizzo = new Indirizzo().CreaIndirizzo("ROMA", "1", "URBVISAGLIA", "62010", "MC");
        Indirizzo indirizzoFruttivendolo = new Indirizzo().CreaIndirizzo("ROMA", "1", "CAMERINO", "62032", "MC");
        Negozio negozioFruttivendolo = new Negozio( 1,"FRUTTIVENDOLO", indirizzoFruttivendolo, "073733333", "FRUTTIVENDOLO@GMAIL.COM", "FRUTTIVENDOLO!!");
        Indirizzo indirizzoPaolo = new Indirizzo().CreaIndirizzo("GIOVANNI", "10", "CAMERINO", "62032", "MC");
        Cliente clientePaolo= new Cliente(1, "PAOLO CAMPANELLI", indirizzoPaolo, "1111341111", "PAOLO.CAMPANELLI@GMAIL.COM", "PAOLO!!");
        Pacco primoPacco = new Pacco().CreaPacco(1, clientePaolo, negozioFruttivendolo, Date.valueOf("2021-02-01"), primoIndirizzo);
        StatoPacco primoStato = new StatoPacco(StatoPaccoEnum.preparato, Date.valueOf("2021-01-24"));
        primoPacco.statiPacco.add(primoStato);
        Indirizzo secondoIndirizzo = new Indirizzo().CreaIndirizzo("COLLE", "10", "CAMERINO", "62032", "MC");
        Pacco secondoPacco = new Pacco().CreaPacco(2, clientePaolo,negozioFruttivendolo, Date.valueOf("2021-01-30"), secondoIndirizzo);
        StatoPacco secondoStato = new StatoPacco(StatoPaccoEnum.preparato, Date.valueOf("2021-01-24"));
        secondoPacco.statiPacco.add(secondoStato);
        pacchi.add(primoPacco);
        pacchi.add(secondoPacco);
        return pacchi;
    }

    @AfterAll
    static void resetPacchi() throws SQLException {
        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt = conn.createStatement();
        stmt.execute("delete from progetto_ids.pacco_statipacco");
        stmt.execute("delete from progetto_ids.pacchi");
       // stmt.execute("delete from progetto_ids.statipacchi");
        stmt.execute("delete from progetto_ids.clienti");
        stmt.execute("delete from progetto_ids.negozi");
        for(Negozio negozio : negoziSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`negozi` (`negozioId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`, `isCancellato`) VALUES ('"+negozio.id+"', '"+negozio.denominazione+"', '"+negozio.indirizzo.citta+"', '"+negozio.indirizzo.numero+"', " +
                    "'"+negozio.indirizzo.cap+"', '"+negozio.indirizzo.via+"', '"+negozio.indirizzo.provincia+"', '"+negozio.telefono+"', '"+negozio.eMail+"', '"+negozio.password+"', '0');");
        }
        for(Cliente cliente : clientiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`clienti` (`clienteId`, `denominazione`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `telefono`, `eMail`, `password`) VALUES ('"+cliente.id+"', '"+cliente.denominazione+"','"+cliente.indirizzo.citta+"', '"+cliente.indirizzo.numero+"', " +
                    "'"+cliente.indirizzo.cap+"', '"+ cliente.indirizzo.via+"', '"+cliente.indirizzo.provincia+"', '"+cliente.telefono+"', '"+cliente.eMail+"', '"+cliente.password+"');");
        }
        for(Pacco pacco : pacchiSalvati){
            stmt.execute("INSERT INTO `progetto_ids`.`pacchi` (`paccoId`, `destinatario`, `mittente`, `corriere`, `indirizzo.citta`, `indirizzo.numero`, `indirizzo.cap`, `indirizzo.via`, `indirizzo.provincia`, `dataPreparazione`, `dataConsegnaRichiesta`, `isCancellato`) VALUES ('"+pacco.id+"', '"+pacco.destinatario+"', '"+pacco.mittente+"', '"+pacco.corriere+"'" +
                    ", '"+pacco.indirizzo.citta+"', '"+pacco.indirizzo.numero+"', '"+pacco.indirizzo.cap+"', '"+pacco.indirizzo.via+"', '"+pacco.indirizzo.provincia+"', '"+pacco.dataPreparazione+"', '"+pacco.dataConsegnaRichiesta+"', '0');");

        }
        stmt.close();
        conn.close();
    }

    @Test
    void getById() {
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
    void getByDestinatario() {
    }

    @Test
    void getByMittente() {
    }

    @Test
    void getByCorriere() {
    }

    @Test
    void getPacchiSenzaCorriere() {
    }

    @Test
    void assegnaPacco() {
    }

    @Test
    void consegnaPacco() {
    }

    @Test
    void storicoByCorriere() {
    }

    @Test
    void storicoByCliente() {
    }

    @Test
    void storicoByNegozio() {
    }
}