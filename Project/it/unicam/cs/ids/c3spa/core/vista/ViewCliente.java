package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;

import static java.lang.System.*;

import java.sql.SQLException;

public class ViewCliente extends ConsoleView{

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
                +"\n4. Digita la categoria da ricercare -> 'FRUTTA', 'VERDURA'...");
    }

    private void sceltaCliente(Cliente cliente) throws SQLException {
        while(on()){
            listaCliente();
            String richiesta = richiediString("Digita scelta: ").toUpperCase();
            switch (richiesta) {
                case "1": {
                    getConsoleController().cercaNegozi(cliente, "1");
                    break;
                }
                case "2": {
                    getConsoleController().cercaNegozi(cliente, "2");
                    break;
                }
                case "3": {
                    ricercaPersonalizzata();
                    break;
                }
                case "EXIT": {
                    off();
                    break;
                }
                case "LOGOUT": {
                    out.println("Disconnessione da " + cliente.denominazione);
                    logout();
                    break;
                }
                default:
                    getConsoleController().checkList(richiesta);
            }
        }
        arrivederci();
    }


    private void ricercaPersonalizzata() throws SQLException {
        String categoria = richiediString("     CATEGORIA");
        String citta = richiediString("         IN QUALE CITTA'?");
        getConsoleController().cercaNegoziCategoria(categoria, citta);
    }

}
