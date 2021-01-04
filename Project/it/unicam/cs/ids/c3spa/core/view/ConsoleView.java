package it.unicam.cs.ids.c3spa.core.view;

import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.controller.AccountController;
import it.unicam.cs.ids.c3spa.core.controller.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;


public class ConsoleView implements IView{

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ConsoleController consoleController = new ConsoleController();
    private AccountController accountController = new AccountController();
    private IView view;

    private void hello(){
        System.out.println("C3 V 1.0\nPowered by C3 SPA");
        System.out.println("----------------");
        System.out.println("Benvenuto!");
    }

    @Override
    public void start() throws IOException, SQLException {
        hello();
        autenticazione();
    }

    private void autenticazione() throws IOException, SQLException {
        System.out.println("Sei già registrato? Y/N");
        String input;
        String tipologia;
        do {
            input = br.readLine().toUpperCase();
            switch(input) {
                case "N":
                    tipologia = tipologia();
                    inserimentoDati(tipologia);
                    break;
                case "Y":
                    System.err.println("IN FASE DI SVILUPPO!");
                    System.exit(0);
                    //TODO Login utente
                    break;
                default:
                    System.err.println("Scelta non valida!");
            }
        }while(input.isEmpty() || input.charAt(0)==' ');
    }

    private String tipologia() throws IOException {
        String input;
        System.out.println("Inserisci la tua tipologia utente (Cliente, Corriere, Commerciante)");
        input = br.readLine().toUpperCase();
        switch (input) {
            case "CLIENTE":
                return "CLIENTE";
            case "CORRIERE":
                return "CORRIERE";
            case "COMMERCIANTE":
                return "COMMERCIANTE";
            default:
                System.err.println("Errore nell'inserimento tipologia");
                tipologia();
                return "";
        }
    }

    private String richiediString(String question) throws IOException {
        String answer;
        do {
            System.out.println(question);
            System.out.print("> ");
            System.out.flush();
            answer = getBr().readLine();
        }while(answer.isEmpty() || answer.charAt(0) == ' ');
            return answer;

    }

    private String richiediPassword(String question) throws IOException {
        System.out.println(question);
        System.out.println("PASSWORD MINIMO 6 CARATTERI!");
        String answer;
        do{
            System.out.print("> ");
            System.out.flush();
            answer = getBr().readLine();
        }while(answer.length()<5);
        return answer;
    }

    private void inserimentoDati(String tipologia) throws IOException, SQLException {
        System.out.println("Inserisci dati:");
        String denominazione=richiediString("Denominazione");
        String email = richiediString("Email");
        String password = richiediPassword("Password");
        String telefono = richiediString("Telefono");
        Indirizzo indirizzo = inputIndirizzo();
        if(tipologia.equals("CLIENTE")) {
            getAccountController().creatoreCliente(denominazione, email, password, telefono, indirizzo);
            view = new ViewCliente();
            view.start();
        }else if(tipologia.equals("CORRIERE")) {
            getAccountController().creatoreCorriere(denominazione, email, password, telefono, indirizzo);
            view = new ViewCorriere();
            view.start();
        }else if(tipologia.equals("COMMERCIANTE")) {
            view = new ViewCommerciante();
            view.start();
            getAccountController().creatoreCommerciante(denominazione, email, password, telefono, indirizzo);
        }
    }

    private Indirizzo inputIndirizzo() throws IOException {
        String via, numero, citta, cap, provincia;
        via = richiediString("Via");
        numero = richiediString("Numero");
        citta = richiediString("Citta'");
        cap = richiediString("cap");
        provincia = richiediString("Provincia");
        return getConsoleController().indirizzoAccount(via, numero, citta, cap, provincia);
    }

    public BufferedReader getBr() { return br; }

    protected ConsoleController getConsoleController() { return consoleController; }

    protected AccountController getAccountController() { return accountController; }
}
