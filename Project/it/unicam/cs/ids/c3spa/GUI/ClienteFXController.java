package it.unicam.cs.ids.c3spa.GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabCittaCategoriaFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabCittaFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.FXTabella;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabCategoriaFXController;
import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClienteFXController implements FXController  {

	private static ClienteFXController istanza;
	private Cliente cliente;
	
	public static ClienteFXController getInstance() {
		if(istanza == null) {
			istanza = new ClienteFXController();
		}
		return istanza;
	}
	
	public ClienteFXController() {}

	@FXML
	private TextField txtMail, txtCategoriaCitta, txtCitta, txtCategoria;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnAccedi, btnRicerca1, btnRicerca2, btnRicerca3, btnRicerca4, btnModifica, btnStorico, btnOrdini, btnSconti;
	@FXML
	private Label lblLogin, lblUtente, lblCittaUtente, lblErrore1, lblErrore2;
	
	
	public void actionAccedi(ActionEvent actionEvent) throws SQLException, IOException {
		String email = txtMail.getText().toUpperCase();;
		String password = txtPassword.getText();
		if((controllaInfo(email, password))) {
			if(controllaCliente(email, password)){
				setCliente(prendiCliente(email, password));
				apriStageController("resources/cliente.fxml", getInstance(), getCliente());
			}else
				lblLogin.setText("Email e/o password non corretto");
		}else
			lblLogin.setText("Errore nell'inserimento dati");
		
		
	}
	
	
	public void actionRicercaNegozi(ActionEvent actionEvent) throws IOException, SQLException {
		apriStageTabella("resources/tabellaNegozi.fxml", new TabNegoziFXController(), cliente);
	}
	
	public void actionRicercaNegoziByCitta(ActionEvent actionEvent) throws SQLException, IOException {
		apriStage("resources/tabellaCitta.fxml", cliente, "" , " ",  new TabCittaFXController());
	}

	//Non va bene la query %...% -> SE DIGITO T MI RIPORTA T-ECH T-ECNOLOGIA...
	public void actionRicercaCategoria(ActionEvent actionEvent) throws IOException, SQLException {
		lblErrore2.setText("");
		if(!(txtCategoria.getText().isBlank()))
			apriStage("resources/tabellaCategoria.fxml", cliente, " ", txtCategoria.getText().toUpperCase(), new TabCategoriaFXController());
		else
			lblErrore2.setText("Valore non valido!");
	}	
	
	
	public void actionRicercaCittaCategoria(ActionEvent actionEvent) throws IOException, SQLException {
		lblErrore1.setText("");
		if(!(txtCitta.getText().isBlank()||txtCategoriaCitta.getText().isBlank()))
			apriStage("resources/tabellaCittaCategoria.fxml", cliente, txtCitta.getText().toUpperCase(), txtCategoriaCitta.getText().toUpperCase(), new TabCittaCategoriaFXController());
		else
			lblErrore1.setText("Valori non validi!");
	}
	
	public void actionStoricoOrdiniCliente(ActionEvent actionEvent) throws IOException, SQLException {
		apriStageTabella("resources/tabellaStorico.fxml", new TabStoricoFXController(), cliente);
	}
	
	public void actionOrdiniAttivi(ActionEvent actionEvent) {
		//TODO 
	}
	
	public void actionVisualizzaSconti(ActionEvent actionEvent) {
		//TODO 
	}
	
	public void actionModificaInfo(ActionEvent actionEvent) throws IOException, SQLException {
		if(alertModifica() == ButtonType.OK) {
			apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), cliente);
			Stage attuale = (Stage) btnModifica.getScene().getWindow();
	    	attuale.close();
		}
	}
	
	
	private ButtonType alertModifica() throws SQLException {
		Alert alert = new Alert(AlertType.NONE,
				"Il sistema verra' riavviato anche se non effettuerai modifiche, vuoi continuare?", ButtonType.OK, ButtonType.NO);
		alert.setTitle("Avvertimento!");
		alert.show();
		return alert.getResult();
	}
	
	@Override
	public void initData(Account account) throws SQLException {
		lblCittaUtente.setText(account.indirizzo.citta);
		lblUtente.setText(account.denominazione);
		setCliente(account);
	}
	
	private void apriStage(String fxml, Cliente cliente, String citta, String categoria, FXTabella controller) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		Stage stage = new Stage();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/logo.png")));
		stage.setScene(new Scene(loader.load()));	
		controller = loader.getController();
		controller.initData(cliente, citta, categoria);
		stage.setTitle("C3");
		stage.show();
	}
	

	private boolean controllaCliente(String email, String password) throws SQLException {
		List<Cliente> lc = new GestoreCliente().getAll();
		return lc.stream().anyMatch(c->c.eMail.equals(email)&&c.password.equals(password));
	}

	private Cliente prendiCliente(String email, String password) throws SQLException {
		GestoreCliente gc = new GestoreCliente();
		List<Cliente> lc = gc.getAll();
		int id = lc.stream().filter(c->c.eMail.equals(email)&&c.password.equals(password)).findAny().get().id;
		return gc.getById(id);
	}
	
	private boolean controllaInfo(String email, String password) throws SQLException {
		if(email.isBlank()) {
			
			return false;
		}
		if(password.isBlank()) {
			lblLogin.setText("Password non valida");
			return false;
		}
		if(password.length()<6) {
			lblLogin.setText("La password deve essere almeno 6 caratteri!");
			return false;
		}
		return true;
	}
	
	public void setCliente(String email) throws SQLException {
		List<Cliente> lc = new GestoreCliente().getByEMail(email);
		for(Cliente cliente : lc) {
			this.cliente = cliente;
		}
	}
	
	public void setCliente(Account account) throws SQLException {
		if (account instanceof Cliente) {
			Cliente cliente = (Cliente) account;
			this.cliente = cliente;
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}


}

