package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;

import java.time.Instant;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
		//Verifichiamo che il pacco non sia già assegnato ad un corriere
		if (this.statiPacco.stream().noneMatch(p->p.stato.equals(StatoPaccoEnum.assegnato))) {
			this.corriere = corriere;
			this.statiPacco.add(new StatoPacco(StatoPaccoEnum.assegnato, Date.from(Instant.now())));
		}
		else
			throw new ConcurrentModificationException("Il pacco è gia stato preso in consegna");
	}

	public void setConsegnato()
	{
		//Controlliamo che il pacco non sia già consegnato
		if (this.statiPacco.stream().noneMatch(p->p.stato.equals(StatoPaccoEnum.consegnato))) {
			this.statiPacco.add(new StatoPacco(StatoPaccoEnum.consegnato, Date.from(Instant.now())));
		}
		else
			throw new ConcurrentModificationException("Il pacco è gia stato consegnato");
	}

	public Corriere getCorriere() {return this.corriere;}
}