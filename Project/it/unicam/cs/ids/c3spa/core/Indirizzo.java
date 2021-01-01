package it.unicam.cs.ids.c3spa.core;


public class Indirizzo {
	public String via;
	public int numero;
	public String citta;
	public String cap;
	public String provincia;

	public Indirizzo CreaIndirizzo(String via, int numero , String citta, String cap, String provincia )
	{
		this.via = via;
		this.numero = numero;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
		return this;


	}

	@Override
	 public String toString() {
		 return "Indirizzo{" +
				 "via='" + via + '\'' +
				 ", numero=" + numero +
				 ", citta='" + citta + '\'' +
				 ", cap='" + cap + '\'' +
				 ", provincia='" + provincia + '\'' +
				 '}';
	 }
 }