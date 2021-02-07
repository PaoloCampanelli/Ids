package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.GUI.ViewFX;
import it.unicam.cs.ids.c3spa.core.controller.Console.*;
import it.unicam.cs.ids.c3spa.core.controller.IController;
import it.unicam.cs.ids.c3spa.core.gestori.*;
import it.unicam.cs.ids.c3spa.core.vista.ConsoleView;
import it.unicam.cs.ids.c3spa.core.vista.IView;
import javafx.application.Application;

import java.sql.SQLException;
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

    public static void main(String[] args) throws Exception {
        System.out.println(Servizi.caricamento());

        //Lettura del cliente numero 1
        Cliente c = new GestoreCliente().getById(5);
        Negozio n = new GestoreNegozio().getById(2);
        Corriere cc = new GestoreCorriere().getById(1);
        Pacco p =new GestorePacco().getById(1);
        Amministratore a = new GestoreAmministratore().getById(1);

        List<Cliente> lc = new GestoreCliente().getAll();
        List<Negozio> ln = new GestoreNegozio().getAll();
        List<Corriere> lcc = new GestoreCorriere().getAll();
        List<Pacco> lp = new GestorePacco().getAll();


        System.out.println(new GestoreAmministratore().getClientiCancellati(a));
        System.out.println(new GestoreAmministratore().getById(1));
        System.out.println(new GestoreAmministratore().getNegoziCancellati(a));
        System.out.println(new GestoreAmministratore().ripristinaNegozio(n));
//        c.password = "654321";
//        System.out.println(new GestoreCliente().cambiaPassword(c));

        System.out.println();



        launchGui();

       /*
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        System.out.println("1. ConsoleView \n2. GUI");
        System.out.print("> ");
        System.out.flush();
        String risposta = br.readLine();
        if(risposta.equals("2")) {

        }else if(risposta.equals("1")){
            consoleApp().run();
        }

        */
    }

}