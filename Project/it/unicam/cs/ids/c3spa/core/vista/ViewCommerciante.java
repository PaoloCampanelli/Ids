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

import static java.lang.System.*;

public class ViewCommerciante extends ConsoleView {

    public void apriVista(int id) throws SQLException {
        Negozio negozio = new GestoreNegozio().getById(id);
        out.println("\n...Effettuato accesso come COMMERCIANTE"
                +"\n----------------"
                +"\nBentornato "+negozio.denominazione+"!");
        menuCommerciante(negozio);
    }

    private void listaCommerciante() {
        out.println("Operazioni disponibili:     " +
                "|| EXIT -> per uscire       LOGOUT -> per tornare alla pagina principale"
                +"\n1. CREA ORDINE"
                +"\n2. AGGIUNGI CATEGORIA"
                +"\n3. RIMUOVI CATEGORIA"
                +"\n4. AGGIUNGI PROMOZIONE"
                +"\n5. RIMUOVI PROMOZIONE"
                +"\n6. ATTIVAZIONE PUBBLICITA"
                +"\n7. VISUALIZZA CATEGORIE ATTIVE"
                +"\n8. VISUALIZZA TUTTI I CLIENTI");
    }

    private void menuCommerciante(Negozio negozio) throws SQLException {
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
                    out.println("..implementazione in corso...");
                    break;
                }
                case "4": {
                    out.println("..implementazione in corso...");
                    break;
                }
                case "5": {
                    out.println("..implementazione in corso...");
                    break;
                }
                case "6": {
                    out.println("..implementazione in corso...");
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
                    out.println("Disconnessione da " + negozio.denominazione);
                    logout();
                    break;
                }
            }
        }
    }


    private void inserisciCategoria(Negozio negozio){
        out.println("- - - - AGGIUNTA CATEGORIA - - - -");
        String nome = richiediString("Nome categoria");
        boolean controllo = getConsoleController().aggiungiCategoria(nome, negozio);
        if(controllo)
            out.println("Categoria aggiunta correttamente\n" + "- - - - - - - - - - - - - - - - -");
        else
            err.println("Errore nell'aggiunta!");
    }

    /*
    private void rimuoviCategoria(Negozio negozio) throws IOException{
        String nome = richiediString("Nome categoria | RIMOZIONE");
        getConsoleController().rimuoviCategoria(nome, negozio);
    }
     */

    private void stampaListe(Negozio negozio){
        out.println("- - - - CATEGORIE - - - -");
        for (CategoriaMerceologica categoriaMerceologica : negozio.categorie) {
            out.println("> " + categoriaMerceologica.nome);
        }
        out.println("- - - - - - - - - - - - -");
    }

    private void nuovoOrdine(Negozio negozio) throws SQLException {
        String email = richiediString("Email destinatario").toUpperCase();
        if((getAccountController().controllaMail("CLIENTE", email))){
            int id = getAccountController().prendiIDCliente(email);
            Cliente cliente = new GestoreCliente().getById(id);
            Date date = inserimentoData();
            boolean controllo = getConsoleController().creazionePacco(cliente, negozio, date);
            if(controllo)
                out.printf("Pacco creato! Intestato a: " + cliente.denominazione + " in data " + date + "%n", "dd/MM/yyyy");
            else
                err.println("Pacco non creato.");
        }else{
            out.println(email+" non esistente nel sistema!");
            out.println("- - - - - - - - - - - - -");
        }

    }

    //Controlli implementati in futuro
    private Date inserimentoData(){
        out.println("Formato data dd/MM/yyyy");
        String giorno = richiediString("Giorno");
        String mese = richiediString("Mese");
        String anno = richiediString("Anno");
        String sData = giorno+"/"+mese+"/"+anno;
        try{
            return new SimpleDateFormat("dd/MM/yyyy").parse(sData);
        }catch(ParseException e){
            err.println("Errore! ");
            return inserimentoData();
        }
    }


}
