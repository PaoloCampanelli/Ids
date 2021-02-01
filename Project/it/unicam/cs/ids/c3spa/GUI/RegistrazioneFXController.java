package it.unicam.cs.ids.c3spa.GUI;


import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrazioneFXController implements FXController{

	public RegistrazioneFXController() {
	}
	
	@FXML
	private TextField txtNome, txtNumero, txtCitta, txtProv, txtCivico, txtVia, txtCap, txtEmail;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label lblErrore;
	@FXML
	private ChoiceBox<String> tipologia;
	@FXML
	private Button btnRegistrati;
	
	ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("CLIENTE","CORRIERE","NEGOZIO");
	
	
	public void initialize() {
		tipologia.setItems(tipologiaDisponibile);
		tipologia.setValue("CLIENTE");
	}
	
	public void actionRegistrazione(ActionEvent actionEvent) throws Exception{
		lblErrore.setText(" ");
		String email = txtEmail.getText().toUpperCase();
		String nome = txtNome.getText();
		String passw = txtPassword.getText();
		String numero = txtNumero.getText();
		String via = txtVia.getText();
		String citta = txtCitta.getText();
		String prov = txtProv.getText();
		String cap = txtCap.getText();
		String civico = txtCivico.getText();
		String tipologia = tipologia();
		if(controllaInfo(tipologia, email, nome, passw, numero)) {
			if(controllaIndirizzo(via, citta, prov, cap, civico)){
				Indirizzo indirizzo = creaIndirizzo(via, civico, citta, cap, prov);
				Stage attuale = (Stage) btnRegistrati.getScene().getWindow();
				attuale.hide();
				switch(tipologia) {
					case "CLIENTE": {
						GestoreCliente gc = new GestoreCliente();
						Cliente nuovo = new Cliente(nome, indirizzo, numero, email, passw);
						gc.save(nuovo);
						apriStageController("resources/cliente.fxml", ClienteFXController.getInstance(), nuovo);
						break;
						}
					case "NEGOZIO":{
						GestoreNegozio gc = new GestoreNegozio();
						Negozio nuovo = new Negozio(nome, indirizzo, numero, email, passw);
						gc.save(nuovo);
						apriStageController("resources/negozio.fxml", NegozioFXController.getInstance(), nuovo);
						break;
					}
					case "CORRIERE":{
						GestoreCorriere gc = new GestoreCorriere();
						Corriere nuovo = new Corriere(0, nome, indirizzo, numero, email, passw);
						gc.save(nuovo);
						apriStageController("resources/corriere.fxml", CorriereFXController.getInstance(), nuovo);
						break;
					}	
				}
				
			}else
				lblErrore.setText("INDIRIZZO ERRATO");
		}else
			lblErrore.setText("INFORMAZIONI PERSONALI NON VALIDE!");
		
	}
	
	public void actionPulisci(ActionEvent actionEvent) {
		txtNome.clear();
		txtNumero.clear();
		txtCitta.clear();
		txtCap.clear();
		txtCivico.clear();
		txtVia.clear();
		txtProv.clear();
		txtPassword.clear();
	}
	

	private Indirizzo creaIndirizzo(String via, String civico, String citta, String cap, String provincia) {
		return new Indirizzo(via, civico, citta, cap, provincia);
	}
	
	private boolean controllaIndirizzo(String via,String citta,String prov,String cap,String civico) {
		if(!citta.isBlank()) {	
			if((!cap.isBlank())&&cap.length()==5) {	
				if((!civico.isBlank())) {
					if((!via.isBlank())) {
						if((!prov.isBlank())&&prov.length()==2) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean controllaInfo(String tipologia, String email, String nome, String passw, String numero) throws SQLException {
		if(!email.isBlank()) {
			if(email.contains("@")) {
				if(!emailEsistente(email.toUpperCase(), tipologia)) {
					if(!nome.isBlank()) {
						if(!passw.isBlank()&&(passw.length()>5)) {
							if(!numero.isBlank()&numero.length()==10){
									return true;
								}
						}
					}
				}
			}
		}
		return false;
	}
	
	
	private String tipologia() {
		tipologia.getValue();
		if(tipologia.getValue().equals("NEGOZIO")) {
			return "NEGOZIO";
		}else if(tipologia.getValue().equals("CLIENTE")) {
			return "CLIENTE";
		}else if(tipologia.getValue().equals("CORRIERE")) {
			return "CORRIERE";
		}else
			return "";
	}
	
	
	private boolean emailEsistente(String email, String tipologia) throws SQLException {
		switch(tipologia) {
			case "CLIENTE": {
				List<Cliente> lc = new GestoreCliente().getAll();
				return lc.stream().anyMatch(c -> c.eMail.equals(email));
				}
			case "NEGOZIO":{
				List<Negozio> lc = new GestoreNegozio().getAll();
				return lc.stream().anyMatch(c -> c.eMail.equals(email));
			}
			case "CORRIERE":{
				List<Corriere> lc = new GestoreCorriere().getAll();
				return lc.stream().anyMatch(c -> c.eMail.equals(email));
			}
		}
		return false;
	}

	@Override
	public void initData(Account account) throws SQLException {}
	

}

