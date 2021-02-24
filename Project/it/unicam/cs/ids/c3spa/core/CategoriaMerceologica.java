package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.astratto.IMapData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaMerceologica implements IMapData {
	public int idCategoria;
	public String nome;

	public CategoriaMerceologica (int id, String nome){
		this.idCategoria = id;
		this.nome = nome;
	}

	public CategoriaMerceologica(String nome){
		this.nome = nome;
	}

	public CategoriaMerceologica() {

	}


	@Override
	public CategoriaMerceologica mapData(ResultSet rs) throws SQLException {
		this.idCategoria = rs.getInt("categoriaId");
		this.nome = rs.getString("nome");
		return this;
	}

	@Override
	public String toString() {
		return nome;
	}
}