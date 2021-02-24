package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.GUI.ViewFX;
import it.unicam.cs.ids.c3spa.controller.*;
import it.unicam.cs.ids.c3spa.gestori.*;
import it.unicam.cs.ids.c3spa.console.ConsoleView;
import it.unicam.cs.ids.c3spa.console.IView;
import javafx.application.Application;

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

    private void run() throws Exception {
        view.start();
    }

    private static void launchGui(){
        Application.launch(ViewFX.class);
    }

    public static void main(String[] args) throws Exception {
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


        System.out.println(new GestorePacco().getByDestinatario(c));
        System.out.println(new GestorePubblicita().getNegoziConPubblicitaAttivaByString("`indirizzo.provincia`", "MC"));
        //System.out.println(new GestorePubblicita().OrderByPubblicita(new GestoreNegozio().getByCategoriaAndCitta("FRUTTA","2"),new GestorePubblicita().getNegoziConPubblicitaAttivaByCategoriaAndString("FRUTTA","`indirizzo.citta`", "2")));
//        c.password = "654321";
//        System.out.println(new GestoreCliente().cambiaPassword(c));

        System.out.println(new GestoreSconto().getScontiAttiviByProvincia("MC"));



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