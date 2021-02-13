package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModificaAdminFXController implements FXStage{

    private Amministratore amministratore;
    @FXML
    TextField txtNome, txtNumero;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnConferma;

    public void actionConferma(ActionEvent actionEvent) throws Exception {
        accettaModifiche();
        alert();
        Stage attuale = (Stage) btnConferma.getScene().getWindow();
        attuale.close();
    }

    private void alert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Effettua il login!");
        alert.setTitle("Modifica dati");
        alert.showAndWait();
    }

    private void accettaModifiche() throws Exception {
        GestoreAmministratore ga = new GestoreAmministratore();
        if(!txtNome.getText().isBlank()){
            amministratore.denominazione = txtNome.getText().toUpperCase();
        }
        if(!txtNumero.getText().isBlank()){
            amministratore.telefono = txtNumero.getText().toUpperCase();
        }
        if(!txtPassword.getText().isBlank()) {
            amministratore.password = txtPassword.getText();
            ga.cambiaPassword(getAmministratore());
        }
        ga.save(getAmministratore());
    }

    @Override
    public void initData(Account account) throws SQLException {
        setAmministratore(account);
        txtNome.setPromptText(account.denominazione);
        txtNumero.setPromptText(account.telefono);
    }

    private void setAmministratore(Account account){
        this.amministratore = (Amministratore) account;
    }

    private Amministratore getAmministratore(){
        return amministratore;
    }
}
