package it.unicam.cs.ids.c3spa.core.controller;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;

public class ConsoleController implements IController {

    @Override
    public boolean executerCliente(String comando) {
        if("NEGOZI".equals(comando)) {
            trovaNegozi();
        }
        else if("CATEGORIE".equals(comando)) {
            trovaCategorie();
        }
        else if("PROMOZIONI".equals(comando)) {
            trovaPromozioni();
        }
        else if("EXIT".equals(comando)) {
            System.exit(0);
        }else {
            if(checkList(comando)) {
                //TODO Stream dei negozi che hanno una data categoria;
            }else
                return false;
        }
        return false;
    }

    @Override
    public boolean executerCommerciante(String comando) {
        if("AGGIUNGI CATEGORIA".equals(comando) || "AG".contains(comando)) {
            //TODO
        }
        else if("RIMUOVI CATEGORIA".equals(comando) || "RG".contains(comando)) {
            //TODO
        }
        else if("AGGIUNGI PROMOZIONE".equals(comando) || "AP".contains(comando)) {
            //TODO
        }
        else if("RIMUOVI PROMOZIONE".equals(comando) || "RP".contains(comando)) {
            //TODO
        }
        else if("CREA ORDINE".equals(comando) || "OP".contains(comando)) {
            //TODO
        }
        else if("ATTIVA PUBBLICITA".equals(comando) || "AT".contains(comando)) {
            //TODO
        }
        else if("EXIT".equals(comando)) {
            System.exit(0);
        }
        return false;
    }

    @Override
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

    private boolean trovaNegozi() {
        //TODO
        return false;
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
        Indirizzo address;
        return address = cliente.indirizzo.CreaIndirizzo(via, numero, citta, cap, provincia);
    }

}
