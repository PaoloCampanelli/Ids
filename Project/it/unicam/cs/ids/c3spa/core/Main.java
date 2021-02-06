package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.GUI.ViewFX;
import it.unicam.cs.ids.c3spa.core.controller.Console.*;
import it.unicam.cs.ids.c3spa.core.gestori.*;
import it.unicam.cs.ids.c3spa.core.vista.ConsoleView;
import it.unicam.cs.ids.c3spa.core.vista.IView;
import it.unicam.cs.ids.c3spa.core.controller.*;
import javafx.application.Application;

import static java.lang.System.in;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private static void launchGui(){
        Application.launch(ViewFX.class);
    }

    public static void main(String[] args) throws SQLException, IOException {
        System.out.println(Servizi.caricamento());

        //Lettura del cliente numero 1
        Cliente c = new GestoreCliente().getById(1);
        Negozio n = new GestoreNegozio().getById(2);
        Corriere cc = new GestoreCorriere().getById(1);
        Pacco p =new GestorePacco().getById(1);
        Amministratore a = new GestoreAmministratore().getById(1);

        List<Cliente> lc = new GestoreCliente().getAll();
        List<Negozio> ln = new GestoreNegozio().getAll();
        List<Corriere> lcc = new GestoreCorriere().getAll();
        List<Pacco> lp = new GestorePacco().getAll();


        System.out.println(new GestoreAmministratore().getClientiCancellati(a));
        //System.out.println(new GestoreAmministratore().aggiungiToken(3,n,a));



        //Sconto sc = new Sconto("5x1",p.dataPreparazione, p.dataConsegnaRichiesta, n , new GestoreCategoriaMerceologica().getById(6));
        //System.out.println(new GestoreSconto().save(sc));


//        Corriere ng = new Corriere(0,"frs", c.indirizzo, "1","7@4", "123456");
//        new GestoreCorriere().save(ng);

//        new GestoreNegozio().delete(1);
//        System.out.println(new GestoreNegozio().getById(1));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        System.out.println("1. ConsoleView \n2. GUI");
        System.out.print("> ");
        System.out.flush();
        String risposta = br.readLine();
        if(risposta.equals("2")) {
            launchGui();
        }else if(risposta.equals("1")){
            consoleApp().run();
        }
    }

}