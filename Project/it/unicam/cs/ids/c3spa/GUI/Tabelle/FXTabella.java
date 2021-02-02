package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import it.unicam.cs.ids.c3spa.core.astratto.Account;

import java.sql.SQLException;

public interface FXTabella {
	
	void initData(Account account) throws SQLException;

	void initData(Account account, String citta, String categoria) throws SQLException;

	
}
