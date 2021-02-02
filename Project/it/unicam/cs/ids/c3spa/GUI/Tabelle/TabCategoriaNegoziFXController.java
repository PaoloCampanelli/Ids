package it.unicam.cs.ids.c3spa.GUI.Tabelle;

import java.sql.SQLException;
import java.util.List;

import it.unicam.cs.ids.c3spa.core.CategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.Negozio;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreCategoriaMerceologica;
import it.unicam.cs.ids.c3spa.core.gestori.GestoreNegozio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TabCategoriaNegoziFXController implements FXTabella {

	private Negozio negozio;
	
	public TabCategoriaNegoziFXController() {
	}

	private ObservableList<CategoriaMerceologica> lcm;
	
	@FXML
	private Button btnInserisci, btnRimuovi;
	@FXML
	private Label lblCategoria, lblID;
	@FXML
	private TextField txtCategoria, txtID;
	@FXML
	private TableView<CategoriaMerceologica> tabellaCategoria;
	@FXML
	private TableColumn<CategoriaMerceologica, String> tbID;
	@FXML
	private TableColumn<CategoriaMerceologica, String> tbNome;
	
	public void setta(Negozio negozio) {
		tabellaCategoria.setPlaceholder(new Label("Non hai categorie attive!"));
		List<CategoriaMerceologica> categoria = negozio.categorie;
		lcm = FXCollections.observableArrayList(categoria);
		lcm.removeIf(c -> c.idCategoria == 0);
		tbID.setCellValueFactory(cd -> new SimpleStringProperty(Integer.toString(cd.getValue().idCategoria)));
		tbNome.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().nome));
		tabellaCategoria.setItems(lcm);	
	}
	
	 public void actionInserisci(ActionEvent actionEvent) throws SQLException {
		 String nome = txtCategoria.getText().toUpperCase();
		 CategoriaMerceologica categoria = new CategoriaMerceologica(nome);
		 if(!(nome.isBlank())){
		 	if(negozio.aggiungiCategoria(categoria)){
				lcm.add(categoria);
				new GestoreCategoriaMerceologica().save(categoria);
				new GestoreNegozio().save(negozio);
			}
		 }else
		 	lblCategoria.setText("Categoria gia' presente");
		 txtCategoria.clear();
	}

	 public void actionRimuovi(ActionEvent actionEvent) throws SQLException {
		try{
			int id = Integer.parseInt(txtID.getText());
			CategoriaMerceologica categoria = prendiCategoria(id);
			if(negozio.rimuoviCategoria(categoria)){
				lcm.remove(categoria);
				new GestoreCategoriaMerceologica().delete(id);
			}else
				lblID.setText("ID non presente");
			txtCategoria.clear();
		}catch(NumberFormatException e){
			lblID.setText("ID non valido");
		}
	 }

	 
	 
	private CategoriaMerceologica prendiCategoria(int idCategoria) {
		 return negozio.categorie.stream().filter(c -> c.idCategoria==idCategoria).findAny().get();
	}
	
	@Override
	public void initData(Account account) throws SQLException {
		setNegozio(account);
		setta(negozio);
	}

	@Override
	public void initData(Account account, String citta, String categoria) throws SQLException {
	}

	public Negozio getNegozio() {
		return negozio;
	}

	public void setNegozio(Account account) {
		if(account instanceof Negozio) {
			Negozio negozio = (Negozio) account;
			this.negozio = negozio;
		}
	}

}
