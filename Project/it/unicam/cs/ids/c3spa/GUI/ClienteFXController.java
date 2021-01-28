package it.unicam.cs.ids.c3spa.GUI;

public class ClienteFXController {

    private static ClienteFXController istanza;
    //private Cliente cliente;

    public static ClienteFXController getInstance() {
        if(istanza == null) {
            istanza = new ClienteFXController();
        }
        return istanza;
    }

    public ClienteFXController() {}

}
