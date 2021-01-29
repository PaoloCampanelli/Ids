package it.unicam.cs.ids.c3spa.GUI;

import java.sql.SQLException;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ModificaDatiFXController implements FXController {

	private Cliente cliente;
	private Negozio negozio;
	private Corriere corriere;
	
	public ModificaDatiFXController() {
	}
	
	
	@FXML
	private TextField txtNome, txtNumero, txtCitta, txtProv, txtCivico, txtVia, txtCap;
	@FXML
	private Button btnConferma, btnPulisci;
	@FXML
	private PasswordField txtPassword;

	
	public void actionConferma(ActionEvent actionEvent) throws Exception {
		if(negozio != null) {
			accettaModifiche(negozio);
			new GestoreNegozio().save(negozio);
		}else if(cliente != null) {
			accettaModifiche(cliente);
			new GestoreCliente().save(cliente);
		}else if (corriere != null) {
			accettaModifiche(corriere);
			new GestoreCorriere().save(corriere);
		}
		alert();
		Stage attuale = (Stage) btnConferma.getScene().getWindow();
		attuale.close();		
	}
	
	private  void alert() throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION, "Il sistema verra' riavviato! Effettua il login nuovamente!");
		alert.setTitle("Ricarica applicazione");
		alert.show();
		
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
	
	@Override
	public void initData(Account account) throws SQLException {
		setAccount(account);
		settaCampo(account);
	}

	
	private void accettaModifiche(Account account) {
		if(!txtNome.getText().isBlank()) {
			account.denominazione = txtNome.getText();
		}
		if(!txtPassword.getText().isBlank()&&(txtPassword.getText().length()>6)) {
			account.SetPassword(txtPassword.getText());
		}
		if(!txtNumero.getText().isBlank()&&txtNumero.getText().length()==10){
			account.telefono = txtNumero.getText();
		}
		if(!txtCitta.getText().isBlank()) {
			account.indirizzo.citta = txtCitta.getText();
		}
		if((!txtCap.getText().isBlank())&&txtCap.getText().length()==5) {
			account.indirizzo.cap = txtCap.getText();
		}
		if((!txtCivico.getText().isBlank())) {
			account.indirizzo.numero = txtCivico.getText();
		}
		if((!txtVia.getText().isBlank())) {
			account.indirizzo.via = txtVia.getText();
		}
		if((!txtProv.getText().isBlank())) {
			account.indirizzo.provincia = txtProv.getText();
		}
	}

	private void settaCampo(Account account) {
		txtNome.setPromptText(account.denominazione);
		txtNumero.setPromptText(account.telefono);
		txtCitta.setPromptText(account.indirizzo.citta);
		txtCap.setPromptText(account.indirizzo.cap);
		txtCivico.setPromptText(account.indirizzo.numero);
		txtVia.setPromptText(account.indirizzo.via);
		txtProv.setPromptText(account.indirizzo.provincia);
	}
	
	private void setAccount(Account account) {
		if (account instanceof Negozio) {
			Negozio negozio = (Negozio) account;
			this.negozio = negozio;
		}else if( account instanceof Cliente) {
			Cliente cliente = (Cliente) account;
			this.cliente = cliente;
		}else if( account instanceof Corriere) {
			Corriere corriere = (Corriere) account;
			this.corriere = corriere;
		}
	}

}
