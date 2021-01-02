package it.unicam.cs.ids.c3spa.core.view;

import it.unicam.cs.ids.c3spa.core.Controller;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleView implements IView{

    private Controller controller;

    private void richiediInput() {

    }

    private void hello(){
        System.out.println("C3 V 1.0\nPowered by C3 SPA");
        System.out.println("----------------");
        System.out.println("Benvenuto!\nDigita la tua tipologia utente: -Cliente -Corriere -Negoziante");
    }

    private void login(){
        try (Scanner scanner = new Scanner(System.in)){
            String login;
            do {
                System.out.print("> ");
                login = scanner.nextLine();
            }while(!login.equals("EXIT"));
        }

    }

    @Override
    public void start() throws IOException {

    }
}
