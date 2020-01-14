package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controleur.ControleurDonnees;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.Message;
import modele.ModeleDonnees;
import vue.FenetreDeConnexion;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ObsFenetrePrincipale implements Initializable {

    @FXML private JFXButton boutonPosterUnMessage;
    @FXML private DatePicker datePicker;
    @FXML private JFXTextField fieldMotsCles;
    @FXML private JFXButton boutonChercher;
    @FXML private JFXButton boutonSupprimerFiltres;
    @FXML private VBox vboxPrincipale;
    @FXML private ScrollPane scrollpanePrincipale;
    @FXML private Text textNomUtilisateur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textNomUtilisateur.setText(ModeleDonnees.getUtilisateurConnecte().getPrenom() + " " + ModeleDonnees.getUtilisateurConnecte().getNom());

        boutonSupprimerFiltres.setOnAction(event -> {
            datePicker.setValue(null);
            fieldMotsCles.setText(null);
            rafraichirMessages();

            event.consume();
        });

        boutonChercher.setOnAction(event -> {
            rafraichirMessages();
            event.consume();
        });

        boutonPosterUnMessage.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fenetreCreerMessage.fxml"));
                ObsFenetreCreerMessage obs = new ObsFenetreCreerMessage();
                loader.setController(obs);
                root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/icon.png")));
                stage.setTitle("Poster un nouveau message");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            event.consume();
        });

        rafraichirMessages();
    }

    public void rafraichirMessages(){

        vboxPrincipale.getChildren().clear();

        String[] motCles = null;
        if(fieldMotsCles.getText() != null && !fieldMotsCles.getText().isEmpty()){
            motCles = fieldMotsCles.getText().split(" ");
        }

        for(Message message : ControleurDonnees.getMessagesFiltres(motCles,  datePicker.getValue())){
            Parent root = null;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/message.fxml"));
                root = loader.load();
                ObsMessage obsMessage = loader.getController();
                obsMessage.definirMessage(message); //Chaque obsMessage possède un objet Message pour pouvoir le supprimer/modifier

            } catch(IOException e){
                e.printStackTrace();
            }
            vboxPrincipale.getChildren().add(root);
        }
    }
}