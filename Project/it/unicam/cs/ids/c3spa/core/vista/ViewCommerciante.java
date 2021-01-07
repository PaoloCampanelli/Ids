package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
            listaCommerciante(comando, id);
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
        System.out.println("6. VISUALIZZA CATEGORIE ATTIVE");
    }

    private void listaCommerciante(String comando, int id) throws SQLException {
        switch(comando){
            case "6": {

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
