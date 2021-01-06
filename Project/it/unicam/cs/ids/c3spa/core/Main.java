package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import it.unicam.cs.ids.c3spa.core.vista.ConsoleView;
import it.unicam.cs.ids.c3spa.core.vista.IView;

import java.io.IOException;
import java.sql.*;
import java.util.List;

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
        Cliente c = new GestoreCliente().getById(1);
        //String nomeCognome, Indirizzo indirizzo, String telefono, String eMail, String password
        //Cliente a = new GestoreCliente().save(c);
        Cliente b = new GestoreCliente().getById(3);
        List<Cliente> lc = new GestoreCliente().getAll();

        Negozio n = new GestoreNegozio().getById(1);
        System.out.println(n.categorie);
        System.out.println(c.toString());
        System.out.println(new GestoreNegozio().getById(1).toString());
        //n.categorie.add(new GestoreCategoriaMerceologica().getById(2));
        //new GestoreNegozio().save(n);
        Pacco p =new GestorePacco().getById(1);
        System.out.println(p.toString());






        //Launcher start View
        //Bisogna implementare tutte le operazioni del controller
        v.start();



    }
}