package it.unicam.cs.ids.c3spa.controller;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.gestori.GestorePacco;

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

    public boolean creazionePacco(Cliente cliente, Negozio negozio, Date data, Indirizzo indirizzo) throws SQLException {
        Pacco pacco = new Pacco(cliente, negozio, data, cliente.indirizzo);
        new GestorePacco().save(pacco);
        return true;
    }

    public void storicoOrdini(Negozio negozio) throws SQLException {
        List<Pacco> lp = new GestorePacco().storicoByNegozio(negozio);
        for (Pacco pacco : lp)
            System.out.println("    > " + pacco.id + "| in data: " + pacco.dataPreparazione+"" +
                    "\n[Destinatario: "+pacco.destinatario.denominazione+" Corriere: "+pacco.corriere.denominazione+"]");
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

    public Negozio prendiNegozio(String email) throws SQLException {
        GestoreNegozio gc = new GestoreNegozio();
        List<Negozio> lc = gc.getAll();
        int id = lc.stream().filter(c -> c.eMail.equals(email)).findAny().get().id;
        return gc.getById(id);
    }

    public CategoriaMerceologica prendiCategoria(Negozio negozio, int idCategoria) {
        return negozio.categorie.stream().filter(c -> c.idCategoria == idCategoria).findAny().orElse(null);
    }
}
