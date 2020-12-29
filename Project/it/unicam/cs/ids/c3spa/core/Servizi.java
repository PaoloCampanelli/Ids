package it.unicam.cs.ids.c3spa.core;


import java.sql.*;

public class Servizi {

    public Connection connessione(){
        Connection conn = null;

        String driver = "com.mysql.jdbc.Driver";
        String jdbcURL = "jdbc:mysql://localhost/progetto_ids?user=root&password=Bd!105788&serverTimezone=Europe/Rome" ;

        System.setProperty(driver,"");


        try{
            conn = DriverManager.getConnection(jdbcURL);
                if(conn != null)
                    System.out.println("Connessione avvenuta con successo");

        }catch(SQLException ex){

            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        return conn;
    }

    public String caricamento() throws SQLException {

        Connection c = null;
        Servizi ser = new Servizi();
        c =ser.connessione();
        Statement stmt=c.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS`progetto_ids`.`negozi` (\n" +
                "  `idnegozi` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(50) NOT NULL,\n" +
                "  `Indirizzo` VARCHAR(45) NOT NULL,\n" +
                "  `telefono` VARCHAR(10) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (`idnegozi`),\n" +
                "  UNIQUE INDEX `telefono_UNIQUE` (`telefono` ASC) VISIBLE,\n" +
                "  UNIQUE INDEX `eMail_UNIQUE` (`eMail` ASC) VISIBLE,\n" +
                "  UNIQUE INDEX `Indirizzo_UNIQUE` (`Indirizzo` ASC) VISIBLE)");
        stmt.close();
        c.close();
        return "Database caricato";
    }


}
