package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {

    public void cercaNegozi(Cliente cliente, String scelta) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        String citta = cliente.indirizzo.citta;
        List<Negozio> lnCitta = gn.getByIndirizzo("indirizzo.citta", citta);
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

    public Indirizzo indirizzoAccount(String via, String numero, String citta, String cap, String provincia) {
        Cliente cliente = new Cliente();
        return cliente.indirizzo.CreaIndirizzo(via, numero, citta, cap, provincia);
    }

}
