package it.unicam.cs.ids.c3spa.core;

import java.util.List;

public class Corriere extends Account {

	private List<Pacco> pacchiInConsegna;

	public void prendiPacco(Pacco pacco) {
		try {
			pacco.setCorriere(this);
			this.pacchiInConsegna.add(pacco);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public void consegnaPacco(Pacco pacco) {
		try {
			pacco.setConsegnato();
			this.pacchiInConsegna.remove(pacco);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}