package it.unicam.cs.ids.c3spa.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ElencoIndirizzi {

    public List<Indirizzo> indirizzi;

    //Costruisce un certo set di indirizzi vuoto
    public ElencoIndirizzi() {
        indirizzi = new ArrayList<Indirizzo>() {};
    }

    public ArrayList<Indirizzo> elencoIndirizzi(){

        Statement st;
        ResultSet rs;
        String sql;


        try {

            sql = "SELECT via, numero, citta, cap, provincia FROM indirizzo;";
            // ________________________________query

            st = Servizi.connessione().createStatement(); // creo sempre uno statement sulla
            // connessione
            rs = st.executeQuery(sql); // faccio la query su uno statement
            while (rs.next() == true) {
                Indirizzo a = new Indirizzo();
                a.CreaIndirizzo(rs.getString("via"), rs.getInt("numero"), rs.getString("citta"),
                        rs.getString("cap"), rs.getString("provincia"));
                if(indirizzi.stream().noneMatch(b -> b.via.equals(a.via)))
                    if(indirizzi.stream().noneMatch(b -> b.numero==a.numero))
                        if(indirizzi.stream().noneMatch(b -> b.cap.equals(a.cap)))
                            this.indirizzi.add(a);
            }

            Servizi.connessione().close(); // chiusura connessione
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            e.printStackTrace();
        } // fine try-catch

        return (ArrayList<Indirizzo>) this.indirizzi;
    }

    /**
     * Inserisci un nuovo indirizzo nella tabella indirizzo
     *
     * @return
     * @throws SQLException
     */
    public Indirizzo CreaIndirizzo(String via, int numero, String citta, String cap, String provincia) {
        Statement st;
        String sql;


        try {

            Indirizzo a = new Indirizzo().CreaIndirizzo(via, numero, citta, cap, provincia);
            sql = "insert into indirizzo (via, numero, citta, cap, provincia) values ('" + via + "','"+ numero + "','" + citta + "','" + cap + "','"+ provincia
                    + "')";

            st = Servizi.connessione().createStatement(); // creo sempre uno statement sulla
            st.execute(sql); // faccio la query su uno statement
            Servizi.connessione().close(); // chiusura connessione
            this.indirizzi.add(a);
            return a;
        } catch (SQLException e) {
            System.out.println("errore:" + e.getMessage());
            return null;
        } // fine try-catch

    }

    /**
     * Elimina un indirizzo nella tabella indirizzo di MySQL
     *
     * @return
     * @throws SQLException
     */
    public  String eliminaIndirizzo(String via, int numero, String cap) {
        Statement st;
        String sql;

        try {

            //this.elencoIndirizzi().stream().filter(a -> a.via.equals(via)).filter(a -> a.numero== numero).filter(a -> a.cap.equals(cap));

            sql = "DELETE FROM `progetto_ids`.`indirizzo` WHERE (`via` = '"+via+"') and (`numero` = '"+numero+"') and (`cap` = '"+cap+"');";


            st = Servizi.connessione().createStatement(); // creo sempre uno statement sulla
            // connessione
            st.execute(sql); // faccio la query su uno statement
            Servizi.connessione().close(); // chiusura connessione
            Indirizzo b = new Indirizzo();
            b = indirizzi.stream().filter(a -> a.via.equals(via)).filter(a -> a.numero== numero).filter(a -> a.cap.equals(cap)).findFirst().orElse(null);
            indirizzi.remove(b);
            return "Cancellato indirizzo con via = "+via+" , numero = "+numero+" e cap = "+cap;
        } catch (SQLException e) {
            return "errore:" + e.getMessage();

        } // fine try-catch


    }

    @Override
    public String toString() {
        return "ElencoIndirizzi{" +
                "indirizzi=" + indirizzi +
                '}';
    }
}
