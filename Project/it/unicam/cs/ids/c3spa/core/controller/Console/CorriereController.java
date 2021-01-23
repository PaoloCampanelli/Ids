package it.unicam.cs.ids.c3spa.core.controller.Console;

import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;

import java.sql.SQLException;
import java.util.List;

public class CorriereController{

    public boolean pacchiLiberi() throws SQLException {
        List<Pacco> lp = new GestorePacco().getPacchiSenzaCorriere();
        if (lp.isEmpty()) {
            System.out.println("Nessun pacco disponibile al momento!");
            return false;
        }
        for (Pacco pacco : lp) {
            System.out.println("     > [" + pacco.id + "| Data consegna: " + pacco.dataConsegnaRichiesta
                    + "\n           INFORMAZIONI DESTINATARIO: " + pacco.destinatario.denominazione + " " + pacco.destinatario.indirizzo
                    + "\n           INFORMAZIONI MITTENTE: " + pacco.mittente.denominazione + " " + pacco.mittente.indirizzo + "]");
        }
        return true;
    }

    public List<Pacco> ordiniCorriere(Corriere corriere) throws SQLException {
        List<Pacco> lp = new GestorePacco().getByCorriere(corriere);
        if(lp.size() == 0){
            System.out.println("Non hai preso nessun pacco in carico!");
        }
        for (Pacco pacco : lp) {
            System.out.println("     > [" + pacco.id + " Destinatario: "
                    + pacco.destinatario.denominazione + " Data consegna: " + pacco.dataConsegnaRichiesta +
                    " " + pacco.destinatario.indirizzo + "]");
        }
        return lp;
    }

    public boolean controllaPacco(int idPacco, Corriere corriere) throws SQLException {
        GestorePacco gp = new GestorePacco();
        List<Pacco> lp = gp.getPacchiSenzaCorriere();
        for (Pacco pacco : lp) {
            if (pacco.id == idPacco)
                return gp.assegnaPacco(pacco, corriere);
        }
        return false;
    }

    public boolean consegnaPacco(int idPacco, Corriere corriere) throws SQLException {
        GestorePacco gp = new GestorePacco();
        return gp.consegnaPacco(new GestorePacco().getById(idPacco), corriere);
    }

    public void storicoOrdini(Corriere corriere) throws SQLException {
        List<Pacco> lp = new GestorePacco().storicoByCorriere(corriere);
        if(lp.size() == 0){
            System.out.println("NON HAI COMPLETATO NESSUN ORDINE!");
        }
        for(Pacco pacco : lp){
            System.out.println("    > "+pacco.id+"| spedito da: "+pacco.mittente.denominazione+" il "+pacco.dataPreparazione+"\n" +
                    "        [Destinatario: "+pacco.destinatario.denominazione+" "+pacco.destinatario.indirizzo+"\n"+
                    "        Consegna prevista: "+pacco.dataConsegnaRichiesta+"]");
        }
    }

}