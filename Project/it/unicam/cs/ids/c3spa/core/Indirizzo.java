package it.unicam.cs.ids.c3spa.core;

 public class Indirizzo {
	public String via;
	public String citta;
	public String cap;
	public String provincia;

	Indirizzo CreaIndirizzo(String via, String citta, String cap, String provincia )
	{
		this.via = via;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
		return this;
	}
}