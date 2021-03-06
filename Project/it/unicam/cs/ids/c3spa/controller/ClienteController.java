package it.unicam.cs.ids.c3spa.controller;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.gestori.GestorePacco;

import java.sql.SQLException;
import java.util.List;

public class ClienteController{

    public void cercaNegozi(Cliente cliente, String scelta) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        String citta = cliente.indirizzo.citta;
        List<Negozio> lnCitta = gn.getByIndirizzo(citta);
        List<Negozio> ln = gn.getAll();
        List<Negozio> lnCategoria = gn.getNegoziAndCategorie();
        switch (scelta) {
            case "1":
                trovaNegozi(ln);
                break;
            case "2":
                trovaNegozi(lnCitta);
                break;
            case "3":
                trovaCategoria(lnCategoria);
                break;
        }
    }

    public void cercaNegoziCategoria(String categoria, String citta) throws SQLException {
        List<Negozio> ln = new GestoreNegozio().getByCategoriaAndCitta(categoria, citta);
        for (Negozio negozio : ln) {
            System.out.println("         > " + negozio.denominazione);
        }
    }

    private void trovaNegozi(List<Negozio> lista) {
        for (Negozio negozio : lista) {
            System.out.println("    > " + negozio.denominazione);
        }
    }

    private void trovaCategoria(List<Negozio> lista) {
        for (Negozio negozio : lista) {
            System.out.println("    > " + negozio.denominazione);
            System.out.println(negozio.categorie.size());
            for (CategoriaMerceologica categoria : negozio.categorie)
                System.out.println("     > " + categoria.nome);
        }
    }

    private boolean trovaPromozioni() {
        System.out.println("...implementazione in corso...");
        return false;
    }

    public void checkList(String categoria) throws SQLException {
        List<Negozio> lc = new GestoreNegozio().getByCategoria(categoria);
        System.out.println("Negozi che possiedono la categoria: " + categoria);
        for (Negozio negozio : lc) {
            System.out.println("    > " + negozio.denominazione);
            System.out.println("        > " + negozio.indirizzo);
        }
    }

    public void storicoOrdini(Cliente cliente) throws SQLException {
        List<Pacco> lp = new GestorePacco().storicoByCliente(cliente);
        if(lp.size()==0){
            System.out.println("NON HAI EFFETTUATO NESSUN ORDINE!");
        }
        for(Pacco pacco : lp){
            System.out.println("    > "+pacco.id+"| spedito da: "+pacco.mittente+" il "+pacco.dataPreparazione+"\n" +
                    "        Consegna prevista "+pacco.dataConsegnaRichiesta);
        }
    }

    public void statoOrdine(int id) throws SQLException {
        Pacco pacco = new GestorePacco().getById(id);
        if(pacco.statiPacco.size()==1){
            System.out.println("  "+id+"| STATO: IN PREPARAZIONE");
        }else if(pacco.statiPacco.size()==2){
            System.out.println("  "+id+"| STATO: ASSEGNATO AL CORRIERE");
        }else if(pacco.statiPacco.size()==3) {
            System.out.println("  "+id + "| STATO: CONSEGNATO");
        }
    }

    public Cliente prendiCliente(String email) throws SQLException {
        GestoreCliente gc = new GestoreCliente();
        List<Cliente> lc = gc.getAll();
        int id = lc.stream().filter(c -> c.eMail.equals(email)).findAny().get().id;
        return gc.getById(id);
    }

    public boolean cercaCliente(String email) throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        return lc.stream().anyMatch(c -> c.eMail.equals(email));
    }
}
