package it.unicam.cs.ids.c3spa;

import java.util.Date;

public class Pacco {
	private int _idPacco;
	private Indirizzo _indirizzo;
	private Cliente _destinatario;
	private Negozio _mittente;
	private double _dimensione;
	private Date _dataConsegna;
	private Date _dataPreparazione;
	private StatoPacco _stato;
	private Corriere _corriere;

	public Cliente getDestinatario() {
		return this._destinatario;
	}

	public void setDestinatario(Cliente aDestinatario) {
		throw new UnsupportedOperationException();
	}

	public void setCorriere(Object aIdCorriere) {
		throw new UnsupportedOperationException();
	}
}