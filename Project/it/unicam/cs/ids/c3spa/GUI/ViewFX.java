package it.unicam.cs.ids.c3spa.GUI;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class ViewFX extends Application {

	public ViewFX() {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		  Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
		  primaryStage.setTitle("C3 - BENVENUTO");
	      primaryStage.setScene(new Scene(root));
	      primaryStage.show();
	}



	
	
}
