package it.unicam.cs.ids.c3spa.core.view;

import it.unicam.cs.ids.c3spa.core.Controller;

import java.util.Scanner;

public class ViewCliente {

    private Controller controller;

    public ViewCliente(Controller controller){
        this.controller = controller;
    }

    private void hello(){
        System.out.println("C3 V 1.0\nPowered by C3 SPA");
        System.out.println("----------------");
        System.out.println("Benvenuto!");
    }

    public void start() {
        hello();
        operazioniDisponibili();
        richiediInput();
    }

    private void richiediInput() {
        try (Scanner scanner = new Scanner(System.in)){
            String comando;
            do {
                System.out.print("> ");
                comando = scanner.nextLine();
            }while(!controller.execute(comando.toUpperCase()));
        }
    }

    private void operazioniDisponibili() {
        System.out.println("----------------");
        System.out.println("Ricerca nella tua citta'!\nDigita: ");
        System.out.println("- Negozi\n- Categorie \n- Promozioni");
        System.out.println("- Nome categoria -> 'Frutta', 'Verdura', '...'");
        System.out.println("----------------"
                + "\n- EXIT");
    }


}
