package it.unicam.cs.ids.c3spa.core.vista;


import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewCommerciante extends ConsoleView {

    public void apriVista(int id) throws SQLException {
        Negozio negozio = new GestoreNegozio().getById(id);
        System.out.println("\n...Effettuato accesso come COMMERCIANTE");
        System.out.println("----------------");
        System.out.println("Bentornato "+negozio.denominazione+"!");
        sceltaCommerciante(negozio);
    }

    private void listaCommerciante() {
        System.out.println("Operazioni disponibili:     " +
                "|| EXIT -> per uscire       LOGOUT -> per tornare alla pagina principale");
        System.out.println("1. CREA ORDINE");
        System.out.println("2. AGGIUNGI CATEGORIA");
        System.out.println("3. RIMUOVI CATEGORIA");
        System.out.println("4. AGGIUNGI PROMOZIONE");
        System.out.println("5. RIMUOVI PROMOZIONE");
        System.out.println("6. ATTIVAZIONE PUBBLICITA");
        System.out.println("7. VISUALIZZA CATEGORIE ATTIVE");
        System.out.println("8. VISUALIZZA TUTTI I CLIENTI");
    }

    private void sceltaCommerciante(Negozio negozio) throws SQLException {
        while(on()){
            listaCommerciante();
            String richiesta = richiediString("Digita scelta: ").toUpperCase();
            switch (richiesta) {
                case "1": {
                    nuovoOrdine(negozio);
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
                case "8":{
                    getConsoleController().visualizzaClienti();
                    break;
                }
                case "EXIT": {
                    off();
                    break;
                }
                case "LOGOUT": {
                    System.out.println("Disconnessione da " + negozio.denominazione);
                    logout();
                    break;
                }
            }
        }
        arrivederci();
    }


    private void inserisciCategoria(Negozio negozio){
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
        for (CategoriaMerceologica categoriaMerceologica : negozio.categorie) {
            System.out.println("> " + categoriaMerceologica.nome);
        }
        System.out.println("- - - - - - - - - - - - -");
    }

    private void nuovoOrdine(Negozio negozio) throws SQLException {
        String email = richiediString("Email destinatario");
        if (getAccountController().controllaMail("CLIENTE", email)) {
            int id = getAccountController().prendiIDCliente(email);
            Cliente cliente = new GestoreCliente().getById(id);
            Date date = inserimentoData();
            boolean controllo = getConsoleController().creazionePacco(cliente, negozio, date);
            if(controllo)
                System.out.printf("Pacco creato! Intestato a: " + cliente.denominazione + " in data " + date + "%n", "dd/MM/yyyy");
            else
                System.err.println("Pacco non creato.");
        }
    }

    //Controlli implementati in futuro
    private Date inserimentoData(){
        System.out.println("Formato data dd/MM/yyyy");
        String giorno = richiediString("Giorno");
        String mese = richiediString("Mese");
        String anno = richiediString("Anno");
        String sData = giorno+"/"+mese+"/"+anno;
        try{
            return new SimpleDateFormat("dd/MM/yyyy").parse(sData);
        }catch(ParseException e){
            System.err.println("Errore! ");
            return inserimentoData();
        }
    }


}
