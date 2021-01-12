package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.*;
import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;
import it.unicam.cs.ids.c3spa.core.gestori.*;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleController implements IController {

    private boolean isOn = true;

    public void cercaNegozi(Cliente cliente, String scelta) throws SQLException {
        GestoreNegozio gn = new GestoreNegozio();
        String citta = cliente.indirizzo.citta;
        List<Negozio> lnCitta = gn.getByIndirizzo("indirizzo.citta",citta);
        List<Negozio> ln = gn.getAll();
        List<Negozio> lnCategoria = gn.getNegoziAndCategorie();
        if(scelta.equals("1")){
            trovaNegozi(ln);
        }else if(scelta.equals("2")){
            trovaNegozi( lnCitta);
        }else if(scelta.equals("3")) {
            trovaCategoria(lnCategoria);
        }
    }

    public void cercaNegoziCategoria(String categoria, String citta) throws SQLException {
        List<Negozio> ln = new GestoreNegozio().getByCategoriaAndCitta(categoria, citta);
        for (Negozio negozio: ln) {
            System.out.println("         > "+negozio.denominazione);
        }
    }

    private void trovaNegozi(List<Negozio> lista) {
        for (Negozio negozio : lista) {
            System.out.println("    > " + negozio.denominazione);
        }
    }

    private void trovaCategoria(List<Negozio> lista){
        for (Negozio negozio : lista) {
            System.out.println("    > " + negozio.denominazione);
            System.out.println(negozio.categorie.size());
            for(CategoriaMerceologica categoria : negozio.categorie)
                System.out.println("     > "+categoria.nome);
        }
    }

    public void visualizzaClienti() throws SQLException {
        List<Cliente> lc = new GestoreCliente().getAll();
        for (Cliente cliente : lc){
            System.out.println("    > "+cliente.denominazione+", "+ cliente.eMail+", "+ cliente.indirizzo.citta);
        }
    }


    private boolean trovaPromozioni() {
        System.out.println("...implementazione in corso...");
        return false;
    }


    public void checkList(String categoria) throws SQLException {
        List<Negozio> lc = new GestoreNegozio().getByCategoria(categoria);
        System.out.println("Negozi che possiedono la categoria: " + categoria);
        for (Negozio negozio : lc) {
            System.out.println("    > " + negozio.denominazione);
            System.out.println("        > "+negozio.indirizzo);
        }
    }

    public Indirizzo indirizzoAccount(String via, String numero, String citta, String cap, String provincia){
        Cliente cliente = new Cliente();
        return cliente.indirizzo.CreaIndirizzo(via, numero, citta, cap, provincia);
    }

    public boolean aggiungiCategoria(String nome, Negozio negozio) {
        try {
            CategoriaMerceologica c = new CategoriaMerceologica(nome);
            CategoriaMerceologica categoria = new GestoreCategoriaMerceologica().save(c);
            negozio.categorie.add(categoria);
            new GestoreNegozio().save(negozio);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean creazionePacco(Cliente cliente, Negozio negozio, Date data) throws SQLException {
        Pacco pacco = new Pacco(cliente,negozio, data);
//        StatoPacco sp = new StatoPacco(StatoPaccoEnum.preparato, Date.from(Instant.now()));
//        new GestoreStatoPacco().save(sp);
        new GestorePacco().save(pacco);
        return true;
    }

    public boolean pacchiLiberi() throws SQLException {
        List<Pacco> lp = new GestorePacco().getPacchiSenzaCorriere();
        if(lp.isEmpty()) {
            System.err.println("Nessun pacco disponibile al momento!");
            return false;
            }
        for(Pacco pacco : lp){
            System.out.println("     > ["+pacco.id+"| Data consegna: "+pacco.dataConsegnaRichiesta
                    +"\n           INFORMAZIONI DESTINATARIO: "+pacco.destinatario.denominazione+" "+pacco.destinatario.indirizzo
                    +"\n           INFORMAZIONI MITTENTE: "+pacco.mittente.denominazione+" "+pacco.mittente.indirizzo+"]");
        }
        return true;
    }

    public void ordiniCorriere(String corriere) throws SQLException{
        List<Pacco> lp = new GestorePacco().getByCorriere(corriere);
        for(Pacco pacco : lp){
            System.out.println("     > ["+pacco.id+" Destinatario: "
                    +pacco.destinatario.denominazione+" Data consegna: "+pacco.dataConsegnaRichiesta+
                    " "+pacco.destinatario.indirizzo+"]");
        }
    }

   public boolean controllaPacco(int idPacco, Corriere corriere) throws SQLException {
        GestorePacco gp = new GestorePacco();
        List<Pacco> lp = gp.getPacchiSenzaCorriere();
        for(Pacco pacco : lp){
            if(pacco.id == idPacco){
                pacco.setCorriere(corriere);
                gp.save(pacco);
                return true;
            }else
                System.out.println("NO!");
                return false;
        }
        return false;
   }

    public void setOff(){
        this.isOn = false;
    }


    public boolean isOn() {
        return isOn;
    }
}
