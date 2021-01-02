package it.unicam.cs.ids.c3spa.core;

public class Controller {

    public boolean execute(String comando) {
        if("NEGOZI".equals(comando)) {
            //TODO Stream di negozi dove citta' cliente = citta' negozio
        }
        else if("CATEGORIE".equals(comando)) {
            //TODO Stream di categorie dove citta' cliente = citta' negozio
        }
        else if("PROMOZIONI".equals(comando)) {
            //TODO Stream di promozioni dove citta' cliente = citta' negozio
        }
        else if(checkList(comando)) {
            //TODO Stream di negozi che soddisfano la categoria
        }

        return false;
    }

    private boolean checkList(String comando){
	/* List<Categoria>
	 *
		Iterator<Categoria> iteratore = list.iterator();
		while(iteratore.hasNext()) {
			Categoria elemento = iteratore.next();
			if(comando.equals(elemento.getNome())) {
	*/
        return false;
    }

}
