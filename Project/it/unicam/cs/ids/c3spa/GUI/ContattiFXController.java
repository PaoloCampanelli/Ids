package it.unicam.cs.ids.c3spa.GUI;

import it.unicam.cs.ids.c3spa.core.Amministratore;
import it.unicam.cs.ids.c3spa.core.astratto.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;


public class ContattiFXController implements FXStage{

    private Amministratore amministratore;

    @FXML
    Label lblNome, lblMail, lblTel;
    @FXML
    ImageView logo;

    public ContattiFXController(){
    }

    @FXML
    public void initialize(){
        logo.setImage(new Image(getClass().getResourceAsStream("resources/logo.png")));
    }

    private void settaDati(){
        lblNome.setText(getAmministratore().denominazione);
        lblMail.setText(getAmministratore().eMail);
        lblTel.setText(getAmministratore().telefono);
    }

    private Amministratore getAmministratore(){
       return amministratore;
    }

    @Override
    public void initData(Account account) throws SQLException {
        setAmministratore(account);
        settaDati();
    }

   private void setAmministratore(Account account){
        if(account instanceof Amministratore){
            this.amministratore = (Amministratore) account;
        }
   }
}
