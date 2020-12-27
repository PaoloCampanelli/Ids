package it.unicam.cs.ids.c3spa.core;

import java.util.List;

public class Negozio extends Account {
	public int token;
	public List<Categoria> categorie;
	public List<Pacco> pacchi;

	public void creaPacco() {
		throw new UnsupportedOperationException();
	}

	public String getIndirizzo() {
		throw new UnsupportedOperationException();
	}

	public void setIndirizzo(String aIndirizzo) {
		throw new UnsupportedOperationException();
	}
}