package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;

import java.io.IOException;
import java.sql.SQLException;

public class ViewCorriere extends ConsoleView {

    public void apriVista(int id) throws IOException, SQLException {
        Corriere corriere = new GestoreCorriere().getById(id);
        System.out.println("\n...Effettuato accesso come CORRIERE");
        System.out.println("----------------");
        System.out.println("Bentornato "+corriere.denominazione+"!");
        sceltaCorriere(corriere);
    }

    private void listaCorriere(){
        System.out.println("Operazioni disponibili:     || EXIT -> per uscire");
        System.out.println("1. VISUALIZZA ORDINI NON ASSEGNATI ");
        System.out.println("2. VISUALIZZA I MIEI ORDINI ");
        System.out.println("3. CONSEGNA PACCO ");
    }


    private void sceltaCorriere(Corriere corriere) throws IOException, SQLException {
        while (on()) {
            listaCorriere();
            System.out.print("> ");
            String richiesta = getBr().readLine().toUpperCase();
            switch (richiesta) {
                case "1": {
                    //Funziona va sviluppato il backend
                    getConsoleController().pacchiLiberi();
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
                default:
                    System.err.println("Comando non esistente");
            }
        }
        arrivederci();
    }

}
