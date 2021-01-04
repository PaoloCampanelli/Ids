package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.AccountController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;


public class ConsoleView implements IView{

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final ConsoleController consoleController = new ConsoleController();
    private final AccountController accountController = new AccountController();
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

    @Override
    public void apriVista(int id) throws IOException, SQLException {

    }

    private void autenticazione() throws IOException, SQLException {
        System.out.println("Sei già registrato? Y/N");
        String input;
        String tipologia;
        int id;
        do {
            input = br.readLine().toUpperCase();
            switch(input) {
                case "N":
                    tipologia = tipologia();
                    inserimentoDati(tipologia);
                    break;
                case "Y":
                    tipologia = tipologia();
                    if(login(tipologia)){
                        System.out.println("Bentornato!");
                    }else{
                        System.err.println("Credenziali non valide");
                        autenticazione();
                        break;
                    }
                default:
                    System.err.println("Scelta non valida!");
            }
        }while(input.trim().isEmpty());
    }

    /*
     Login utilizzato solo per vedere la corretta esecuzione dello switch
     Riga 64: richiamare metodo controller che ritorna un boolean
        - True se email e password esistono per qualche account
        - False altrimenti
     */
    private boolean login(String tipologia) throws IOException, SQLException {
        String email = richiediString("Inserisci email: ");
        String password = richiediString("Inserisci password: ");
        switch (tipologia) {
            case "CLIENTE":
                if (getAccountController().controllaCliente(email, password)) {
                    redirectView(tipologia, getAccountController().prendiID(email, password));
                    System.out.println("Bentornato!");
                    return true;
                }
                break;
            case "CORRIERE":
                System.err.println("...implementazione in corso");
                System.exit(0);
            case "COMMERCIANTE":
                System.err.println("...implementazione in corso");
                System.exit(0);
        }
        return false;
    }

    private String tipologia() throws IOException {
        String input = richiediString("Inserisci la tua tipologia utente (Cliente, Corriere, Commerciante)").toUpperCase();
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
        switch (tipologia) {
            case "CLIENTE":
                getAccountController().creatoreCliente(denominazione, email, password, telefono, indirizzo);
                redirectView(tipologia, getAccountController().prendiID(email, password));
                break;
            case "CORRIERE":
                getAccountController().creatoreCorriere(denominazione, email, password, telefono, indirizzo);
                //redirect view
                break;
            case "COMMERCIANTE":
                getAccountController().creatoreCommerciante(denominazione, email, password, telefono, indirizzo);
                //redirect view
                break;
        }
    }

    private void redirectView(String tipologia, int id) throws IOException, SQLException {
        switch (tipologia) {
            case "CLIENTE":
                view = new ViewCliente();
                view.apriVista(id);
                break;
            case "CORRIERE":
                view = new ViewCorriere();
                view.apriVista(id);
                break;
            case "COMMERCIANTE":
                view = new ViewCommerciante();
                view.apriVista(id);
                break;
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
