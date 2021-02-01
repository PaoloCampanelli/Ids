package it.unicam.cs.ids.c3spa.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ContattiFXController {

    @FXML
    private Label nome, email, telefono;
    @FXML
    private ImageView logo;

    @FXML
    private void initialize() {
        nome.setText("..Implementazione..");
        email.setText("..Implementazione..");
        telefono.setText("..Implementazione..");
        logo.setImage(new Image(getClass().getResourceAsStream("resources/logo.png")));

    }


}
