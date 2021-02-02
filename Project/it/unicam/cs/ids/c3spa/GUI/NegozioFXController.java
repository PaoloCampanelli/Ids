package it.unicam.cs.ids.c3spa.GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabCategoriaNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabNegoziFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabScontiFXController;
import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NegozioFXController implements FXController{

	public NegozioFXController(){}

	private static NegozioFXController istanza;
	private Negozio negozio;
	
	public static NegozioFXController getInstance() {
		if(istanza == null) {
			istanza = new NegozioFXController();
		}
		return istanza;
	}
	
	@FXML 
	private TextField txtMail;
	@FXML	
	private PasswordField txtPassword;
	@FXML 
	private Label lblLogin, lblUtente, lblErrore2, lblErrore3, lblToken;
	@FXML
	private Button btnAccedi, btnCreaOrdine, btnCatAttive, btnStorico, btnSconti, btnOrdini, btnListaCliente, btnModifica, btnPromozioni;
	
	
	public void actionAccedi(ActionEvent actionEvent) throws SQLException, IOException {
		String email = txtMail.getText().toUpperCase();
		String password = txtPassword.getText();
		if((controllaInfo(email, password))) {
			if(controllaNegozio(email, password)){
				setNegozio(prendiNegozio(email, password));
				apriStageController("resources/negozio.fxml", getInstance(), negozio/*, btnAccedi*/);
			}
		}
	}
	
	public void actionOrdine(ActionEvent actionEvent) throws IOException, SQLException {
		apriStageController("resources/creaOrdine.fxml", new OrdineFXController(), negozio);
	}

    public void actionListaClienti(ActionEvent actionEvent) throws IOException, SQLException {
    	apriStageTabella("resources/tabellaClienti.fxml", new TabNegoziFXController(), negozio);
    }
    
  	public void actionListaCategorie(ActionEvent actionEvent) throws IOException, SQLException {
	    apriStageTabella("resources/tabellaCategoriaNegozi.fxml", new TabCategoriaNegoziFXController(), negozio);
  	}
  	
 
    public void actionStoricoOrdiniNegozio(ActionEvent actionEvent) throws IOException, SQLException {
    	apriStageTabella("resources/tabellaStorico.fxml", new TabStoricoFXController(), negozio);
    }
    
    public void actionAttivaPubblicita(ActionEvent actionEvent) {
    	//TODO
    }

    public void actionModificaInfo(ActionEvent actionEvent) throws IOException, SQLException {
    	if(alertModifica() == ButtonType.OK) {
    	apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), negozio);
    	Stage attuale = (Stage) btnModifica.getScene().getWindow();
	    attuale.close();
    	}
    }

	public void actionContatti(ActionEvent actionEvent) throws IOException {
		apriStage("resources/contatti.fxml", new ContattiFXController());
	}
    
    public void actionListaPromozioni(ActionEvent actionEvent) throws IOException, SQLException {
    	apriStageTabella("resources/tabellaSconti.fxml", new TabScontiFXController(), negozio);
    }
    	
	private ButtonType alertModifica() throws SQLException {
		Alert alert = new Alert(AlertType.NONE,
				"Il sistema verra' riavviato anche se non effettuerai modifiche, vuoi continuare?", ButtonType.OK, ButtonType.NO);
		alert.setTitle("Avvertimento!");
		alert.showAndWait();
		return alert.getResult();
	}
    
	
	private boolean controllaInfo(String email, String password) throws SQLException {
		if(email.isBlank()) {
			lblLogin.setText("Email non valida");
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
	
	private boolean controllaNegozio(String email, String password) throws SQLException {
		List<Negozio> ln = new GestoreNegozio().getAll();
		return ln.stream().anyMatch(c->c.eMail.equals(email)&&c.password.equals(password));
	}

	private Negozio prendiNegozio(String email, String password) throws SQLException {
		GestoreNegozio gc = new GestoreNegozio();
		List<Negozio> lc = gc.getAll();
		int id = lc.stream().filter(c->c.eMail.equals(email)).findAny().get().id;
		return gc.getById(id);
	}


	public Negozio getNegozio() {
		return negozio;
	}

	public void setNegozio(Account account) {
		if (account instanceof Negozio) {
			Negozio negozio = (Negozio) account;
			this.negozio = negozio;
		}
	}

	@Override
	public void initData(Account account) throws SQLException {
		lblUtente.setText(account.denominazione);
		setNegozio(account);
		lblToken.setText("Token disponibili: "+negozio.token);
	}


}
