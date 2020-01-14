package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import controleur.ControleurDonnees;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.*;
import vue.FenetreDeConnexion;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ObsMessage implements Initializable {
    @FXML private Text textMotCles;
    @FXML private JFXButton boutonLienPrecedent;
    @FXML private JFXButton boutonLienSuivant;
    @FXML private Hyperlink texteLien;
    @FXML private GridPane gridPaneContenuMessage;
    @FXML private GridPane gridPaneImages;
    @FXML private JFXButton boutonImageSuivante;
    @FXML private JFXButton boutonImagePrecedente;
    private List<Image> images;
    private int indexImage;
    private List<Lien> liens;
    private int indexLien;
    @FXML private ImageView image;
    private Message messageAffiche;
    @FXML private BorderPane borderPaneMessage;
    @FXML private Text texteNom;
    @FXML private Text texteDate;
    @FXML private Label texteMessage;
    @FXML private Label texteTitre;
    @FXML private HBox boxCommandes;
    @FXML private Hyperlink linkModifier;
    @FXML private Hyperlink linkSupprimer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        indexImage = 0;
        indexLien = 0;

        texteLien.setOnAction(t -> {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("http://" + liens.get(indexLien).getAdresseLien()));
                } catch (IOException | URISyntaxException exc) {
                    exc.printStackTrace();
                }
            }
        });

        linkModifier.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fenetreCreerMessage.fxml"));
                ObsFenetreModifierMessage obs = new ObsFenetreModifierMessage(messageAffiche);
                loader.setController(obs);
                root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/icon.png")));
                stage.setTitle("Modifier un nouveau message");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            event.consume();
        });

        linkSupprimer.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce message ?");
            Optional<ButtonType> choose = alert.showAndWait();
            if(choose.get() == ButtonType.OK){
                ControleurDonnees.supprimerMessage(messageAffiche);
                ModeleDonnees.getObsFenetrePrincipale().rafraichirMessages();
            }
            event.consume();
        });

        boutonImageSuivante.setOnAction(event -> {
            indexImage++;
            if(indexImage == images.size()){
                indexImage = 0;
            }
            Image img = images.get(indexImage);
            image.setImage(img);
            image.setCache(true);

            event.consume();
        });

        boutonImagePrecedente.setOnAction(event -> {
            indexImage--;
            if(indexImage == -1){
                indexImage = images.size() - 1;
            }
            Image img = images.get(indexImage);
            image.setImage(img);
            image.setCache(true);

            event.consume();
        });

        boutonLienSuivant.setOnAction(event -> {
            indexLien++;
            if(indexLien == liens.size()){
                indexLien = 0;
            }
            texteLien.setText(liens.get(indexLien).getTexteLien());

            event.consume();
        });

        boutonLienPrecedent.setOnAction(event -> {
            indexLien--;
            if(indexLien == -1){
                indexLien = liens.size() - 1;
            }
            texteLien.setText(liens.get(indexLien).getTexteLien());

            event.consume();
        });
    }

    public void definirMessage(Message message){
        messageAffiche = message;

        texteNom.setText(message.getUtilisateur().getPrenom() + " " + message.getUtilisateur().getNom());
        texteDate.setText(message.getDate().toString());
        texteTitre.setText(message.getTitre());
        texteMessage.setText(message.getTexte());

        images = new ArrayList<>();
        for(modele.Image img : message.getImages()){
            try{
                images.add(new javafx.scene.image.Image(img.getAdresseImage()));
            } catch(IllegalArgumentException e){
                //Ne rien faire, impossible d'afficher l'image.
            }
        }
        this.liens = message.getLiens();
        //Si il y a au moins une image, on l'initialise à la première image :
        if(!images.isEmpty()){
            Image img = images.get(0);
            image.setImage(img);
            image.setCache(true);
        }

        if(!liens.isEmpty()){
            texteLien.setText(liens.get(0).getTexteLien());
        }

        if(!message.getMotCles().isEmpty()){
            StringBuilder texteMotCles = new StringBuilder();
            for(MotCle m : message.getMotCles()){
                texteMotCles.append(" ").append(m.getMotCle());
            }
            textMotCles.setText(texteMotCles.toString());
        }

        if(!message.getUtilisateur().getAdresseMail().equals(ModeleDonnees.getUtilisateurConnecte().getAdresseMail())) { borderPaneMessage.getChildren().remove(boxCommandes); }

        if(message.getImages().isEmpty()){ gridPaneImages.getChildren().removeAll(boutonImagePrecedente, boutonImageSuivante, image); }

        if(message.getLiens().isEmpty()){ gridPaneImages.getChildren().removeAll(boutonLienPrecedent, boutonLienSuivant, texteLien); }

        if(message.getMotCles().isEmpty()){ gridPaneContenuMessage.getChildren().remove(textMotCles); }
    }
}