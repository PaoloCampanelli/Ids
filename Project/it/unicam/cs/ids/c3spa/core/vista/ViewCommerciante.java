package it.unicam.cs.ids.c3spa.core.vista;

import java.io.IOException;

public class ViewCommerciante extends ConsoleView {

    public void start(int id) throws IOException {
        System.out.println("\n...Effettuato accesso come COMMERCIANTE");
        System.out.println("----------------");
        listaCommerciante();
        String comando;
        do {
            System.out.print("> ");
            comando = getBr().readLine().toUpperCase();
        }while(!getConsoleController().executerCommerciante(comando));
    }

    private void listaCommerciante() {
        System.out.println("Operazioni disponibili: ");
        System.out.println("AGGIUNGI CATEGORIA -> AG ");
        System.out.println("RIMUOVI CATEGORIA -> RG");
        System.out.println("AGGIUNGI PROMOZIONE -> AP");
        System.out.println("RIMUOVI PROMOZIONE -> RP");
        System.out.println("CREA ORDINE -> CO");
        System.out.println("ATTIVAZIONE PUBBLICITA -> AT");
    }

}
