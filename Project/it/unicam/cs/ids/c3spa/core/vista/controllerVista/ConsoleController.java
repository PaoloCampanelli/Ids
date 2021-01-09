package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleController implements IController {

    private boolean isOn = true;

    public void cercaNegozi(Cliente cliente, String scelta) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        String citta = cliente.indirizzo.citta;
        List<Negozio> lnCitta = gn.getByIndirizzo("indirizzo.citta",citta);
        List<Negozio> ln = gn.getAll();
        if(scelta.equals("1")){
            trovaNegozi(ln);
        }else if(scelta.equals("2")){
            trovaNegozi( lnCitta);
        }else if(scelta.equals("3")){
            trovaCategoria(lnCitta);
        }
    }


    private void trovaNegozi(List<Negozio> lista) {
        for (Negozio negozio : lista) {
            System.out.println("    > " + negozio.denominazione);
        }
    }

    private void trovaCategoria(List<Negozio> lista){
        for (Negozio negozio : lista) {
            System.out.println("    > " + negozio.denominazione);
            if(!(negozio.categorie.isEmpty())){
                for(CategoriaMerceologica categoria : negozio.categorie){
                    System.out.println("    > "+ categoria.nome);
                }
            }else
                System.out.println("         > Quest'attività non ha categorie inserite!");
        }
    }


    private boolean trovaPromozioni() {
        System.out.println("...implementazione in corso...");
        return false;
    }

    //Le categorie di negozio risultano []
    public void checkList(String categoria, Cliente cliente) throws SQLException {
        List<Negozio> lc = new GestoreNegozio().getAll();
        CategoriaMerceologica ct = new CategoriaMerceologica(categoria);
        System.out.println("Negozio che possiedono la categoria: " + categoria);
        for (Negozio negozio : lc) {
            negozio.getCategorie();
            if (negozio.getCategorie().contains(ct)) {
                System.out.println("    > " + negozio.denominazione);
            }
        }
        System.out.println("\n");
    }

    public Indirizzo indirizzoAccount(String via, String numero, String citta, String cap, String provincia){
        Cliente cliente = new Cliente();
        return cliente.indirizzo.CreaIndirizzo(via, numero, citta, cap, provincia);
    }


    public boolean aggiungiCategoria(String nome, Negozio negozio) {
        try {
            CategoriaMerceologica c = new CategoriaMerceologica(nome);
            CategoriaMerceologica categoria = new GestoreCategoriaMerceologica().save(c);
            negozio.categorie.add(categoria);
            new GestoreNegozio().save(negozio);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean creazionePacco(Cliente cliente, Negozio negozio, Date data) throws SQLException {
        Pacco pacco = new Pacco(cliente, negozio, data);
        return true;
    }



    public void setOff(){
        this.isOn = false;
    }


    public boolean isOn() {
        return isOn;
    }
}
