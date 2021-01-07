package it.unicam.cs.ids.c3spa.core.vista;


import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

public class ViewCommerciante extends ConsoleView {

    public void apriVista(int id) throws IOException, SQLException {
        Negozio negozio = new GestoreNegozio().getById(id);
        System.out.println("\n...Effettuato accesso come COMMERCIANTE");
        System.out.println("----------------");
        System.out.println("Bentornato "+negozio.denominazione+"!");
        sceltaCommerciante(negozio);
    }

    private void listaCommerciante() {
        System.out.println("Operazioni disponibili:     || EXIT -> per uscire");
        System.out.println("1. CREA ORDINE");
        System.out.println("2. AGGIUNGI CATEGORIA");
        System.out.println("3. RIMUOVI CATEGORIA");
        System.out.println("4. AGGIUNGI PROMOZIONE");
        System.out.println("5. RIMUOVI PROMOZIONE");
        System.out.println("6. ATTIVAZIONE PUBBLICITA");
        System.out.println("7. VISUALIZZA CATEGORIE ATTIVE");
    }

    private void sceltaCommerciante(Negozio negozio) throws SQLException, IOException {
        while(on()){
            listaCommerciante();
            System.out.print("> ");
            String richiesta = getBr().readLine().toUpperCase();
            switch (richiesta) {
                case "1": {
                    System.out.println("..implementazione in corso...");
                    break;
                }
                case "2": {
                    inserisciCategoria(negozio);
                    break;
                }
                case "3": {
                    System.out.println("..implementazione in corso...");
                    break;
                }
                case "4": {
                    System.out.println("..implementazione in corso...");
                    break;
                }
                case "5": {
                    System.out.println("..implementazione in corso...");
                    break;
                }
                case "6": {
                    System.out.println("..implementazione in corso...");
                    break;
                }
                case "7": {
                    stampaListe(negozio);
                    break;
                }
                case "EXIT": {
                    off();
                    break;
                }
            }
        }
        arrivederci();
    }


    private void inserisciCategoria(Negozio negozio) throws IOException, SQLException {
        System.out.println("- - - - AGGIUNTA CATEGORIA - - - -");
        String nome = richiediString("Nome categoria");
        boolean controllo = getConsoleController().aggiungiCategoria(nome, negozio);
        if(controllo)
            System.out.println("Categoria aggiunta correttamente\n" + "- - - - - - - - - - - - - - - - -");
        else
            System.err.println("Errore nell'aggiunta!");
    }

    /*
    private void rimuoviCategoria(Negozio negozio) throws IOException{
        String nome = richiediString("Nome categoria | RIMOZIONE");
        getConsoleController().rimuoviCategoria(nome, negozio);
    }
     */


    private void stampaListe(Negozio negozio){
        System.out.println("- - - - CATEGORIE - - - -");
        Iterator<CategoriaMerceologica> iterator = negozio.categorie.iterator();
        while(iterator.hasNext()){
            System.out.println("> "+iterator.next().nome);
        }
        System.out.println("- - - - - - - - - - - - -");
    }

}
