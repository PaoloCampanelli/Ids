package it.unicam.cs.ids.c3spa.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Indirizzo {
	public String via;
	public int numero;
	public String citta;
	public String cap;
	public String provincia;

	Indirizzo CreaIndirizzo(String via, int numero , String citta, String cap, String provincia )
	{
		this.via = via;
		this.numero = numero;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
		return this;
	}

	public static ArrayList<Indirizzo> elencoIndirizzi(){

		ArrayList<Indirizzo> elenco = new ArrayList<>();
		Statement st;
		ResultSet rs;
		String sql;


		try {

			sql = "SELECT via, numero, citta, cap, provincia FROM indirizzo;";
			// ________________________________query

			st = Servizi.connessione().createStatement(); // creo sempre uno statement sulla
			// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				Indirizzo a = new Indirizzo();
				a.CreaIndirizzo(rs.getString("via"), rs.getInt("numero"), rs.getString("citta"),
						rs.getString("cap"), rs.getString("provincia"));
				elenco.add(a);
			}

			Servizi.connessione().close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch

		return elenco;
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