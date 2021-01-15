package it.unicam.cs.ids.c3spa.core.vista.controllerVista;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import static java.lang.System.in;
import static java.lang.System.out;

public class ConsoleController implements IController {

    private boolean isOn = true;

    public void setOff(){
        this.isOn = false;
    }

    public String richiediString(String domanda){
        String risposta;
        risposta = leggiInput(domanda);
        while (risposta.isEmpty() || risposta.charAt(0) == ' ') {
            risposta = leggiInput(domanda);
        }
        return risposta;
    }

    public String leggiInput(String domanda){
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            out.println(domanda);
            out.print("> ");
            out.flush();
            return br.readLine();
        }catch (IOException e){
            return e.getMessage();
        }
    }

    public int richiediInt(String domanda){
        String intero;
        do{
            intero = leggiInput(domanda);
        }while(intero.isEmpty() || intero.equals("0"));
        return Integer.parseInt(intero);
    }

    public boolean isOn() {
        return isOn;
    }
}
