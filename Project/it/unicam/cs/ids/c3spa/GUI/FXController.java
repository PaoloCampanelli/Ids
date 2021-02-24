package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.astratto.Account;

import java.sql.SQLException;
import java.util.List;

public interface FXController {

    /**
     * Inizializzare un account
     *
     * @param account account da settare
     * @throws SQLException
     */
    void initData(Account account) throws SQLException;

    /**
     * Controlla i parametri passati
     *
     * @param email    email inserita
     * @param password password inserita
     * @return true
     * se l'email e la password non e' vuota, password maggiore di 6 caratteri
     */
    default boolean controllaInfo(String email,  String password){
            return !email.isBlank() && !password.isBlank() && password.length() >= 6;
    }


    default boolean cercaAccount(List<? extends Account> lista, String email, String password){
        return lista.stream().anyMatch(c -> {
            try {
                return c.eMail.equals(email) && password.equals(new Servizi().decrypt(c.password));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
    }



}
