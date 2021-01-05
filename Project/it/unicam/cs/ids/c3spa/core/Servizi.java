package it.unicam.cs.ids.c3spa.core;


import java.sql.*;
import it.unicam.cs.ids.c3spa.core.gestori.*;


public class Servizi {

    //metodi Spostato in Gestore base
/*    public static Connection ApriConnessione(){
        Connection conn = null;

        String driver = "com.mysql.jdbc.Driver";
        String jdbcURL = "jdbc:mysql://localhost/progetto_ids?user=root&password=Bd!105788&serverTimezone=Europe/Rome" ;

        System.setProperty(driver,"");


        try{
            conn = DriverManager.getConnection(jdbcURL);
                if(conn != null);
                    //System.out.println("Connessione avvenuta con successo");

        }catch(SQLException ex){

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        return conn;
    }

    public static void ChiudiConnessione(Connection conn) throws SQLException {
        try {
            conn.close();
        }
        catch (SQLException ex)
        {}
    }*/

    public static String caricamento() throws SQLException {

        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt= conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS`progetto_ids`.`negozi` (\n" +
                "  `negozioId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
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
                "  PRIMARY KEY (`clienteId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`categoriemerceologiche` (\n" +
                "  `categoriaId` INT NOT NULL,\n" +
                "  `nome` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`categoriaId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`negozio_categoriamerceologica` (\n" +
                "  `negozioId` INT NOT NULL,\n" +
                "  `categoriaId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`negozioId`, `categoriaId`));");




        stmt.close();
        conn.close();
        return "Database caricato";
    }

}
