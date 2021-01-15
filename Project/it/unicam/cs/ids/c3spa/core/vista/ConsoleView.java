package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.AccountController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ClienteController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ConsoleController;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;


public class ConsoleView implements IView{

    private final ConsoleController consoleController = new ConsoleController();
    private final AccountController accountController = new AccountController();
    private final ClienteController clienteController = new ClienteController();

    private void hello(){
        out.println(" - - - - - - - - - - - - ");
        out.println("       C3 V 1.0\n     Powered by C3 SPA    ");
        out.println(" - - - - - - - - - - - - ");
        out.println("Benvenuto!");
    }

    @Override
    public void start() throws SQLException {
        hello();
        autenticazione();
    }

    private void autenticazione() throws SQLException {
        String input = getConsoleController().richiediString("Sei già registrato? SI / NO     || EXIT -> per uscire");
        while(getConsoleController().isOn()){
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
                        out.println("Credenziali non valide");
                        autenticazione();
                        break;
                    }
                case "EXIT":
                    getConsoleController().setOff();
                    break;
                default:
                    out.println("Scelta non valida!");
                    autenticazione();
            }
        }
        arrivederci();
    }

    private int login(String tipologia) throws SQLException {
        String email = getConsoleController().richiediString("Inserisci email").toUpperCase();
        String password = getConsoleController().richiediString("Inserisci password");
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
        String input = getConsoleController().richiediString("Digita (1,2,3) per selezionare la tua tipologia utente (1. Cliente, 2. Corriere, 3. Commerciante)");
        do{
            switch (input) {
                case "1":
                    return "CLIENTE";
                case "2":
                    return "CORRIERE";
                case "3":
                    return "COMMERCIANTE";
                default:
                    out.println("Tipologia non valida.");
                    return tipologia();
            }
        }while(!((input).equals("1")||!(input).equals("2")||!(input).equals("3")));
    }


    private String richiediPassword(String domanda){
        out.println("PASSWORD MINIMO 6 CARATTERI!");
        String risposta;
            do {
                risposta = getConsoleController().leggiInput(domanda);
            } while (risposta.length() < 5);
            return risposta;
    }

    private String richiediEmail(String domanda, String tipologia) throws SQLException {
        String risposta;
        boolean controllo;
        do {
            risposta = getConsoleController().leggiInput("Email");
            controllo = getAccountController().controllaMail(tipologia, risposta);
            if (!risposta.contains("@"))
                out.println("\nEmail deve contenere @");
            if (controllo) {
                out.println("Email gia' esistente! Inserirne un'altra");
            } else {
                continue;
            }
        } while (!(risposta.contains("@")) || controllo);
        return risposta.toUpperCase();
    }

    private void inserimentoDati(String tipologia) throws SQLException{
        out.println("Inserisci dati:");
        String denominazione= getConsoleController().richiediString("Denominazione");
        String email = richiediEmail("Email",tipologia);
        String password = richiediPassword("Password");
        String telefono = getConsoleController().richiediString("Telefono");
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
        via = getConsoleController().richiediString("Via");
        numero = getConsoleController().richiediString("Numero");
        citta = getConsoleController().richiediString("Citta'");
        cap = getConsoleController().richiediString("cap");
        provincia = getConsoleController().richiediString("Provincia");
        return getClienteController().indirizzoAccount(via, numero, citta, cap, provincia);
    }

    public void arrivederci(){
        out.println(" - - - - - - - - - - - - ");
        out.println("  GRAZIE PER AVER USATO C3 ");
        out.println(" - - - - - - - - - - - - ");
        System.exit(1);
    }

    protected void logout() throws SQLException {
        out.println(" - - - - - - - - - - - - ");
        out.println("        LOGOUT IN CORSO.....");
        out.println(" - - - - - - - - - - - - ");
        out.println("Benvenuto!");
        autenticazione();
    }

    public ConsoleController getConsoleController() { return consoleController; }

    public AccountController getAccountController() { return accountController; }

    public ClienteController getClienteController() { return clienteController; }
}
