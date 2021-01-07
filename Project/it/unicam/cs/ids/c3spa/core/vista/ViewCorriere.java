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
        listaCorriere();
        String comando;
        do {
            System.out.print("> ");
            comando = getBr().readLine().toUpperCase();
        }while(!getConsoleController().executerCorriere(comando));
    }

    private void listaCorriere(){
        System.out.println("Operazioni disponibili: ");
        System.out.println("1. VISUALIZZA ORDINI ");
        System.out.println("2. PRESA IN CARICO ");
    }
}
