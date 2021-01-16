package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.*;

public class ViewCommerciante extends ConsoleView {

    public ViewCommerciante(ConsoleController controller) {
        super(controller);
    }

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
                +"\n8. VISUALIZZA TUTTI I CLIENTI"
                +"\n9. STORICO ORDINI");
    }

    private void menuCommerciante(Negozio negozio) throws SQLException {
        while(getConsole().isOn()){
            listaCommerciante();
            String richiesta = getInput().richiediString("Digita scelta: ").toUpperCase();
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
                    getNegozio().stampaListe(negozio);
                    break;
                }
                case "8":{
                    getNegozio().visualizzaClienti();
                    break;
                }
                case "9":{
                    getNegozio().storicoOrdiniNegozio(negozio);
                    break;
                }
                case "EXIT": {
                    getInput().setOff();
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
        String nome = getInput().richiediString("Nome categoria").toUpperCase();
        boolean controllo = getNegozio().aggiungiCategoria(nome, negozio);
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


    private void nuovoOrdine(Negozio negozio) throws SQLException {
        String email = getInput().richiediString("Email destinatario").toUpperCase();
        if((getAccount().controllaMail("CLIENTE", email))){
            int id = getAccount().prendiIDCliente(email);
            Cliente cliente = new GestoreCliente().getById(id);
            Date date = inserimentoData();
            boolean controllo = getNegozio().creazionePacco(cliente, negozio, date);
            if(controllo)
                out.printf("Pacco creato! Intestato a: " + cliente.denominazione + " in data " + date + "%n", "dd/MM/yyyy");
            else
                err.println("Pacco non creato.");
        }else{
            out.println(email+" non esistente nel sistema!");
            out.println("- - - - - - - - - - - - -");
        }

    }

    private Date inserimentoData(){
        try{
            int giorno, mese, anno;
            boolean controllo;
            do{
                out.println("INSERISCI LA DATA DI CONSEGNA (NB: Non può essere una data passata!)");
                giorno = getInput().richiediInt("Giorno");
                mese = getInput().richiediInt("Mese");
                anno = getInput().richiediInt("Anno");
                controllo = getNegozio().correggiData(giorno, mese, anno);
            }while(!controllo);
            return new SimpleDateFormat("dd/MM/yyyy").parse(giorno+"/"+mese+"/"+anno);
        }catch(ParseException e){
            out.println("Errore!");
            return inserimentoData();
        }
    }


}
