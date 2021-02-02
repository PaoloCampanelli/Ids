package it.unicam.cs.ids.c3spa.core;


import java.sql.*;
import it.unicam.cs.ids.c3spa.core.gestori.*;


public class Servizi {

    public static String caricamento() throws SQLException {

        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt= conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS`progetto_ids`.`negozi` (\n" +
                "  `negozioId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `token` INT NULL DEFAULT 5,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`negozioId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`clienti` (\n" +
                "  `clienteId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`clienteId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`categoriemerceologiche` (\n" +
                "  `categoriaId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `nome` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`categoriaId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`negozio_categoriemerceologiche` (\n" +
                "  `negozioId` INT NOT NULL,\n" +
                "  `categoriaId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`negozioId`, `categoriaId`),\n" +
                "  FOREIGN KEY (negozioId) REFERENCES negozi(negozioId));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`pacchi` (\n" +
                "  `paccoId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `destinatario` INT NOT NULL,\n" +
                "  `mittente` INT NOT NULL,\n" +
                "  `corriere` INT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `dataPreparazione` DATE NOT NULL,\n" +
                "  `dataConsegnaRichiesta` DATE NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`paccoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`pacco_statipacco` (\n" +
                "  `paccoId` INT NOT NULL,\n" +
                "  `statoId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`paccoId`, `statoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`statipacchi` (\n" +
                "  `statoId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `stato` ENUM('preparato', 'assegnato', 'consegnato') NOT NULL,\n" +
                "  `data` DATE NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`statoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`sconti` (\n" +
                "  `scontoId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `tipo` VARCHAR(10) NOT NULL,\n" +
                "  `dataInizio` DATE NOT NULL,\n" +
                "  `dataFine` DATE NOT NULL,\n" +
                "  `negozioId` INT NOT NULL,\n" +
                "  `categoriaMerceologicaId` INT NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`scontoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`corrieri` (\n" +
                "  `corriereId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`corriereId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`pubblicita` (\n" +
                "  `pubblicitaId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `dataInizio` DATE NOT NULL,\n" +
                "  `dataFine` DATE NOT NULL,\n" +
                "  `negozioId` INT NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`pubblicitaId`));");


        stmt.close();
        conn.close();
        return "Database caricato";
    }

    public static java.sql.Date dataUtilToSql(java.util.Date data){

        return new java.sql.Date(data.getTime());
    }

    public static java.util.Date dataSqlToUtil(java.sql.Date data){

        return new java.util.Date(data.getTime());
    }

}
