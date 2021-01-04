package it.unicam.cs.ids.c3spa.core.vista;

import java.io.IOException;

public class ViewCorriere extends ConsoleView {

    public void apriVista(int id) throws IOException {
        System.out.println("\n...Effettuato accesso come CORRIERE");
        System.out.println("----------------");
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
