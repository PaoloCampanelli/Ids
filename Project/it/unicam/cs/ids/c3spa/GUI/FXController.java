package it.unicam.cs.ids.c3spa.GUI;

import java.io.IOException;
import java.sql.SQLException;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.FXTabella;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface FXController {

	default void apriStage(String fxml, Object controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("C3");
        stage.setScene(new Scene(root));
        stage.showAndWait();
	}
	
	
	default void apriStageController(String fxml, FXController controller, Account account) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		Stage stage = new Stage();
		stage.setScene(new Scene(loader.load()));	
		controller = loader.getController();
		controller.initData(account);
		stage.setTitle("C3");
		stage.showAndWait();
	}
	
	
	void initData(Account account) throws SQLException;


	default void apriStageTabella(String fxml, FXTabella controller, Account account) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		Stage stage = new Stage();
		stage.setScene(new Scene(loader.load()));	
		controller = loader.getController();
		controller.initData(account);
		stage.setTitle("C3");
		stage.showAndWait();
	}
	

	
}
