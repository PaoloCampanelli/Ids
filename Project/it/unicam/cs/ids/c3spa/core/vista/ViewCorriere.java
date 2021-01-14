package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;

import java.sql.SQLException;

import static java.lang.System.*;

public class ViewCorriere extends ConsoleView {

    public void apriVista(int id) throws SQLException {
        Corriere corriere = new GestoreCorriere().getById(id);
        out.println("\n...Effettuato accesso come CORRIERE"
                +"\n----------------"
                +"\nBentornato "+corriere.denominazione+"!");
        menuCorriere(corriere);
    }

    private void listaCorriere(){
        out.println("Operazioni disponibili:     || EXIT -> per uscire       LOGOUT -> per tornare alla pagina principale"
                +"\n1. VISUALIZZA ORDINI NON ASSEGNATI "
                +"\n2. VISUALIZZA I MIEI ORDINI "
                +"\n3. CONSEGNA PACCO ");
    }


    private void menuCorriere(Corriere corriere) throws SQLException {
        while (on()) {
            listaCorriere();
            String richiesta = richiediString("Digita scelta: ").toUpperCase();
            switch (richiesta) {
                case "1": {
                    selezioneOrdine(corriere);
                    break;
                }
                case "2": {
                    getConsoleController().ordiniCorriere(corriere);
                    break;
                }
                case "3": {
                    effettuaConsegna(corriere);
                    break;
                }
                case "EXIT": {
                    off();
                    break;
                }
                case "LOGOUT": {
                    out.println("Disconnessione da "+corriere.denominazione);
                    logout();
                    break;
                }
                default:
                    out.println("Comando non esistente");
                    break;
            }
        }
    }


    private void selezioneOrdine(Corriere corriere) throws SQLException {
        if(getConsoleController().pacchiLiberi()) {
            String richiesta;
            do {
                richiesta = richiediString("Vuoi selezionare un pacco?" +
                        "\n-> Digita: SI per confermare, NO per tornare indietro").toUpperCase();
                if (richiesta.equals("SI")) {
                    out.println("SELEZIONE ORDINE ");
                    int idPacco = richiediInt("ID PACCO: ");
                    if (getConsoleController().controllaPacco(idPacco, corriere))
                        out.println("Pacco preso in carico!");
                    else
                        out.println("Errore nell'assegnamento!");
                }
            } while(!richiesta.equals("NO"));
            menuCorriere(corriere);
        }else
            menuCorriere(corriere);
    }

    public void effettuaConsegna(Corriere corriere) throws SQLException {
        boolean consegnato = true;
        getConsoleController().ordiniCorriere(corriere);
        int idPacco = richiediInt("Digita l'ID del pacco consegnato");
        while(consegnato) {
            if (getConsoleController().consegnaPacco(idPacco, corriere)) {
                out.println("IL PACCO E' STATO CONSEGNATO CORRETTAMENTE!");
                consegnato = false;
            } else
                out.println("Errore!");
        }
    }



}




