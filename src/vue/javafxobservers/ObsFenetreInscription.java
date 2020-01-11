package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ObsFenetreInscription implements Initializable {

    @FXML private JFXButton boutonCreerCompte;
    @FXML private JFXTextField fieldMail;
    @FXML private JFXTextField fieldNom;
    @FXML private JFXTextField fieldPrenom;
    @FXML private JFXTextField fieldAdresse;
    @FXML private JFXPasswordField fieldMotDePasse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        boutonCreerCompte.setOnAction(event -> {
            String mail = fieldMail.getText();
            String nom = fieldNom.getText();
            String prenom = fieldPrenom.getText();
            String adresse = fieldAdresse.getText();
            String motDePasse = fieldMotDePasse.getText();

            if(!mail.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !motDePasse.isEmpty()){

            }
            else{
                //TODO MESSAGE D'ERREUR
            }
        });

    }

}
