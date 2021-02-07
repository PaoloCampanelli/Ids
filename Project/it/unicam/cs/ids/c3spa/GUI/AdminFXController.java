package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminFXController implements FXStage {

    private Amministratore amministratore;

    @FXML
    TextField txtMail, txtPassword;

    public void actionAccedi() throws IOException, SQLException {
        accedi(txtMail.getText().toUpperCase(), txtPassword.getText());
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
