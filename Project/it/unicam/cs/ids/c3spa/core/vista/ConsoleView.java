package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.AccountController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ConsoleController;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    private void autenticazione() throws SQLException {
        String input = richiediString("Sei già registrato? SI / NO     || EXIT -> per uscire");
        while(on()){
            String tipologia;
            switch (input.toUpperCase()) {
                case "NO" :
                    tipologia = tipologia();
                    inserimentoDati(tipologia);
                    break;
                case "SI":
                    tipologia = tipologia();
                    int idUtente = login(tipologia);
                    if (getAccountController().controllaID(tipologia, idUtente)) {
                        redirectView(tipologia, idUtente);
                    } else {
                        System.err.println("Credenziali non valide");
                        autenticazione();
                        break;
                    }
                case "EXIT":
                    off();
                    break;
                default:
                    System.err.println("Scelta non valida!");
                    autenticazione();
            }
        }
        arrivederci();
    }

    private int login(String tipologia) throws SQLException {
        String email = richiediEmail("Inserisci email: ");
        String password = richiediString("Inserisci password: ");
        switch (tipologia) {
            case "CLIENTE":
                if (getAccountController().controllaCliente(email, password)) {
                    int idC = getAccountController().prendiIDCliente(email, password);
                    return idC;
                }
                break;
            case "CORRIERE":
                if (getAccountController().controllaCorriere(email, password)) {
                    int idCr;
                    return idCr = getAccountController().prendiIDCorriere(email,password);
                }
            case "COMMERCIANTE":
                if(getAccountController().controllaNegozio(email, password)){
                    int idN = getAccountController().prendiIDNegozio(email,password);
                    return idN;
                }
        }
        return 0;
    }

    private String tipologia(){
        String input = richiediString("Digita (1,2,3) per selezionare la tua tipologia utente (1. Cliente, 2. Corriere, 3. Commerciante)");
        do{
            switch (input) {
                case "1":
                    return "CLIENTE";
                case "2":
                    return "CORRIERE";
                case "3":
                    return "COMMERCIANTE";
                default:
                    System.out.println("Tipologia non valida.");
                    return tipologia();
            }
        }while(!((input).equals("1")||!(input).equals("2")||!(input).equals("3")));
    }

    protected String richiediString(String question){
        String answer;
        try {
            do {
                System.out.println(question);
                System.out.print("> ");
                System.out.flush();
                answer = getBr().readLine();
                return answer;
            }while(answer.isEmpty() || answer.charAt(0) == ' ');
        } catch (IOException e) {
            return "Errore: "+e.getMessage();
        }
    }

   /* protected String richiediScelta(String question){

    }

    */

    private String richiediPassword(){
        System.out.println("Password");
        System.out.println("PASSWORD MINIMO 6 CARATTERI!");
        String answer;
        try {
            do {
                System.out.print("> ");
                System.out.flush();
                answer = getBr().readLine();
            } while (answer.length() < 5);
            return answer;
        }catch (IOException e) {
            return "Errore: "+e.getMessage();
        }
    }

    private String richiediEmail(String tipologia) throws SQLException {
        System.out.println("Email");
        String risposta;
        boolean controllo;
        try {
            do {
                System.out.print("> ");
                System.out.flush();
                risposta = getBr().readLine().toUpperCase();
                controllo = getAccountController().controllaMail(tipologia, risposta);
                if (!risposta.contains("@"))
                    System.err.println("\nEmail deve contenere @");
                if (controllo) {
                    System.err.println("Email gia' esistente! Inserirne un'altra");
                } else {
                    continue;
                }
            } while (!(risposta.contains("@")) || controllo);
            return risposta.toUpperCase();
        }catch (IOException e) {
            return "Errore: "+e.getMessage();
        }
    }

    private void inserimentoDati(String tipologia) throws SQLException{
        System.out.println("Inserisci dati:");
        String denominazione=richiediString("Denominazione");
        String email = richiediEmail(tipologia);
        String password = richiediPassword();
        String telefono = richiediString("Telefono");
        Indirizzo indirizzo = inputIndirizzo();
        switch (tipologia) {
            case "CLIENTE":
                Cliente cliente = getAccountController().creatoreCliente(denominazione, email, password, telefono, indirizzo);
                int idC = getAccountController().prendiIDCliente(email,password);
                redirectView(tipologia, idC);
                break;
            case "CORRIERE":
                Corriere corriere = getAccountController().creatoreCorriere(denominazione, email, password, telefono, indirizzo);
                int idCr = getAccountController().prendiIDCorriere(email,password);
                redirectView(tipologia, idCr);
                break;
            case "COMMERCIANTE":
                Negozio n = getAccountController().creatoreCommerciante(denominazione, email, password, telefono, indirizzo);
                int idN = getAccountController().prendiIDNegozio(email, password);
                redirectView(tipologia, idN);
                break;
        }
    }

    private void redirectView(String tipologia, int id) throws SQLException {
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

    private Indirizzo inputIndirizzo(){
        String via, numero, citta, cap, provincia;
        via = richiediString("Via");
        numero = richiediString("Numero");
        citta = richiediString("Citta'");
        cap = richiediString("cap");
        provincia = richiediString("Provincia");
        return getConsoleController().indirizzoAccount(via, numero, citta, cap, provincia);
    }

    public void arrivederci(){
        System.out.println(" - - - - - - - - - - - - ");
        System.out.println("  GRAZIE PER AVER USATO C3 ");
        System.out.println(" - - - - - - - - - - - - ");
    }

    protected boolean on(){
        return getConsoleController().isOn();
    }

    protected void off(){
        getConsoleController().setOff();
    }

    protected void logout() throws SQLException {
        System.out.println(" - - - - - - - - - - - - ");
        System.out.println("        LOGOUT IN CORSO.....");
        System.out.println(" - - - - - - - - - - - - ");
        System.out.println("Benvenuto!");
        autenticazione();
    }

    public BufferedReader getBr() { return br; }

    protected ConsoleController getConsoleController() { return consoleController; }

    protected AccountController getAccountController() { return accountController; }
}
