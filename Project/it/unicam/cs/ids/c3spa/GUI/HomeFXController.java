package it.unicam.cs.ids.c3spa.GUI;


import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreAmministratore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;

public class HomeFXController implements FXStage {

    private final ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("CLIENTE", "CORRIERE", "NEGOZIO");
    @FXML
    Button btnLogin, btnRegistrati;
    @FXML
    private ChoiceBox<String> tipoLogin;
    @FXML
    private ImageView logo;

    @FXML
    public void initialize() {
        tipoLogin.setItems(tipologiaDisponibile);
        tipoLogin.setValue("CLIENTE");
        logo.setImage(new Image(getClass().getResourceAsStream("resources/logo.png")));
    }

    public void actionRegistrati() throws IOException {
        apriStage("resources/registrazione.fxml", new RegistrazioneFXController());
    }

    public void actionAccedi() throws IOException {
        String tipologia = tipoLogin.getValue();
        String fxml = "resources/login.fxml";
        switch (tipologia) {
            case "CLIENTE":
                apriStage(fxml, ClienteFXController.getInstance());
                break;
            case "NEGOZIO":
                apriStage(fxml, NegozioFXController.getInstance());
                break;
            case "CORRIERE":
                apriStage(fxml, CorriereFXController.getInstance());
                break;
        }
    }

    public void actionContatti() throws SQLException, IOException {
        apriStageController("resources/contatti.fxml", new ContattiFXController(), new GestoreAmministratore().getById(1));
    }


    public void actionAdminPanel(ActionEvent actionEvent) throws IOException {
        apriStage("resources/login.fxml", new AdminFXController());
    }

    public void initData(Account account) throws SQLException {
    }


}
