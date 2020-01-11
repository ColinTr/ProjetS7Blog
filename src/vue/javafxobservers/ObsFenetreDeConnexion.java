package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controleur.Connexion;
import controleur.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.Utilisateur;
import vue.FenetreDeConnexion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ObsFenetreDeConnexion implements Initializable {

    @FXML JFXButton boutonInscription;
    @FXML JFXButton boutonConnection;
    @FXML Button boutonOptions;
    @FXML Button boutonEye;

    @FXML ImageView imageEye;

    @FXML JFXTextField fieldNomDeCompte;
    @FXML JFXPasswordField fieldMotDePasse;

    @FXML Text errorText;

    boolean motDePasseCache = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connexion.init("classique");

        Image openEye = new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/SMALL_eye-opened.png"));
        Image closedEye = new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/SMALL_eye-closed.png"));

        boutonEye.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            imageEye.setImage(openEye);
            if(!fieldMotDePasse.getText().isEmpty()){
                fieldMotDePasse.setPromptText(fieldMotDePasse.getText());
                fieldMotDePasse.setText(null);
                motDePasseCache = false;
            }
        });

        boutonEye.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            imageEye.setImage(closedEye);
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
                    creerFenetre("/resources/fenetrePrincipale.fxml", "/resources/images/icon.png", "Polyblogger");
                    ((Stage) boutonConnection.getScene().getWindow()).close();
                }
                else{
                    errorText.setText("Login ou mot de passe incorrect");
                }
            }
            else{
                errorText.setText("Remplissez tous les champs");
            }
        });

        boutonInscription.setOnAction(event -> {
            creerFenetre("/resources/fenetreInscription.fxml", "/resources/images/icon.png", "Créer un compte");
        });

        boutonOptions.setOnAction(event -> {
            //TODO Lancer la fenêtre des options
        });
    }

    private void creerFenetre(String fxml, String icon, String titre){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(ObsFenetreDeConnexion.class.getResource(fxml));
            root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream(icon)));
            stage.setTitle(titre);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}