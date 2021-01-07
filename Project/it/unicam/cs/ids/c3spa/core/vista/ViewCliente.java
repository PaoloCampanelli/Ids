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
        listaCliente();
        sceltaCliente();
    }

    private void listaCliente() {
        System.out.println("Operazioni disponibili:     || EXIT -> per uscire");
        System.out.println("1. Ricerca negozi");
        System.out.println("2. Ricerca categorie presenti");
        System.out.println("3. Promozione");
        System.out.println("digita la categoria da ricercare -> 'FRUTTA', 'VERDURA'...");
    }

    private void sceltaCliente() throws IOException {
        while(on()){
            System.out.print("> ");
            String richiesta = getBr().readLine().toUpperCase();
            switch (richiesta) {
                case "1": {
                    break;
                }
                case "2": {
                    break;
                }
                case "3": {

                    break;
                }
                case "EXIT": {
                    off();
                    break;
                }
                default:
                    getConsoleController().checkList(richiesta);
            }
        }
        arrivederci();
    }


}
