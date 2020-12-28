package it.unicam.cs.ids.c3spa.core;
import java.sql.*;

public class Servizi {

    public Connection connessione(){
        Connection conn = null;

        String url = "jdbc:mysql://localhost/progetto_ids";
        String username = "root";
        String psw = "Bd!105788";

        try{
            conn = DriverManager.getConnection(url,username,psw);
                if(conn != null)
                    System.out.println("Connessione avvemnuta con successo");

        }catch(SQLException e){

            System.out.println("no");

        }

        return conn;
    }


}
