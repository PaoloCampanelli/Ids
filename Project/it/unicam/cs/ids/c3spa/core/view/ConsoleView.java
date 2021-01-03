package it.unicam.cs.ids.c3spa.core.view;

import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.controller.AccountController;
import it.unicam.cs.ids.c3spa.core.controller.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
    public void start() throws IOException {
        hello();
        autenticazione();
        String input;
        do {
            input = br.readLine().toUpperCase();
            switch(input) {
                case "CLIENTE":{
                    view = new ViewCliente();
                    view.start();
                    break;
                }
                case "CORRIERE":{
                    view = new ViewCorriere();
                    view.start();
                    break;
                }
                case "COMMERCIANTE":
                    view = new ViewCommerciante();{
                    view.start();
                    break;
                }
                case "EXIT":
                    System.exit(0);
                default:
                    System.err.println("Scelta non valida");
            }
        }while(true);
    }

    private void autenticazione() throws IOException {
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
                    //TODO Login utente
                    break;
                default:
                    System.err.println("Scelta non valida!");
            }
        }while(!input.equals("Y"));
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
        System.out.print("> ");
        System.out.flush();
        String answer;
        do{
         answer = getBr().readLine();
        }while(answer.length()<5);
        return answer;
    }

    private void inserimentoDati(String tipologia) throws IOException {
        System.out.println("Inserisci dati:");
        String denominazione=richiediString("Denominazione");
        String email = richiediString("Email");
        String password = richiediPassword("Password");
        String telefono = richiediString("Telefono");
        Indirizzo indirizzo = inputIndirizzo();
        if(tipologia=="CLIENTE") {
            getAccountController().creatoreCliente(denominazione, email, password, telefono, indirizzo);
        }else if(tipologia=="CORRIERE") {
            getAccountController().creatoreCorriere(denominazione, email, password, telefono, indirizzo);
        }else if(tipologia=="COMMERCIANTE")
            getAccountController().creatoreCommerciante(denominazione, email, password, telefono, indirizzo);
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

    /* Potrebbe andare nel controller
    private void selezioneVista(String tipologia){
        if(tipologia=="CLIENTE")

        if(tipologia=="CORRIERE")

        if(tipologia=="COMMERCIANTE")
    }
     */

    public BufferedReader getBr() { return br; }

    protected ConsoleController getConsoleController() { return consoleController; }

    protected AccountController getAccountController() { return accountController; }
}
