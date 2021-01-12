package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;

import java.sql.SQLException;

public class ViewCorriere extends ConsoleView {

    public void apriVista(int id) throws SQLException {
        Corriere corriere = new GestoreCorriere().getById(id);
        System.out.println("\n...Effettuato accesso come CORRIERE");
        System.out.println("----------------");
        System.out.println("Bentornato "+corriere.denominazione+"!");
        menuCorriere(corriere);
    }

    private void listaCorriere(){
        System.out.println("Operazioni disponibili:     || EXIT -> per uscire       LOGOUT -> per tornare alla pagina principale");
        System.out.println("1. VISUALIZZA ORDINI NON ASSEGNATI ");
        System.out.println("2. VISUALIZZA I MIEI ORDINI ");
        System.out.println("3. CONSEGNA PACCO ");
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
                    getConsoleController().ordiniCorriere(corriere.denominazione);
                    break;
                }
                case "3": {
                    System.out.println("..implementazione in corso...");
                    break;
                }
                case "EXIT": {
                    off();
                    break;
                }
                case "LOGOUT": {
                    System.out.println("Disconnessione da "+corriere.denominazione);
                    logout();
                    break;
                }
                default:
                    System.err.println("Comando non esistente");
            }
        }
        arrivederci();
    }


    private void selezioneOrdine(Corriere corriere) throws SQLException {
        getConsoleController().pacchiLiberi();
        String richiesta;
        do {
            richiesta = richiediBoolean("Vuoi selezionare un pacco?" +
                    "   -> Digita: S per confermare, N per tornare indietro").toUpperCase();
            if (richiesta.equals("S")) {
                System.out.println("SELEZIONE ORDINE ");
                int idPacco = richiediInt("ID PACCO: ");
                if (getConsoleController().controllaPacco(idPacco, corriere))
                    System.out.println("Pacco preso in carico!");
                else
                    System.err.println("Errore nell'assegnamento!");
            }
        }while(!richiesta.equals("N"));
        menuCorriere(corriere);
    }

}




