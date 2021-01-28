package it.unicam.cs.ids.c3spa.GUI;


import java.io.IOException;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class HomeFXController extends Application implements FXController {

	private static HomeFXController istanza;
	
	
	public static HomeFXController getInstance() {
		if(istanza == null) {
			istanza = new HomeFXController();
		}
		return istanza;
	}
	
	@FXML
	Button btnLogin, btnRegistrati;
	@FXML
	private ChoiceBox<String> tipoLogin;
	
	ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("CLIENTE","CORRIERE","NEGOZIO");

	public void initialize() {
		tipoLogin.setItems(tipologiaDisponibile);
		tipoLogin.setValue("CLIENTE");
	}
	
	public void actionRegistrati(ActionEvent actionEvent) throws IOException {
		//apriStage("registrazione.fxml", new RegistrazioneFXController());
	}
	
	public void actionAccedi(ActionEvent actionEvent) throws IOException {
		String tipologia = tipoLogin.getValue();
		String fxml = "login.fxml";
		if(tipologia.equals("CLIENTE")) {
			apriStage(fxml, ClienteFXController.getInstance());
		}else if(tipologia.equals("NEGOZIO")) {
			//apriStage(fxml, NegozioFXController.getInstance());
		}else if(tipologia.equals("CORRIERE")) {
			//apriStage(fxml, CorriereFXController.getInstance());
		}
	}
	
	@Override
	public void initData(Account account) {}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/home.fxml"));
        loader.getController();
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("C3 - BENVENUTO");
        stage.setScene(new Scene(root));
        stage.showAndWait();
	}



	
	
}
