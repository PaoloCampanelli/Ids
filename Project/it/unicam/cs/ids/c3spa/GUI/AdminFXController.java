package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AdminFXController implements FXStage {

    @FXML
    private TextField txtMail, txtPassword;

    public void actionAccedi() throws IOException, SQLException {
        accedi(txtMail.getText().toUpperCase(), txtPassword.getText());
    }

    private void accedi(String mail, String passw) {
        //CONTROLLI SULLA MAIL E PASSWORD
        //apriStageController("resources/admin.fxml", this, getAdmin());
    }


    @Override
    public void initData(Account account) throws SQLException {

    }
}
