package vue;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controleur.Connexion;
import controleur.UtilisateurDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modele.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ObsFenetreDeConnexion implements Initializable {

    @FXML JFXTextField fieldNomDeCompte;
    @FXML JFXPasswordField fieldMotDePasse;
    @FXML Button boutonOptions;
    @FXML JFXButton boutonConnection;
    @FXML Button boutonEye;
    @FXML ImageView imageEye;

    boolean motDePasseCache = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connexion.init("classique");

        Image openEye = new Image(FenetreDeConnexion.class.getResourceAsStream( "/resources/SMALL_eye-opened.png" ));
        Image openClose = new Image(FenetreDeConnexion.class.getResourceAsStream( "/resources/SMALL_eye-closed.png" ));

        boutonEye.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            imageEye.setImage(openEye);
            if(!fieldMotDePasse.getText().isEmpty()){
                fieldMotDePasse.setPromptText(fieldMotDePasse.getText());
                fieldMotDePasse.setText(null);
                motDePasseCache = false;
            }
        });

        boutonEye.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            imageEye.setImage(openClose);
            if(!motDePasseCache){
                fieldMotDePasse.setText(fieldMotDePasse.getPromptText());
                fieldMotDePasse.setPromptText("Mot de passe");
                motDePasseCache = true;
            }
        });

        boutonConnection.setOnAction(event -> {

            String mail = fieldNomDeCompte.getText();
            String motDePasse = fieldMotDePasse.getText();
            if(!mail.isEmpty() && !motDePasse.isEmpty()){
                Utilisateur utilisateur = UtilisateurDAO.testerAuthentification(mail, motDePasse);

                if(utilisateur != null){

                    //On ouvre la fenetre principale :
                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(ObsFenetreDeConnexion.class.getResource("/resources/fenetrePrincipale.fxml"));
                        root = loader.load();
                        Stage stage = new Stage();
                        stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream( "/resources/icon.png" )));
                        stage.setTitle("Polyblogger");
                        stage.setScene(new Scene(root, 1080, 720));
                        stage.show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    ((Stage) boutonConnection.getScene().getWindow()).close();
                }
                else{
                    //TODO message d'erreur OU popup indiquant que l'utilisateur n'existe pas
                }
            }
        });

        boutonOptions.setOnAction(event -> {
            //TODO Lancer la fenêtre des options
        });
    }
}