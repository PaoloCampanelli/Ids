package it.unicam.cs.ids.c3spa.GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.GUI.Tabelle.TabStoricoFXController;
import it.unicam.cs.ids.c3spa.core.Corriere;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCorriere;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CorriereFXController implements FXController {

	private static CorriereFXController istanza;
	private Corriere corriere;
	
	
	public static CorriereFXController getInstance() {
		if(istanza == null) {
			istanza = new CorriereFXController();
		}
		return istanza;
	}
	
	
	public CorriereFXController() {
	}
	
	private ObservableList<Pacco> pacchiNonAssegnati;
	private ObservableList<Pacco> ordiniCorriere;
	@FXML 
	private TextField txtMail, txtIDConsegna, txtIDAnnulla, txtPaccoPreso;
	@FXML	
	private PasswordField txtPassword;
	@FXML 
	private Label lblLogin, lblUtente;
	@FXML 
	private Button btnModifica, btnStorico, btnConsegna1, btnConsegna2, btnConsegna3, btnAccedi;
	@FXML 
	private VBox vbox3;
	
	@FXML
	private TableView<Pacco> tabellaPacchi;
	@FXML
	private TableColumn<Pacco, String> tbPID;
	@FXML
	private TableColumn<Pacco, String> tbPNegozio;
	@FXML
	private TableColumn<Pacco, String> tbPDestinatario;
	@FXML
	private TableColumn<Pacco, String> tbPConsegna;
	@FXML
	private TableColumn<Pacco, String> tbPCitta;
	
	
	@FXML
	private TableView<Pacco> tabellaOrdini;
	@FXML
	private TableColumn<Pacco, String> tbOID;
	@FXML
	private TableColumn<Pacco, String> tbOIndirizzo;
	@FXML
	private TableColumn<Pacco, String> tbODestinatario;
	@FXML
	private TableColumn<Pacco, String> tbOConsegna;
	
	
	public void settaPacchi() throws SQLException {
		List<Pacco> pacco = new GestorePacco().getPacchiSenzaCorriere();
	    pacchiNonAssegnati = FXCollections.observableArrayList(pacco);
	    tbPID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
	    tbPDestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.eMail));
	    tbPConsegna.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
	    tbPNegozio.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().mittente.denominazione));
	    tbPCitta.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.citta));
	    pacchiNonAssegnati.removeIf(p -> p.id == 0);
	    tabellaPacchi.setItems(pacchiNonAssegnati);
	    tabellaPacchi.setPlaceholder(new Label("C3 non contiene pacchi!"));
	}
	
	private void settaOrdini(Corriere corriere) throws SQLException {
		List<Pacco> pacco = new GestorePacco().getByCorriere(corriere);
		ordiniCorriere = FXCollections.observableArrayList(pacco);
	    tbOID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
	    tbODestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.eMail));
	    tbOConsegna.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().dataConsegnaRichiesta.toString()));
	    tbOIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
	    ordiniCorriere.removeIf(p -> p.id == 0);
	    tabellaOrdini.setItems(ordiniCorriere);
	    tabellaOrdini.setPlaceholder(new Label("Non hai preso in carico nessun pacco!"));
	}
	
	public void actionAccedi(ActionEvent actionEvent) throws SQLException, IOException {
		String email = txtMail.getText().toUpperCase();
		String password = txtPassword.getText();
		if((controllaInfo(email, password))) {
			if(controllaCorriere(email, password)){
				setCorriere(prendiCorriere(email, password));
				apriStageController("resources/corriere.fxml", getInstance(), corriere/*, btnAccedi*/);
			}
		}
	}
	
	public void actionAssegna(ActionEvent actionEvent) throws SQLException {
		if(cercaPacco(txtPaccoPreso.getText())) {
			Pacco pacco = prendiPacco(txtPaccoPreso.getText());
			corriere.prendiPacco(pacco);
			ordiniCorriere.add(pacco);
			new GestorePacco().save(pacco);
			pacchiNonAssegnati.removeIf(p -> p.id == pacco.id);
		}
	}
	
	
	public void actionConsegna(ActionEvent actionEvent) throws SQLException {
		if(cercaPacco(txtIDConsegna.getText())) {
			Pacco pacco = prendiPacco(txtIDConsegna.getText());
			avvertiConsegna(pacco);
		}
	}
	
	
	public void actionAnnulla(ActionEvent actionEvent) throws SQLException {
		if(cercaPacco(txtIDAnnulla.getText())) {
			Pacco pacco = prendiPacco(txtIDAnnulla.getText());
			pacco.corriere = null;
			pacchiNonAssegnati.add(pacco);
			new GestorePacco().save(pacco);
			new GestoreCorriere().save(pacco);
			ordiniCorriere.removeIf(p -> p.id == pacco.id);
			
		}
	}
	
	public void actionModificaInfo(ActionEvent actionEvent) throws IOException, SQLException {
		if(alertModifica() == ButtonType.OK) {
			apriStageController("resources/aggiornaDati.fxml", new ModificaDatiFXController(), corriere);
			Stage attuale = (Stage) btnModifica.getScene().getWindow();
		    attuale.close();
		}
		
	}
	
	
	public void actionStoricoOrdiniNegozio(ActionEvent actionEvent) throws IOException, SQLException {
		apriStageTabella("resources/tabellaStorico.fxml", new TabStoricoFXController(), corriere);
	}
	
	private void avvertiConsegna(Pacco pacco) throws SQLException {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Spedito da: "+ pacco.mittente.denominazione + "									"
				+ "Destinatario: " + pacco.destinatario.eMail
				+ " Consegna: "+pacco.dataConsegnaRichiesta+" "
				+pacco.indirizzo.toString(), ButtonType.YES, ButtonType.NO);
		alert.setTitle("Conferma pacco");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			corriere.consegnaPacco(pacco);
			new GestorePacco().save(pacco);
			ordiniCorriere.removeIf(p -> p.id == pacco.id);
		}
	}
	
	private ButtonType alertModifica() throws SQLException {
		Alert alert = new Alert(AlertType.NONE,
				"Il sistema verra' riavviato anche se non effettuerai modifiche, vuoi continuare?", ButtonType.OK, ButtonType.NO);
		alert.setTitle("Avvertimento!");
		alert.showAndWait();
		return alert.getResult();
	}
	
	private boolean cercaPacco(String id) throws SQLException {
		List<Pacco> pacchi = new GestorePacco().getAll();
		return pacchi.stream().anyMatch(p -> p.id == Integer.parseInt(id));
	}
	
	private Pacco prendiPacco(String id) throws SQLException {
		List<Pacco> pacchi = new GestorePacco().getAll();
		int idPacco = pacchi.stream().filter(p -> p.id == Integer.parseInt(id)).findAny().get().id;
		return new GestorePacco().getById(idPacco);
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
	
	private boolean controllaCorriere(String email, String password) throws SQLException {
		List<Corriere> lc = new GestoreCorriere().getAll();
		return lc.stream().anyMatch(c->c.eMail.equals(email)&&c.password.equals(password));
	}

	private Corriere prendiCorriere(String email, String password) throws SQLException {
		GestoreCorriere gc = new GestoreCorriere();
		List<Corriere> lc = gc.getAll();
		int id = lc.stream().filter(c->c.eMail.equals(email)).findAny().get().id;
		return gc.getById(id);
	}

	
	public Corriere getCorriere() {
		return corriere;
	}

	public void setCorriere(Account account) {
		if (account instanceof Corriere) {
			Corriere corriere = (Corriere) account;
			this.corriere = corriere;
		}
	}
	
	@Override
	public void initData(Account account) throws SQLException {
		lblUtente.setText(account.denominazione);
		setCorriere(account);
		settaPacchi();
		settaOrdini(corriere);
		vbox3.setDisable(true);
	}

}
