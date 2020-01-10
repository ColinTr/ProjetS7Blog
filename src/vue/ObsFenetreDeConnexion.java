package vue;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controleur.Connexion;
import controleur.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modele.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;

public class ObsFenetreDeConnexion implements Initializable {

    @FXML JFXTextField fieldNomDeCompte;
    @FXML JFXPasswordField fieldMotDePasse;
    @FXML Button boutonOptions;
    @FXML JFXButton boutonConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connexion.init("classique");

        boutonConnection.setOnAction(event -> {
            String mail = fieldNomDeCompte.getText();
            String motDePasse = fieldMotDePasse.getText();
            if(!mail.isEmpty() && !motDePasse.isEmpty()){
                Utilisateur utilisateur = UtilisateurDAO.testerAuthentification(mail, motDePasse);

                System.out.println(UtilisateurDAO.SHA512(motDePasse));

                if(utilisateur != null){
                    //TODO Lancer la fenêtre principale
                    ((Stage) boutonConnection.getScene().getWindow()).close();
                }
                else{
                    //TODO message d'erreur OU popup indiquant que l'utilisateur n'existe pas
                }
            }
        });

        boutonOptions.setOnAction(event -> {

        });
    }
}