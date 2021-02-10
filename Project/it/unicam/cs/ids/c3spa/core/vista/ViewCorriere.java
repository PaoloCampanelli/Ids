package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.controller.Console.ConsoleController;


import java.sql.SQLException;

import static java.lang.System.*;

public class ViewCorriere extends ConsoleView {

    public ViewCorriere(ConsoleController controller) {
        super(controller);
    }

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
                +"\n3. CONSEGNA PACCO "
                +"\n4. STORICO ORDINI "
                +"\n-------------------"
                +"\n10. MODIFICA DATI");
    }

    private void menuCorriere(Corriere corriere) throws SQLException {
        while (getConsole().isOn()) {
            listaCorriere();
            String richiesta = getInput().richiediString("Digita scelta: ").toUpperCase();
            switch (richiesta) {
                case "1": {
                    selezioneOrdine(corriere);
                    break;
                }
                case "2": {
                    getCorriere().ordiniCorriere(corriere);
                    break;
                }
                case "3": {
                    effettuaConsegna(corriere);
                    break;
                }
                case "4": {
                    getCorriere().storicoOrdini(corriere);
                    break;
                }
                case "10": {
                    sceltaModifica(corriere);
                    break;
                }
                case "EXIT": {
                    getConsole().setOff();
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
        if(getCorriere().pacchiLiberi()) {
            out.println("SELEZIONE ORDINE");
            int idPacco = getInput().richiediInt("Digita l'ID:      || 0 -> per tornare indietro");
            if(idPacco==0)
                menuCorriere(corriere);
            else {
                if (getCorriere().controllaPacco(idPacco, corriere))
                    out.println("Pacco preso in carico!");
                else
                    out.println("Errore nell'assegnamento!");
            }
        }
        menuCorriere(corriere);
    }

    public void effettuaConsegna(Corriere corriere) throws SQLException {
        getCorriere().ordiniCorriere(corriere);
        int idPacco = getInput().richiediInt("Digita l'ID:      || 0 -> per tornare indietro");
        if(idPacco==0)
            menuCorriere(corriere);
        else{
            if(!getCorriere().consegnaPacco(idPacco, corriere)) {
                out.println("ERRORE! Pacco NON consegnato!");
            }else
                out.println("IL PACCO E' STATO CONSEGNATO CORRETTAMENTE!");
        }
    }


}








