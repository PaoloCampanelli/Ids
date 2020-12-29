package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;

import java.util.List;

public class Negozio extends Account {
	public int token;
	public List<CategoriaMerceologica> categorie;
	public List<Pacco> pacchi;

	public String getIndirizzo() {
		throw new UnsupportedOperationException();
	}

	public void setIndirizzo(String aIndirizzo) {
		throw new UnsupportedOperationException();
	}
}