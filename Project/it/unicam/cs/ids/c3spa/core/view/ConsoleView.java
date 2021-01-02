package it.unicam.cs.ids.c3spa.core.view;

import it.unicam.cs.ids.c3spa.core.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleView implements IView{

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ConsoleController consoleController = new ConsoleController();
    private IView view;

    private void hello(){
        System.out.println("C3 V 1.0\nPowered by C3 SPA");
        System.out.println("----------------");
        System.out.println("Benvenuto!");
        System.out.println("Digita la tua tipologia di utente (Cliente, Commerciante, Corriere)");
    }

    @Override
    public void start() throws IOException {
        hello();
        String login;
        do {
            login = br.readLine().toUpperCase();
            switch(login){
                case "CLIENTE":
                    view = new ViewCliente();
                    view.start();
                    break;
                case "CORRIERE":
                    view = new ViewCorriere();
                    view.start();
                    break;
                case "COMMERCIANTE":
                    view = new ViewCommerciante();
                    view.start();
                    break;
                case "EXIT":
                    System.exit(0);
                default:
                    System.err.println("Scelta non valida");
            }
        }while(!login.equals("EXIT"));
    }

    public BufferedReader getBr() {
        return br;
    }

    public ConsoleController getController() {
        return consoleController;
    }
}
