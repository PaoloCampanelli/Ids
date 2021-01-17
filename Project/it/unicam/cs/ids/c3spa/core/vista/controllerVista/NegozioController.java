package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class NegozioController{

    public void visualizzaClienti() throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        for (Cliente cliente : lc) {
            System.out.println("    > " + cliente.denominazione + ", " + cliente.eMail + ", " + cliente.indirizzo.citta);
        }
    }

    public boolean aggiungiCategoria(String nome, Negozio negozio) {
        try {
            CategoriaMerceologica c = new CategoriaMerceologica(nome);
            CategoriaMerceologica categoria = new GestoreCategoriaMerceologica().save(c);
            negozio.categorie.add(categoria);
            new GestoreNegozio().save(negozio);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean creazionePacco(Cliente cliente, Negozio negozio, Date data) throws SQLException {
        Pacco pacco = new Pacco(cliente, negozio, data);
//        StatoPacco sp = new StatoPacco(StatoPaccoEnum.preparato, Date.from(Instant.now()));
//        new GestoreStatoPacco().save(sp);
        new GestorePacco().save(pacco);
        return true;
    }

    public void storicoOrdini(Negozio negozio) throws SQLException {
        List<Pacco> lp = new GestorePacco().storicoByNegozio(negozio);
        for (Pacco pacco : lp)
            System.out.println("    > " + pacco.id + "| in data: " + pacco.dataPreparazione+"" +
                    "\n[Destinatario: "+pacco.destinatario);
    }

    public boolean correggiData(int giorno, int meseInput, int anno) {
        LocalDate ieri = LocalDate.now().minusDays(1);
        String meseString = String.valueOf(meseInput);
        try {
            if (meseString.length() == 1) {
                LocalDate dataCorretta = LocalDate.parse(anno + "-" + "0" + meseString + "-" + giorno);
                return dataCorretta.isAfter(ieri);
            }else {
                LocalDate dataScelta = LocalDate.parse(anno + "-" + meseString + "-" + giorno);
                return dataScelta.isAfter(ieri);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Data inserita non valida");
            return false;
        }
    }

    public void stampaListe(Negozio negozio){
        out.println("- - - - CATEGORIE - - - -");
        for (CategoriaMerceologica categoriaMerceologica : negozio.categorie) {
            out.println("> " + categoriaMerceologica.nome);
        }
        out.println("- - - - - - - - - - - - -");
    }

}
