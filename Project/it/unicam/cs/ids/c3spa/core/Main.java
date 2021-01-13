package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.gestori.*;
import it.unicam.cs.ids.c3spa.core.vista.ConsoleView;
import it.unicam.cs.ids.c3spa.core.vista.IView;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        IView v = new ConsoleView();
        System.out.println(Servizi.caricamento());

        //Lettura del cliente numero 1
        Cliente c = new GestoreCliente().getById(1);
        Negozio n = new GestoreNegozio().getById(1);
        Corriere cc = new GestoreCorriere().getById(1);
        Pacco p =new GestorePacco().getById(4);
        
        List<Cliente> lc = new GestoreCliente().getAll();
        List<Negozio> ln = new GestoreNegozio().getAll();
        List<Corriere> lcc = new GestoreCorriere().getAll();
        List<Pacco> lp = new GestorePacco().getAll();


        //System.out.println(new GestoreNegozio().getById(1).toString());
        //System.out.println(new GestoreNegozio().getByCategoria("frutta"));
        //n.categorie.add(new GestoreCategoriaMerceologica().getById(2));
        //new GestoreNegozio().save(n);

        //System.out.println(cc.toString());
        System.out.println(p.toString());
        System.out.println(cc.toString());

        //System.out.println(new GestorePacco().getByMittente("Negozi"));
        System.out.println(new GestorePacco().assegnaPacco(p,cc));

        System.out.println(p.toString());
        System.out.println(cc.toString());

        System.out.println((new GestorePacco().consegnaPacco(p , cc)));

        System.out.println(p.toString());
        System.out.println(cc.toString());










        //Launcher start View
        //Bisogna implementare tutte le operazioni del controller
        v.start();



    }
}