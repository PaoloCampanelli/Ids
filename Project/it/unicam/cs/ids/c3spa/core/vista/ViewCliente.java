package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.AccountController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ClienteController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.ConsoleController;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.InputController;

import static java.lang.System.*;

import java.sql.SQLException;

public class ViewCliente extends ConsoleView{

    public ViewCliente(ConsoleController controller) {
        super(controller);
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
        while(getConsole().isOn()){
            listaCliente();
            String richiesta = getInput().richiediString("Digita scelta: ").toUpperCase();
            switch (richiesta) {
                case "1": {
                    getCliente().cercaNegozi(cliente, "1");
                    break;
                }
                case "2": {
                    getCliente().cercaNegozi(cliente, "2");
                    break;
                }
                case "3": {
                    String categoria = getInput().richiediString("     CATEGORIA");
                    String citta = getInput().richiediString("         IN QUALE CITTA'?");
                    getCliente().cercaNegoziCategoria(categoria, citta);
                    break;
                }
                case "4": {
                        out.println("..implementazione in corso..");
                    break;
                }
                case "EXIT": {
                    getCliente().setOff();
                    break;
                }
                case "LOGOUT": {
                    out.println("Disconnessione da " + cliente.denominazione);
                    logout();
                    break;
                }
                default:
                   getCliente().checkList(richiesta);
            }
        }
    }

}
