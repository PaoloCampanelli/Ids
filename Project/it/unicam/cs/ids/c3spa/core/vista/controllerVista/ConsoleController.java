package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleController{

    public boolean executerCliente(String comando, Cliente cliente) throws SQLException {
        if("1".equals(comando)) {
            trovaNegozi(cliente);
        }
        else if("2".equals(comando)) {
            trovaCategorie();
        }
        else if("3".equals(comando)) {
            trovaPromozioni();
        }else {
            if(checkList(comando)) {
                //TODO Stream dei negozi che hanno una data categoria;
            }else
                return false;
        }
        return false;
    }


    public boolean executerCorriere(String comando) {
        if("VISUALIZZA ORDINI".equals(comando) || "VO".equals(comando)) {
            //TODO
        }
        else if("PRESA IN CARICO".equals(comando) || "PC".equals(comando)){
            //TODO
        }
        else if("EXIT".equals(comando)) {
            System.exit(0);
        }
        return false;
    }

    private void trovaNegozi(Cliente cliente) throws SQLException {
        List<Negozio> lc = new GestoreNegozio().getAll();
        lc.stream().filter(lista -> (lista.indirizzo.cap).equals(cliente.indirizzo.cap))
                .map(Negozio::toString)
                .collect(Collectors.joining("\n"));
    }

    private boolean trovaPromozioni() {
        //TODO
        return false;
    }

    private boolean trovaCategorie() {
        //TODO
        return false;
    }

    private boolean checkList(String comando){
	/*
		Iterator<Categoria> iteratore = list.iterator();
		while(iteratore.hasNext()) {
			Categoria elemento = iteratore.next();
			if(comando.equals(elemento.getNome())) {
				return true;
			}
	*/
        return false;
    }

    public Indirizzo indirizzoAccount(String via, String numero, String citta, String cap, String provincia){
        Cliente cliente = new Cliente();
        return cliente.indirizzo.CreaIndirizzo(via, numero, citta, cap, provincia);
    }

    //Gestire il salvataggio(?)
    public void aggiungiCategoria(String nome, Negozio negozi){
        CategoriaMerceologica categoria = new CategoriaMerceologica(0, nome);
        //new GestoreNegozio.save(categoria)

    }

    public void rimuoviCategoria(String nome) {
        /*
         * ln.
         */

    }
}
