package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.gestori.*;
import it.unicam.cs.ids.c3spa.core.vista.ConsoleView;
import it.unicam.cs.ids.c3spa.core.vista.IView;
import it.unicam.cs.ids.c3spa.core.vista.controllerVista.*;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Main {

    private IView view;
    private IController controller;

    public Main(IView view, IController controller){
        this.view = view;
        this.controller = controller;
    }

    private static Main consoleApp(){
        InputController inputController = new InputController();
        AccountController accountController = new AccountController();
        NegozioController negozioController = new NegozioController();
        CorriereController corriereController = new CorriereController();
        ClienteController clienteController = new ClienteController();
        ConsoleController controller = new ConsoleController(inputController, accountController, negozioController, corriereController, clienteController);
        IView view = new ConsoleView(controller);
        return new Main(view, controller);
    }

    private void run() throws SQLException {
        view.start();
    }


    public static void main(String[] args) throws SQLException{
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
//        System.out.println(new GestorePacco().assegnaPacco(p,cc));
//
//        System.out.println(p.toString());
//        System.out.println(cc.toString());
//
//        System.out.println((new GestorePacco().consegnaPacco(p , cc)));
//
//        System.out.println(p.toString());
//        System.out.println(cc.toString());

        System.out.println(new GestorePacco().storicoByCorriere(cc));
        System.out.println(new GestorePacco().storicoByCliente(new GestoreCliente().getById(9)));
        System.out.println(new GestorePacco().storicoByNegozio(new GestoreNegozio().getById(2)));

        consoleApp().run();

    }
}