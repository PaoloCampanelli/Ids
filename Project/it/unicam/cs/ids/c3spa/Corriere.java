package it.unicam.cs.ids.c3spa;

public class Corriere extends Persona {
	private int idCorriere;
	private String nome;
	private String cognome;
	public Pacco pacchiPresi;

	public void visualizzaOrdini() {
		throw new UnsupportedOperationException();
	}

	public void prendiPacco(Object aIdPacco) {
		throw new UnsupportedOperationException();
	}

	public void setStatoPacco(Object aStatoEnum) {
		throw new UnsupportedOperationException();
	}
}