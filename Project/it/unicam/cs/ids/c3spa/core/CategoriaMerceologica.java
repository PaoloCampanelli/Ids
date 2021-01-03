package it.unicam.cs.ids.c3spa.core;

public class CategoriaMerceologica {
	protected int idCategoria;
	protected String nome;

	public CategoriaMerceologica(int id, String nome) {
		this.idCategoria= id;
		this.nome = nome;
	}

	public CategoriaMerceologica creaCategoria(int id, String nome){
		this.idCategoria = id;
		this.nome = nome;
		return this;
	}
}