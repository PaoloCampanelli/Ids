package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.view.ConsoleView;
import it.unicam.cs.ids.c3spa.core.view.IView;

import java.io.IOException;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        IView v = new ConsoleView();
        System.out.println(Servizi.caricamento());

/*        ElencoIndirizzi ei = new ElencoIndirizzi();
        Indirizzo i = new Indirizzo();

        System.out.println(ei.elencoIndirizzi().toString());
        System.out.println(ei.elencoIndirizzi().stream().findFirst().orElse(i).toString());
        //System.out.println(Indirizzo.eliminaIndirizzo(Indirizzo.elencoIndirizzi().stream().findFirst().orElse(null)));
        ei.CreaIndirizzo("der colosseo",10,"Roma","00133","RM");
        ei.CreaIndirizzo("da qua",10,"Roma","00133","RM");
        System.out.println(ei.elencoIndirizzi().toString());
        System.out.println(ei.eliminaIndirizzo("der colosseo", 10,"00133"));
        System.out.println(ei.elencoIndirizzi().toString());*/

        //Lettura del cliente numero 1
        Cliente c = new Cliente().GetById(1);
        System.out.println(c.toString());

        //Launcher start View
        //Bisogna implementare tutte le operazioni del controller
        v.start();



    }
}