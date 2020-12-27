package it.unicam.cs.ids.c3spa.core;

import java.util.List;

public class Corriere extends Account {

	public List<Pacco> pacchiInConsegna;

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