package it.unicam.cs.ids.c3spa.core;

import java.util.Scanner;

public class ConsoleView {

    private void hello(){
        System.out.println("C3 V 1.0\nPowered by C3 SPA");
        System.out.println("----------------");
        System.out.println("Benvenuto!");
    }

    public void start() {
        hello();
        lista();
        comando();
    }

    private void comando() {
        try (Scanner scanner = new Scanner(System.in)){
            String comando;
            /*
            do {
                System.out.print("> ");
                comando = scanner.nextLine();
            }while(!c.execute(comando.toUpperCase()));
             */
        }
    }

    private void lista() {
        System.out.println("----------------");
        System.out.println("Ricerca nella tua citta'!\nDigita: ");
        System.out.println("- Negozi\n- Categorie \n- Promozioni");
        System.out.println("- Nome categoria -> 'Frutta', 'Verdura', '...'");
        System.out.println("----------------"
                + "\n- EXIT");
    }


}
