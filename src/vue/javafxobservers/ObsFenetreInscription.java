package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controleur.ControleurDonnees;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ObsFenetreInscription implements Initializable {

    @FXML private JFXButton boutonCreerCompte;
    @FXML private JFXTextField fieldMail;
    @FXML private JFXTextField fieldNom;
    @FXML private JFXTextField fieldPrenom;
    @FXML private JFXTextField fieldAdresse;
    @FXML private JFXPasswordField fieldMotDePasse;
    @FXML private Text errorMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        boutonCreerCompte.setOnAction(event -> {
            String mail = fieldMail.getText();
            String nom = fieldNom.getText();
            String prenom = fieldPrenom.getText();
            String adresse = fieldAdresse.getText();
            String motDePasse = fieldMotDePasse.getText();

            if(!mail.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !motDePasse.isEmpty()){
                if(ControleurDonnees.inscription(nom, prenom, adresse, mail, motDePasse)){
                    ((Stage) boutonCreerCompte.getScene().getWindow()).close();
                }
                else{
                    errorMessage.setText("Un compte avec la même adresse Email existe déjà.");
                }
            }
            else{
                errorMessage.setText("Merci de remplir tous les champs.");
            }

            event.consume();
        });
    }
}