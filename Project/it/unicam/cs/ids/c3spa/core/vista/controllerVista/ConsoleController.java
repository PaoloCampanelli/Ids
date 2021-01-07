package it.unicam.cs.ids.c3spa.core.vista.controllerVista;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleController implements IController{

    private boolean isOn = true;

    private void trovaNegozi(Cliente cliente) throws SQLException {
        List<Negozio> lc = new GestoreNegozio().getAll();
        lc.stream().filter(lista -> (lista.indirizzo.cap).equals(cliente.indirizzo.cap))
                .map(Negozio::toString)
                .collect(Collectors.joining("\n"));
    }

    private boolean trovaPromozioni() {
        //TODO
        return false;
    }

    private boolean trovaCategorie() {
        //TODO
        return false;
    }

    public boolean checkList(String comando){
        System.out.println("In corso di implementazione...");
	/*
		Iterator<Categoria> iteratore = list.iterator();
		while(iteratore.hasNext()) {
			Categoria elemento = iteratore.next();
			if(comando.equals(elemento.getNome())) {
				return true;
			}
	*/
        return false;
    }

    public Indirizzo indirizzoAccount(String via, String numero, String citta, String cap, String provincia){
        Cliente cliente = new Cliente();
        return cliente.indirizzo.CreaIndirizzo(via, numero, citta, cap, provincia);
    }

    //Gestire il salvataggio(?)
    public String aggiungiCategoria(String nome, Negozio negozio) {
        try {
            CategoriaMerceologica c = new CategoriaMerceologica(nome);
            CategoriaMerceologica categoria = new GestoreCategoriaMerceologica().save(c);
            negozio.categorie.add(categoria);
            new GestoreNegozio().save(negozio);
            return "Categoria aggiunta correttamente";
        }catch (SQLException e){
            return ("errore: "+e.getMessage());
        }
    }

    public void setOff(){
        this.isOn = false;
    }


    public boolean isOn() {
        return isOn;
    }
}
