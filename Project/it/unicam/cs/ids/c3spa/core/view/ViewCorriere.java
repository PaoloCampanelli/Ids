package it.unicam.cs.ids.c3spa.core.view;

import java.io.IOException;

public class ViewCorriere extends ConsoleView {

    @Override
    public void start() throws IOException {
        System.out.println("----------------");
        listaCorriere();
        String comando;
        do {
            System.out.print("> ");
            comando = getBr().readLine().toUpperCase();
        }while(!getController().executerCorriere(comando));
    }

    private void listaCorriere(){
        System.out.println("Operazioni disponibili: ");
        System.out.println("VISUALIZZA ORDINI -> VO");
        System.out.println("PRESA IN CARICO -> PC");
    }
}