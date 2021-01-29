package it.unicam.cs.ids.c3spa.GUI;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unicam.cs.ids.c3spa.core.Cliente;
import it.unicam.cs.ids.c3spa.core.Indirizzo;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.Pacco;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCliente;
import it.unicam.cs.ids.c3spa.core.gestori.GestorePacco;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class OrdineFXController implements FXController {

	public OrdineFXController() {
	}
	
	private Negozio negozio;
	
	private ObservableList<String> tipologiaDisponibile = FXCollections.observableArrayList("PREDEFINITO","NUOVO");
	private ObservableList<Pacco> pacchiNonAssegnati;
	private ObservableList<Cliente> clienteDisponibili;
	
	
	@FXML
	private TableView<Cliente> tabellaCliente;
	@FXML
	private TableView<Pacco> tabellaPacchi;
	@FXML
	private TableColumn<Cliente, String> tbNome;
	@FXML
	private TableColumn<Cliente, String> tbIndirizzo;
	@FXML
	private TableColumn<Cliente, String> tbEmail;
	@FXML
	private TextField txtEmail, txtId, txtEmailRicerca, txtVia, txtCitta, txtCivico, txtProv, txtCap;
	@FXML
	private DatePicker dpData;
	@FXML
	private Button btnRicerca, btnAnnulla, btnCrea, btnResetta, btnConferma;
	@FXML
	private HBox hboxNascosta;
	@FXML
	private Label lblAnnulla, lblEmail, lblErrore, lblErroreIndirizzo;
	@FXML
	private TableColumn<Pacco, String> tbId;
	@FXML
	private TableColumn<Pacco, String> tbDestinatario;
	@FXML
	private TableColumn<Pacco, String> tbIndirizzoPacco;
	@FXML
	private ChoiceBox<String> selezionaIndirizzo;
	
	
	@FXML
	public void initialize() {
		selezionaIndirizzo.setItems(tipologiaDisponibile);
		selezionaIndirizzo.setValue("PREDEFINITO");
		hboxNascosta.setDisable(true);
	}

	public void settaPacchi(Negozio negozio) throws SQLException {
		List<Pacco> pacco = new GestorePacco().getByMittente(negozio);
	    pacchiNonAssegnati = FXCollections.observableArrayList(pacco);
	    tbId.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().id)));
	    tbDestinatario.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().destinatario.eMail));
	    tbIndirizzoPacco.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
	    pacchiNonAssegnati.removeIf(p -> p.id == 0);
	    tabellaPacchi.setItems(pacchiNonAssegnati);
	    tabellaPacchi.setPlaceholder(new Label(negozio.denominazione +" non contiene pacchi!"));
	}
	
	public void settaClienti() throws SQLException {
		lblEmail.setText(" ");
		List<Cliente> cliente = new GestoreCliente().getAll();
	    clienteDisponibili = FXCollections.observableArrayList(cliente);
	    tbEmail.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().eMail));
	    tbIndirizzo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().indirizzo.toString()));
	    tabellaCliente.setItems(clienteDisponibili);
	    tabellaCliente.setPlaceholder(new Label("C3 non contiene clienti!"));
	}
	
	public void actionRicerca(ActionEvent actionEvent) throws SQLException {
		lblEmail.setText(" ");
		List<Cliente> unico = new ArrayList<Cliente>();
		String email = txtEmail.getText().toUpperCase();
		if(!email.isBlank()){
			if(clienteEsistente(email)) {
				Cliente cliente = prendiCliente(email);
				unico.add(cliente);
				clienteDisponibili = FXCollections.observableArrayList(unico);
				tabellaCliente.setItems(clienteDisponibili);
			}else{
				lblEmail.setText(email + " non esistente nel sistema");
			}	
		}
	}
	
	public void actionCopri(ActionEvent actionEvent) throws SQLException {
		 if(selezionaIndirizzo.getValue().contains("NUOVO")) {
			 hboxNascosta.setDisable(false);
		 }else {
			 hboxNascosta.setDisable(true);
		 }
			 
	}
	
	public void actionResetta(ActionEvent actionEvent) throws SQLException {
		 settaClienti();
	}
	
	public void actionAnnulla(ActionEvent actionEvent) throws SQLException {
		String id = txtId.getText();
		if(pacchiNonAssegnati.stream().anyMatch(p -> p.id == (Integer.parseInt(id)))) {
			Pacco pacco = pacchiNonAssegnati.stream().filter(p -> p.id == (Integer.parseInt(id))).findAny().get();
			pacchiNonAssegnati.remove(pacco);
			new GestorePacco().delete(Integer.parseInt(id));
		}
	}
	
	public void actionCreaPacco(ActionEvent actionEvent) throws SQLException {
		String email = txtEmail.getText().toUpperCase();
		LocalDate data = dpData.getValue();
		String via = txtVia.getText();
		String citta = txtCitta.getText();
		String prov = txtProv.getText();
		String civico = txtCivico.getText();
		String cap = txtCap.getText();
		if(controllaInfo(email, data, via, citta, prov, civico, cap)){
			lblErrore.setText("");
			lblErroreIndirizzo.setText("");
			Cliente cliente = prendiCliente(email);
			Pacco pacco;
			Date date = Date.from(data.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			if(selezionaIndirizzo.getValue().equals("NUOVO")) {
				Indirizzo indirizzo = new Indirizzo(via, civico, citta, cap, prov);
				pacco = new Pacco(cliente, getNegozio(), date, indirizzo);
			}else
				pacco = new Pacco(cliente, getNegozio(), date, cliente.indirizzo);
			recapInfo(pacco);
		}
	}
	
	private void recapInfo(Pacco pacco) throws SQLException {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Spedito da: "+ pacco.mittente.denominazione + "									"
				+ "Destinatario: " + pacco.destinatario.eMail
				+ " Consegna: "+pacco.dataConsegnaRichiesta+" "
				+pacco.indirizzo.toString(), ButtonType.YES, ButtonType.NO);
		alert.setTitle("Conferma pacco");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			pacchiNonAssegnati.add(pacco);
			new GestorePacco().save(pacco);
		}
    }
	
	private Cliente prendiCliente(String email) {
		return clienteDisponibili.stream().filter(c -> c.eMail.toUpperCase().equals(email)).findAny().get();
	}
	
	private boolean clienteEsistente(String email){
		return clienteDisponibili.stream().anyMatch(c -> c.eMail.toUpperCase().equals(email));
	}
	
	private boolean controllaInfo(String email,LocalDate data,String via,String citta,String prov,String civico,String cap){
		if(!email.isBlank()){
			if(clienteEsistente(email)) {
				if(checkData(data)) {
					if(selezionaIndirizzo.getValue().equals("NUOVO")) {
						if(!(via.isBlank()||citta.isBlank())){
							if((prov.length()==2)||!(prov.isBlank())){
								if(!(civico.isBlank())) {
									if(cap.length()<5||cap.length()>5||!(cap.isBlank())) {
										return true;
									}else
										lblErroreIndirizzo.setText("Cap non valido");
								}else
									lblErroreIndirizzo.setText("Civico non valido");
							}else
								lblErroreIndirizzo.setText("Provincia non valida");
						}else
							lblErroreIndirizzo.setText("Info non valide");
					}else
						return true;
				}else
					lblErrore.setText("Data non valida");
			}else
				lblErrore.setText("Email non valida");
		}else
			lblErrore.setText("Email non esistente nel sistema");
		return false;
		
	}
	
	
	private boolean checkData(LocalDate date) {
		LocalDate oggi = LocalDate.now();
		return !date.isBefore(oggi);
	}
	

	@Override
	public void initData(Account account) throws SQLException {
		setNegozio(account);
		settaPacchi(getNegozio());
		settaClienti();
		
	}

	
	public void setNegozio(Account account) {
		if (account instanceof Negozio) {
			this.negozio = (Negozio) account;
		}
	}

	public Negozio getNegozio() {
		return negozio;
	}
}
