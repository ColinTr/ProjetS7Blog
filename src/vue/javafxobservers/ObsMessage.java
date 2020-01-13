package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import controleur.MessageDAO;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import modele.Lien;
import modele.Message;
import modele.MotCle;
import modele.Utilisateur;

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
    @FXML private RowConstraints rowImages;
    @FXML private RowConstraints rowLiens;
    @FXML private GridPane gridPaneContenuMessage;
    @FXML private GridPane gridPaneImages;
    @FXML private JFXButton boutonImageSuivante;
    @FXML private JFXButton boutonImagePrecedente;
    private List<Image> images;
    private int indexImage;
    private List<Lien> liens;
    private int indexLien;
    @FXML private ImageView image;
    private ObsFenetrePrincipale obsFenetrePrincipale;
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
            System.out.println("modifier " + texteTitre);
            event.consume();
        });

        linkSupprimer.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer ce message ?");
            Optional<ButtonType> choose = alert.showAndWait();
            if(choose.get() == ButtonType.OK){
                MessageDAO.supprimerMessage(messageAffiche);
                obsFenetrePrincipale.rafraichirMessages();
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

    public void supprimerCommandes(){
        borderPaneMessage.getChildren().remove(boxCommandes);
    }

    public void supprimerImages(){
        gridPaneImages.getChildren().removeAll(boutonImagePrecedente, boutonImageSuivante, image);
    }

    public void supprimerLiens(){
        gridPaneImages.getChildren().removeAll(boutonLienPrecedent, boutonLienSuivant, texteLien);
    }

    public void supprimerMotsCles(){
        gridPaneContenuMessage.getChildren().remove(textMotCles);
    }

    public void definirMessage(Message message, Utilisateur utilisateurConnecte){
        messageAffiche = message;

        texteNom.setText(message.getUtilisateur().getPrenom() + " " + message.getUtilisateur().getNom());
        texteDate.setText(message.getDate().toString());
        texteTitre.setText(message.getTitre());
        texteMessage.setText(message.getTexte());

        images = new ArrayList<>();
        for(modele.Image img : message.getImages()){
            images.add(new javafx.scene.image.Image(img.getAdresseImage()));
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

        if(!message.getUtilisateur().getAdresseMail().equals(utilisateurConnecte.getAdresseMail())) { supprimerCommandes(); }

        if(message.getImages().isEmpty()){ supprimerImages(); }

        if(message.getLiens().isEmpty()){ supprimerLiens(); }

        if(message.getMotCles().isEmpty()){ supprimerMotsCles(); }
    }

    public void setObsFenetrePrincipale(ObsFenetrePrincipale obsFenetrePrincipale) { this.obsFenetrePrincipale = obsFenetrePrincipale; }
}