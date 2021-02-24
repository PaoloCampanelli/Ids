package it.unicam.cs.ids.c3spa.gestori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class GestoreBase {
    public static Connection ApriConnessione(){
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
    }
}
