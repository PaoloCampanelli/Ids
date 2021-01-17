package it.unicam.cs.ids.c3spa.core.vista.controllerVista;



public class ConsoleController implements IController {

    private boolean isOn;
    private InputController inputController;
    private AccountController accountController;
    private NegozioController negozioController;
    private CorriereController corriereController;
    private ClienteController clienteController;

    public ConsoleController(InputController inputController, AccountController accountController, NegozioController negozioController, CorriereController corriereController, ClienteController clienteController) {
        this.isOn = true;
        this.inputController = inputController;
        this.accountController = accountController;
        this.negozioController = negozioController;
        this.corriereController = corriereController;
        this.clienteController = clienteController;
    }
    public ConsoleController(){
    }

    public void setOff(){
        this.isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }

    public InputController getInput() {
        return inputController;
    }
    public AccountController getAccount() {
        return accountController;
    }
    public NegozioController getNegozio() {
        return negozioController;
    }
    public CorriereController getCorriere() {
        return corriereController;
    }
    public ClienteController getCliente() {
        return clienteController;
    }

}


