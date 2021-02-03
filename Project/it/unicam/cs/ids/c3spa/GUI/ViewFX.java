package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.vista.IView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewFX extends Application implements IView {

    @Override
    public void start() {
    }

    /**
     * Metodo per avvio dello stage iniziale
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/home.fxml"));
        loader.getController();
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
        stage.setTitle("C3 - BENVENUTO");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}
