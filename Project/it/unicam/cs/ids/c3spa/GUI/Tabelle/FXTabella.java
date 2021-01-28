package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import java.sql.SQLException;

import it.unicam.cs.ids.c3spa.core.astratto.Account;

public interface FXTabella {
	
	void initData(Account account) throws SQLException;

	void initData(Account account, String citta, String categoria) throws SQLException;

	
}
