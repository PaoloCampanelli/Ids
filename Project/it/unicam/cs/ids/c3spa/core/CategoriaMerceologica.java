package it.unicam.cs.ids.c3spa.core;

public class CategoriaMerceologica {
	protected int id;
	protected String nome;

	public CategoriaMerceologica(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public CategoriaMerceologica creaCategoria(int id, String nome){
		this.id = id;
		this.nome = nome;
		return this;
	}

	public void rimuoviCategoria(int id, String nome){
		this.id = id;
		this.nome = nome;
	}
}