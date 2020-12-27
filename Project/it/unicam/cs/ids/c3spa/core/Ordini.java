package it.unicam.cs.ids.c3spa.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ordini {
	public List<Pacco> pacchi;

	public Ordini()
	{
		pacchi = new ArrayList<Pacco>();
	}

	public Pacco CreaPacco(Cliente destinatario, Negozio mittente, Date dataConsegnaRichiesta)
	{
		//Precondizioni se i parametri sono validi
		//Determiniamo l'id
		int maxId = 0;
		//ciclo per assegnare sempre un id diverso
		for(Pacco pacco : pacchi ){
			if(pacco.idPacco > maxId)
				maxId = pacco.idPacco;
		}
		Pacco p = new Pacco().CreaPacco(maxId++,destinatario,mittente,dataConsegnaRichiesta);
		this.pacchi.add(p);
		return  p;
	}

	public List<Pacco> GetPacchiDaAssegnareAiCorrieri()
	{

		return null;
	}
}