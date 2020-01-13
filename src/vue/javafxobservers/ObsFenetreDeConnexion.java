package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controleur.Connexion;
import controleur.ControleurDonnees;
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
import modele.ModeleDonnees;
import modele.Utilisateur;
import org.dom4j.rule.Mode;
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

    private boolean motDePasseCache = true;

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
            e.consume();
        });

        boutonEye.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            imageEye.setImage(closedEye);
            if(!motDePasseCache){
                fieldMotDePasse.setText(fieldMotDePasse.getPromptText());
                fieldMotDePasse.setPromptText("Mot de passe");
                motDePasseCache = true;
            }
            e.consume();
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

                        ControleurDonnees.initialiserDonnees(loader.getController(), utilisateur);

                        root = loader.load();

                        ModeleDonnees.setObsFenetrePrincipale(loader.getController());

                        Stage stage = new Stage();
                        stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/icon.png")));
                        stage.setTitle("Polyblogger");
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    event.consume();
                    ((Stage) boutonConnection.getScene().getWindow()).close();
                }
                else{
                    errorText.setText("Login ou mot de passe incorrect");
                }
            }
            else{
                errorText.setText("Remplissez tous les champs");
            }
            event.consume();
        });

        boutonInscription.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(ObsFenetreDeConnexion.class.getResource("/resources/fenetreInscription.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/icon.png")));
                stage.setTitle("Créer un compte");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        });

        boutonOptions.setOnAction(event -> {
            //TODO Lancer la fenêtre des options
            event.consume();
        });
    }
}