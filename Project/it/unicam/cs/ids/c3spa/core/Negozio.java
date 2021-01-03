package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.Account;

import java.util.List;

public class Negozio extends Account {
	public int token;
	public List<CategoriaMerceologica> categorie;
	public List<Pacco> pacchi;
	public int numeroCategorie;

	public Negozio(int negozioId, String denominazione, Indirizzo indirizzo, String telefono, String eMail, String password) {
		this.id= negozioId;
		this.denominazione = denominazione;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
		this.eMail = eMail;
		this.password= password;
	}

	public CategoriaMerceologica creaCategoria(int id, String nome) {
		//verifico che la categoria che voglio aggiungere non sia già presente, se è gia presente la ritorno.
		if (categorie.stream().anyMatch(c->c.idCategoria==id && c.nome.equals(nome))) {
			return categorie.stream().filter(c -> c.idCategoria==id).findFirst().orElse(null);
		}
		CategoriaMerceologica c= new CategoriaMerceologica(id, nome);
		c.idCategoria= numeroCategorie;
		numeroCategorie++;
		categorie.add(c);
		return c;
	}

	public void rimuoviCategoria(int id, String nome){
		CategoriaMerceologica categoria= categorie.stream().filter(c->c.idCategoria==id && c.nome.equals(nome)).findAny().orElse(null);
		this.categorie.remove(categoria);
	}

	public List<CategoriaMerceologica> getCategorie() {
		return categorie;
	}

	public List<Pacco> getPacchi() {
		return pacchi;
	}

	public String getIndirizzo() {
		throw new UnsupportedOperationException();
	}

	public void setIndirizzo(String aIndirizzo) {
		throw new UnsupportedOperationException();
	}
}