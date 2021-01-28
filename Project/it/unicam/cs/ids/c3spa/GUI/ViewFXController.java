package it.unicam.cs.ids.c3spa.GUI;

import java.io.IOException;

import it.unicam.cs.ids.c3spa.core.astratto.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ViewFXController implements FXController{
	
	@FXML
	Button btnLogin, btnRegistrati;
	@FXML
	private ChoiceBox<String> tipoLogin;
	
	ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("CLIENTE","CORRIERE","NEGOZIO");

	public void initialize() {
		tipoLogin.setItems(tipologiaDisponibile);
		tipoLogin.setValue("CLIENTE");
	}
	
	public void actionRegistrati(ActionEvent actionEvent) {
		String tipologia = tipoLogin.getValue();
		switch (tipologia) {
			case "CLIENTE":{

			}
			case "NEGOZIO":{

			}
			case "CORRIERE":{

			}
		}
	}
	
	public void actionAccedi(ActionEvent actionEvent) throws IOException {
		String tipologia = tipoLogin.getValue();
		//String fxml = "login.fxml";
		switch (tipologia) {
			case "CLIENTE":
				//apriStage(fxml, ClienteFXController.getInstance());
				break;
			case "NEGOZIO":
				//apriStage(fxml, NegozioFXController.getInstance());
				break;
			case "CORRIERE":
				//apriStage(fxml, CorriereFXController.getInstance());
				break;
		}
	}

	@Override
	public void initData(Account account) {}

}
