package it.unicam.cs.ids.c3spa.core;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pacco {
	public int idPacco;
	public Cliente destinatario;
	public Negozio mittente;
	public Corriere corriere;
	public Date dataPreparazione;
	public Date dataConsegnaRichiesta;
	public List<StatoPacco> statiPacco;

	public Pacco CreaPacco(int idPacco, Cliente destinatario, Negozio mittente, Date dataConsegnaRichiesta)
	{
		Date dataAttuale = Date.from(Instant.now());
		this.idPacco = idPacco;
		this.destinatario = destinatario;
		this.mittente = mittente;
		this.dataPreparazione = dataAttuale;
		this.dataConsegnaRichiesta = dataConsegnaRichiesta;
		this.corriere = null;
		this.statiPacco = new ArrayList<StatoPacco>();
		this.statiPacco.add(new StatoPacco(StatoPaccoEnum.preparato, dataAttuale));
		return this;
	}

	public Cliente getDestinatario() {
		return this.destinatario;
	}

	public Negozio getMittente() {return this.mittente;}

	public void setCorriere(Corriere corriere) {
		this.corriere = corriere;
	}

	public Corriere getCorriere() {return this.corriere;}
}