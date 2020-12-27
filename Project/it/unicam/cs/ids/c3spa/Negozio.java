package it.unicam.cs.ids.c3spa;

public class Negozio extends Persona {
	private int idNegozio;
	private String nome;
	private int token;
	public Categoria categorie;
	public Ordini ordini;

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