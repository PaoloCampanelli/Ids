package it.unicam.cs.ids.c3spa.core.vista;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;

import java.io.IOException;
import java.sql.SQLException;

public class ViewCliente extends ConsoleView{

    // Cliente c = new GestoreCliente().getById(2);
    public void start() throws IOException {
        System.out.println("\n...Effettuato accesso come CLIENTE");
        System.out.println("----------------");
        listaCliente();
        String comando;
        do {
            System.out.print("> ");
            comando = getBr().readLine().toUpperCase();
        }while(!getConsoleController().executerCliente(comando));
    }

    private void listaCliente() {
        System.out.println("Operazioni disponibili: ");
        System.out.println("NEGOZI");
        System.out.println("CATEGORIE");
        System.out.println("PROMOZIONI");
        System.out.println("NOME CATEGORIA -> 'FRUTTA', 'VERDURA'...");
    }
}
