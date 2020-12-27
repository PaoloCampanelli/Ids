package it.unicam.cs.ids.c3spa.core;

public class Corriere extends Account {
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