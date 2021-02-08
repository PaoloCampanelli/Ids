package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabGestioneFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabRipristinaFXController;
import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminFXController implements FXStage {

    private Amministratore amministratore;

    @FXML
    TextField txtMail, txtPassword;
    @FXML
    Button btnModifica;
    @FXML
    ImageView logo;

    public void actionAccedi() throws IOException, SQLException {
        //DA RIMUOVERE
        txtMail.setText("AMMINISTRATORE@MAIL");
        txtPassword.setText("123456");

        accedi(txtMail.getText().toUpperCase(), txtPassword.getText());
    }

    public void actionModifica() throws IOException, SQLException {
        if (alertModifica() == ButtonType.OK) {
            Stage attuale = (Stage) btnModifica.getScene().getWindow();
            apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), getAdmin());
            attuale.close();
        }
    }

    public void actionGestisci(ActionEvent actionEvent) throws IOException, SQLException {
        apriStageController("resources/gestioneAccount.fxml", new TabGestioneFXController(), getAdmin());
    }

    public void actionRecupera(ActionEvent actionEvent) throws IOException, SQLException {
        apriStageController("resources/ripristinaAccount.fxml", new TabRipristinaFXController(), getAdmin());
    }


    private void accedi(String email, String passw) throws IOException, SQLException {
        List<Amministratore> la = new GestoreAmministratore().getAll();
        if ((controllaInfo(email, passw))) {
            if (cercaAccount(la, email, passw)) {
                setAmministratore(prendiAdmin(la, email, passw));
                apriStageController("resources/admin.fxml", this, getAdmin());
            }
        }
    }

    private Amministratore prendiAdmin(List<Amministratore> la, String email, String password) throws SQLException {
        int id = la.stream().filter(c -> {
            try {
                return c.eMail.equals(email) && password.equals(new Servizi().decrypt(c.password));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }).findAny().get().id;
        return new GestoreAmministratore().getById(id);
    }

    @Override
    public void initData(Account account) throws SQLException {
        setAmministratore(account);
        logo.setImage(new Image(getClass().getResourceAsStream("resources/logo.png")));
    }

    private void setAmministratore(Account account){
        if(account instanceof Amministratore){
            this.amministratore= (Amministratore) account;
        }
    }

    private Amministratore getAdmin(){
        return amministratore;
    }


}
