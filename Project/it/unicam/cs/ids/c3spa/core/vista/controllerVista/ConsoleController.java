package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleController implements IController {

    private boolean isOn = true;

    //Negozio{Token=0, categorie[]...} Lista categorie vuota
    public void trovaNegozi(Cliente cliente) throws SQLException {
        List<Negozio> lc = new GestoreNegozio().getByIndirizzo("indirizzo.citta",cliente.indirizzo.citta);
        Iterator<Negozio> iteraNegozi = lc.iterator();
        while (iteraNegozi.hasNext()) {
                Negozio negozio = iteraNegozi.next();
                System.out.println("    > " + negozio.denominazione + " -> Categorie: ");
                //NON STAMPA LE CATEGORIE
                Iterator<CategoriaMerceologica> iteraCategorie = negozio.getCategorie().iterator();
                while(iteraCategorie.hasNext()){
                    System.out.print(iteraCategorie.next().nome);
                }
            }
    }

    private boolean trovaPromozioni() {
        System.out.println("...implementazione in corso...");
        return false;
    }

    //Le categorie di negozio risultano []
    public void checkList(String categoria, Cliente cliente) throws SQLException {
        List<Negozio> lc = new GestoreNegozio().getByIndirizzo("indirizzo.citta", cliente.indirizzo.citta);
        CategoriaMerceologica ct = new CategoriaMerceologica(categoria);
        Iterator<Negozio> iteraNegozi = lc.iterator();
        while(iteraNegozi.hasNext()) {
            Negozio negozio = iteraNegozi.next();
            if (negozio.categorie.contains(ct)) {
                System.out.println("    > " + negozio.denominazione);
            }
        }
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


    public void setOff(){
        this.isOn = false;
    }


    public boolean isOn() {
        return isOn;
    }
}
