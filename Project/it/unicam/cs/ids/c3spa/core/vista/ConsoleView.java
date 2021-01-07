package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.AccountController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ConsoleController;

import javax.swing.text.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;


public class ConsoleView implements IView{

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final ConsoleController consoleController = new ConsoleController();
    private final AccountController accountController = new AccountController();

    private void hello(){
        System.out.println(" - - - - - - - - - - - - ");
        System.out.println("       C3 V 1.0\n     Powered by C3 SPA    ");
        System.out.println(" - - - - - - - - - - - - ");
        System.out.println("Benvenuto!");
    }

    @Override
    public void start() throws IOException, SQLException {
        hello();
        autenticazione();
    }

    private void autenticazione() throws IOException, SQLException {
        System.out.println("Sei già registrato? SI / NO     || EXIT -> per uscire");
        String input;
        String tipologia;
        do {
            input = br.readLine().toUpperCase();
            switch(input) {
                case "NO":
                    tipologia = tipologia();
                    inserimentoDati(tipologia);
                    break;
                case "SI":
                    tipologia = tipologia();
                    if(login(tipologia)){
                            continue;
                    }else{
                        System.err.println("Credenziali non valide");
                        autenticazione();
                        break;
                    }
                case "EXIT":
                    System.exit(0);
                    break;
                default:
                    System.err.println("Scelta non valida!");
                    autenticazione();
            }
        }while(input.isEmpty());
    }

    private boolean login(String tipologia) throws IOException, SQLException {
        String email = richiediString("Inserisci email: ");
        String password = richiediString("Inserisci password: ");
        switch (tipologia) {
            case "CLIENTE":
                if (getAccountController().controllaCliente(email, password)) {
                    int idC = getAccountController().prendiIDCliente(email, password);
                    redirectView(tipologia, idC);
                }
                break;
            case "CORRIERE":
                if (getAccountController().controllaCorriere(email, password)) {
                    int idCr = getAccountController().prendiIDCorriere(email,password);
                    redirectView(tipologia, idCr);
                }
            case "COMMERCIANTE":
                if(getAccountController().controllaNegozio(email, password)){
                    int idN = getAccountController().prendiIDNegozio(email,password);
                    redirectView(tipologia, idN);
                }
        }
        return false;
    }

    private String tipologia() throws IOException {
        String input = richiediString("Digita (1,2,3) per selezionare la tua tipologia utente (1. Cliente, 2. Corriere, 3. Commerciante)");
        switch (input) {
            case "1":
                return "CLIENTE";
            case "2":
                return "CORRIERE";
            case "3":
                return "COMMERCIANTE";
            default:
                System.err.println("Errore nell'inserimento tipologia");
                tipologia();
                return "";
        }
    }

    protected String richiediString(String question) throws IOException {
        String answer;
        do {
            System.out.println(question);
            System.out.print("> ");
            System.out.flush();
            answer = getBr().readLine();
        }while(answer.isEmpty() || answer.charAt(0) == ' ');
            return answer;

    }

    private String richiediPassword() throws IOException {
        System.out.println("Password");
        System.out.println("PASSWORD MINIMO 6 CARATTERI!");
        String answer;
        do{
            System.out.print("> ");
            System.out.flush();
            answer = getBr().readLine();
        }while(answer.length()<5);
        return answer;
    }

    private String richiediEmail(String tipologia) throws IOException, SQLException {
        System.out.println("Email");
        String risposta;
        boolean controllo;
        do{
            System.out.print("> ");
            System.out.flush();
            risposta = getBr().readLine();
            controllo = getAccountController().controllaMail(tipologia, risposta);
            if(!risposta.contains("@"))
                System.err.println("\nEmail deve contenere @");
            if(controllo) {
                System.err.println("Email gia' esistente! Inserirne un'altra");
            }else{
                continue;
            }
        }while(!(risposta.contains("@")) || controllo);
        return risposta;
    }

    private void inserimentoDati(String tipologia) throws IOException, SQLException {
        System.out.println("Inserisci dati:");
        String denominazione=richiediString("Denominazione");
        String email = richiediEmail(tipologia);
        String password = richiediPassword();
        String telefono = richiediString("Telefono");
        Indirizzo indirizzo = inputIndirizzo();
        switch (tipologia) {
            case "CLIENTE":
                Cliente cliente = getAccountController().creatoreCliente(denominazione, email, password, telefono, indirizzo);
                redirectView(tipologia, cliente.id);
                break;
            case "CORRIERE":
                Corriere corriere = getAccountController().creatoreCorriere(denominazione, email, password, telefono, indirizzo);
                redirectView(tipologia, corriere.id);
                break;
            case "COMMERCIANTE":
                Negozio n = getAccountController().creatoreCommerciante(denominazione, email, password, telefono, indirizzo);
                redirectView(tipologia, n.id);
                break;
        }
    }

    private void redirectView(String tipologia, int id) throws IOException, SQLException {
        switch (tipologia) {
            case "CLIENTE":
                ViewCliente viewCliente = new ViewCliente();
                viewCliente.apriVista(id);
                break;
            case "CORRIERE":
               ViewCorriere viewCorriere = new ViewCorriere();
               viewCorriere.apriVista(id);
               break;
            case "COMMERCIANTE":
                ViewCommerciante viewCommerciante = new ViewCommerciante();
                viewCommerciante.apriVista(id);
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
