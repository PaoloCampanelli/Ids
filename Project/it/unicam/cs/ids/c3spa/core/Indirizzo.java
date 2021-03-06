package it.unicam.cs.ids.c3spa.core;


public class Indirizzo {
	public String via;
	public String numero;
	public String citta;
	public String cap;
	public String provincia;

	public Indirizzo CreaIndirizzo(String via, String numero , String citta, String cap, String provincia ) {
		this.via = via;
		this.numero = numero;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
		return this;
	}

	public Indirizzo(String via, String numero , String citta, String cap, String provincia ) {
		this.via = via;
		this.numero = numero;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
	}

	public Indirizzo() {

	}

	@Override
	 public String toString() {
		 return via+", "+numero+" "+citta+", "+cap+" "+provincia;
	 }
 }