package it.unicam.cs.ids.c3spa;

import java.util.Date;

public class Pacco {
	private int idPacco;
	private Indirizzo indirizzo;
	private Cliente destinatario;
	private Negozio mittente;
	private double dimensione;
	private Date dataConsegna;
	private Date dataPreparazione;
	private StatoPacco stato;
	private Corriere corriere;

	public Cliente getDestinatario() {
		return this.destinatario;
	}

	public void setDestinatario(Cliente aDestinatario) {
		throw new UnsupportedOperationException();
	}

	public void setCorriere(Object aIdCorriere) {
		throw new UnsupportedOperationException();
	}
}