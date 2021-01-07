package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.io.IOException;
import java.sql.SQLException;

public class ViewCommerciante extends ConsoleView {

    public void apriVista(int id) throws IOException, SQLException {
        Negozio negozio = new GestoreNegozio().getById(id);
        System.out.println("\n...Effettuato accesso come COMMERCIANTE");
        System.out.println("----------------");
        System.out.println("Bentornato "+negozio.denominazione+"!");
        listaCommerciante();
        String comando;
        do {
            System.out.print("> ");
            comando = getBr().readLine().toUpperCase();
            listaCommerciante(comando);
        }while(comando.isEmpty());
    }

    private void listaCommerciante() {
        System.out.println("Operazioni disponibili:     || EXIT -> per uscire");
        System.out.println("1. AGGIUNGI CATEGORIA");
        System.out.println("2. RIMUOVI CATEGORIA");
        System.out.println("3. AGGIUNGI PROMOZIONE");
        System.out.println("4. RIMUOVI PROMOZIONE");
        System.out.println("5. CREA ORDINE");
        System.out.println("6. ATTIVAZIONE PUBBLICITA");
    }

    private void listaCommerciante(String comando){
        //Negozio negozio = new GestoreNegozio().getById(id)
        switch(comando){
            case "1":{

                break;
            }
        }
    }

    /*Passo il negozio in input per aggiungere o rimuovere la categoria dal negozio selezionato
     * Controllare quando sarà disponibile GestoreNegozi
    private void aggiungiCategoria(Negozio negozio) throws IOException {
        String nome = richiediString("Nome categoria | AGGIUNTA");
        getConsoleController().aggiungiCategoria(nome, negozio);
    }

    private void rimuoviCategoria(Negozio negozio) throws IOException{
        String nome = richiediString("Nome categoria | RIMOZIONE");
        getConsoleController().rimuoviCategoria(nome, negozio);
    }
     */



}
