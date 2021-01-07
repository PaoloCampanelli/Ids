package it.unicam.cs.ids.c3spa.core.vista;


import it.unicam.cs.ids.c3spa.core.Negozio;
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
        sceltaCommerciante();
    }

    private void listaCommerciante() {
        System.out.println("Operazioni disponibili:     || EXIT -> per uscire");
        System.out.println("1. AGGIUNGI CATEGORIA");
        System.out.println("2. RIMUOVI CATEGORIA");
        System.out.println("3. AGGIUNGI PROMOZIONE");
        System.out.println("4. RIMUOVI PROMOZIONE");
        System.out.println("5. CREA ORDINE");
        System.out.println("6. ATTIVAZIONE PUBBLICITA");
        System.out.println("7. VISUALIZZA CATEGORIE ATTIVE");
    }

    private void sceltaCommerciante() throws SQLException, IOException {

        while(on()){
            System.out.print("> ");
            String richiesta = getBr().readLine().toUpperCase();
            switch (richiesta) {
                case "1": {
                    break;
                }
                case "2": {
                    break;
                }
                case "3": {

                    break;
                }
                case "4": {
                    break;
                }
                case "5": {
                    break;
                }
                case "6": {
                    break;
                }
                case "7": {
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
