package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;

import java.io.IOException;
import java.sql.SQLException;

public class ViewCliente extends ConsoleView{

    public void apriVista(int id) throws IOException, SQLException {
        Cliente c = new GestoreCliente().getById(id);
        System.out.println("\n...Effettuato accesso come CLIENTE");
        System.out.println("----------------");
        System.out.println("Bentornato "+c.denominazione+"!\n");
        sceltaCliente(c);
    }

    private void listaCliente() {
        System.out.println("Operazioni disponibili:     || EXIT -> per uscire");
        System.out.println("1. MOSTRA TUTTI I NEGOZI");
        System.out.println("2. MOSTRA NEGOZI VICINO A ME");
        System.out.println("3. MOSTRA TUTTI I NEGOZI PER CATEGORIA E CITTA'");
        System.out.println("4. Digita la categoria da ricercare -> 'FRUTTA', 'VERDURA'...");
    }

    private void sceltaCliente(Cliente cliente) throws IOException, SQLException {
        while(on()){
            listaCliente();
            System.out.print("> ");
            String richiesta = getBr().readLine().toUpperCase();
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
                default:
                    getConsoleController().checkList(richiesta, cliente);
            }
        }
        arrivederci();
    }


    private void ricercaPersonalizzata() throws IOException, SQLException {
        String categoria = richiediString("     CATEGORIA");
        String citta = richiediString("         IN QUALE CITTA'?");
        getConsoleController().cercaNegoziCategoria(categoria, citta);
    }

}
