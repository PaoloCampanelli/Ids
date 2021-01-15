package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ClienteController;

import static java.lang.System.*;

import java.sql.SQLException;

public class ViewCliente extends ConsoleView{

    private ClienteController clienteController;

    public ViewCliente(){
        this.clienteController = new ClienteController();
    }

    public void apriVista(int id) throws SQLException {
        Cliente c = new GestoreCliente().getById(id);
        out.println("\n...Effettuato accesso come CLIENTE"
                +"\n----------------"
                +"\nBentornato "+c.denominazione+"!\n");
        sceltaCliente(c);
    }

    private void listaCliente() {
        out.println("Operazioni disponibili:     || EXIT -> per uscire       LOGOUT -> per tornare alla pagina principale"
                +"\n1. MOSTRA TUTTI I NEGOZI"
                +"\n2. MOSTRA NEGOZI VICINO A ME"
                +"\n3. MOSTRA TUTTI I NEGOZI PER CATEGORIA E CITTA'"
                +"\n4. MOSTRA STORICO ORDINI"
                +"\n5. Digita la categoria da ricercare -> 'FRUTTA', 'VERDURA'...");
    }

    private void sceltaCliente(Cliente cliente) throws SQLException {
        while(getConsoleController().isOn()){
            listaCliente();
            String richiesta = getConsoleController().richiediString("Digita scelta: ").toUpperCase();
            switch (richiesta) {
                case "1": {
                    clienteController.cercaNegozi(cliente, "1");
                    break;
                }
                case "2": {
                    clienteController.cercaNegozi(cliente, "2");
                    break;
                }
                case "3": {
                    String categoria = getConsoleController().richiediString("     CATEGORIA");
                    String citta = getConsoleController().richiediString("         IN QUALE CITTA'?");
                    clienteController.cercaNegoziCategoria(categoria, citta);
                    break;
                }
                case "4": {
                        out.println("..implementazione in corso..");
                    break;
                }
                case "EXIT": {
                    getConsoleController().setOff();
                    break;
                }
                case "LOGOUT": {
                    out.println("Disconnessione da " + cliente.denominazione);
                    logout();
                    break;
                }
                default:
                    clienteController.checkList(richiesta);
            }
        }
    }


}
