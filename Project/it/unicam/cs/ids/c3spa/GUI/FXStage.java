package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public interface FXStage extends FXController {

    /**
     * Aprire un nuovo stage con un qualsiasi controller
     *
     * @param fxml       file fxml da aprire
     * @param controller controller fxml da utilizzare
     * @throws IOException
     */
    default void apriStage(String fxml, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
        stage.setTitle("C3");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    /**
     * Aprire un nuovo stage con un FXController e un determinato account
     *
     * @param fxml       file fxml
     * @param controller controller di tipo FXController
     * @param account    account da settare
     * @throws IOException
     * @throws SQLException
     */
    default void apriStageController(String fxml, FXController controller, Account account) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
        stage.setScene(new Scene(loader.load()));
        controller = loader.getController();
        controller.initData(account);
        stage.setTitle("C3");
        stage.showAndWait();
    }

    default ButtonType alertModifica() {
        Alert alert = new Alert(Alert.AlertType.NONE,
                "Il sistema verra' riavviato anche se non effettuerai modifiche, vuoi continuare?", ButtonType.OK, ButtonType.NO);
        alert.setTitle("Avvertimento!");
        alert.showAndWait();
        return alert.getResult();
    }

}
