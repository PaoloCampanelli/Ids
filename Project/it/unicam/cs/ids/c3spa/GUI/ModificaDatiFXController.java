package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModificaDatiFXController implements FXController {

    private Cliente cliente;
    private Negozio negozio;
    private Corriere corriere;
    @FXML
    private TextField txtNome, txtNumero, txtCitta, txtProv, txtCivico, txtVia, txtCap;
    @FXML
    private Button btnConferma;
    @FXML
    private PasswordField txtPassword;

    public ModificaDatiFXController() {
    }

    public void actionConferma() throws Exception {
        if (negozio != null) {
            accettaModifiche(negozio);
            new GestoreNegozio().save(negozio);
        } else if (cliente != null) {
            accettaModifiche(cliente);
            new GestoreCliente().save(cliente);
        } else if (corriere != null) {
            accettaModifiche(corriere);
            new GestoreCorriere().save(corriere);
        }
        alert();
        Stage attuale = (Stage) btnConferma.getScene().getWindow();
        attuale.close();
    }

    public void actionPulisci() {
        txtNome.clear();
        txtNumero.clear();
        txtCitta.clear();
        txtCap.clear();
        txtCivico.clear();
        txtVia.clear();
        txtProv.clear();
        txtPassword.clear();
    }

    private void alert() {
        Alert alert = new Alert(AlertType.INFORMATION, "Il sistema verra' riavviato! Effettua il login nuovamente!");
        alert.setTitle("Ricarica applicazione");
        alert.show();
    }

    /**
     * Inizializza i dati dell'account
     *
     * @param account account da settare
     * @throws SQLException
     */
    @Override
    public void initData(Account account) throws SQLException {
        setAccount(account);
        settaCampo(account);
    }

    /**
     * Riceve le determinate modifiche dell'utente e le setta
     *
     * @param account account da modificare
     */
    private void accettaModifiche(Account account) {
        if (!txtNome.getText().isBlank()) {
            account.denominazione = txtNome.getText();
        }
        if (!txtPassword.getText().isBlank() && (txtPassword.getText().length() > 6)) {
            account.SetPassword(txtPassword.getText());
        }
        if (!txtNumero.getText().isBlank() && txtNumero.getText().length() == 10) {
            account.telefono = txtNumero.getText();
        }
        if (!txtCitta.getText().isBlank()) {
            account.indirizzo.citta = txtCitta.getText();
        }
        if ((!txtCap.getText().isBlank()) && txtCap.getText().length() == 5) {
            account.indirizzo.cap = txtCap.getText();
        }
        if ((!txtCivico.getText().isBlank())) {
            account.indirizzo.numero = txtCivico.getText();
        }
        if ((!txtVia.getText().isBlank())) {
            account.indirizzo.via = txtVia.getText();
        }
        if ((!txtProv.getText().isBlank())) {
            account.indirizzo.provincia = txtProv.getText();
        }
    }

    /**
     * Setta i campi dell'account
     *
     * @param account account da modificare
     */
    private void settaCampo(Account account) {
        txtNome.setPromptText(account.denominazione);
        txtNumero.setPromptText(account.telefono);
        txtCitta.setPromptText(account.indirizzo.citta);
        txtCap.setPromptText(account.indirizzo.cap);
        txtCivico.setPromptText(account.indirizzo.numero);
        txtVia.setPromptText(account.indirizzo.via);
        txtProv.setPromptText(account.indirizzo.provincia);
    }

    /**
     * Setta l'account in base alla sua istanza
     *
     * @param account account da settare
     */
    private void setAccount(Account account) {
        if (account instanceof Negozio) {
            this.negozio = (Negozio) account;
        } else if (account instanceof Cliente) {
            this.cliente = (Cliente) account;
        } else if (account instanceof Corriere) {
            this.corriere = (Corriere) account;
        }
    }

}
