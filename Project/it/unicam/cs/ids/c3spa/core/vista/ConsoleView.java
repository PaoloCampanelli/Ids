package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.*;

import java.sql.SQLException;

import static java.lang.System.*;


public class ConsoleView implements IView{

    private ConsoleController consoleController;

    public ConsoleView(ConsoleController controller){
        this.consoleController = controller;
    }

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

    private void autenticazione() throws SQLException{
        String input = getInput().richiediString("Sei già registrato? SI / NO     || EXIT -> per uscire");
        while(getConsole().isOn()){
            String tipologia;
            switch (input.toUpperCase()) {
                case "NO" :
                    tipologia = tipologia();
                    inserimentoDati(tipologia);
                    break;
                case "SI":
                    tipologia = tipologia();
                    int idUtente = login(tipologia);
                    if (getAccount().controllaID(tipologia, idUtente)) {
                        redirectView(tipologia, idUtente);
                    } else {
                        out.println("Credenziali non valide");
                        autenticazione();
                        break;
                    }
                case "EXIT":
                    getInput().setOff();
                    break;
                default:
                    out.println("Scelta non valida!");
                    autenticazione();
            }
        }
        arrivederci();
    }

    private int login(String tipologia) throws SQLException {
        String email = getInput().richiediString("Inserisci email").toUpperCase();
        String password = getInput().richiediString("Inserisci password");
        switch (tipologia) {
            case "CLIENTE":
                if (getAccount().controllaDati("CLIENTE", email, password)) {
                    int idC = getAccount().prendiID("CLIENTE",email, password);
                    return idC;
                }
                break;
            case "CORRIERE":
                if (getAccount().controllaDati("CORRIERE", email, password)) {
                    int idCr;
                    return idCr = getAccount().prendiID("CORRIERE",email,password);
                }
            case "COMMERCIANTE":
                if(getAccount().controllaDati("COMMERCIANTE", email, password)){
                    int idN = getAccount().prendiID("COMMERCIANTE", email,password);
                    return idN;
                }
        }
        return 0;
    }

    private String tipologia(){
        String input = getInput().richiediString("Digita (1,2,3) per selezionare la tua tipologia utente (1. Cliente, 2. Corriere, 3. Commerciante)");
        switch (input) {
            case "1": {
                return "CLIENTE";
            }
            case "2": {
                return "CORRIERE";
            }
            case "3": {
                return "COMMERCIANTE";
            }
            default: {
                out.println("Tipologia non valida.");
                return tipologia();
            }
        }
    }

    private String richiediPassword(String domanda){
        out.println("PASSWORD MINIMO 6 CARATTERI!");
        String risposta;
            do {
                risposta = getInput().leggiInput(domanda);
            } while (risposta.length() < 5);
            return risposta;
    }

    private String richiediEmail(String domanda, String tipologia) throws SQLException {
        String risposta;
        boolean controllo;
        do {
            risposta = getInput().leggiInput("Email");
            controllo = getAccount().controllaMail(tipologia, risposta);
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
        String denominazione= getInput().richiediString("Denominazione");
        String email = richiediEmail("Email",tipologia);
        String password = richiediPassword("Password");
        String telefono = getInput().richiediString("Telefono");
        Indirizzo indirizzo = inputIndirizzo();
        switch (tipologia) {
            case "CLIENTE": {
                Cliente cliente = getAccount().creatoreCliente(denominazione, email, password, telefono, indirizzo);
                int idC = getAccount().prendiID("CLIENTE", email, password);
                redirectView(tipologia, idC);
                break;
            }
            case "CORRIERE": {
                Corriere corriere = getAccount().creatoreCorriere(denominazione, email, password, telefono, indirizzo);
                int idCr = getAccount().prendiID("CORRIERE", email, password);
                redirectView(tipologia, idCr);
                break;
            }
            case "COMMERCIANTE": {
                Negozio n = getAccount().creatoreCommerciante(denominazione, email, password, telefono, indirizzo);
                int idN = getAccount().prendiID("COMMERCIANTE", email, password);
                redirectView(tipologia, idN);
                break;
            }
        }
    }

    private void redirectView(String tipologia, int id) throws SQLException {
        switch (tipologia) {
            case "CLIENTE": {
                ViewCliente viewCliente = new ViewCliente(consoleController);
                viewCliente.apriVista(id);
                break;
            }
            case "CORRIERE":{
                ViewCorriere viewCorriere = new ViewCorriere(consoleController);
                viewCorriere.apriVista(id);
                break;
            }
            case "COMMERCIANTE": {
                ViewCommerciante viewCommerciante = new ViewCommerciante(consoleController);
                viewCommerciante.apriVista(id);
                break;
            }
        }
    }

    private Indirizzo inputIndirizzo(){
        String via, numero, citta, cap, provincia;
        via = getInput().richiediString("Via");
        numero = getInput().richiediString("Numero");
        citta = getInput().richiediString("Citta'");
        cap = getInput().richiediString("cap");
        provincia = getInput().richiediString("Provincia");
        return getAccount().indirizzoAccount(via, numero, citta, cap, provincia);
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

    public InputController getInput() {
         return consoleController.getInput();
    }
    public AccountController getAccount(){
        return consoleController.getAccount();
    }
    public CorriereController getCorriere(){
        return consoleController.getCorriere();
    }
    public ClienteController getCliente(){
        return consoleController.getCliente();
    }
    public NegozioController getNegozio(){
        return consoleController.getNegozio();
    }
    public ConsoleController getConsole(){
        return consoleController;
    }

}
