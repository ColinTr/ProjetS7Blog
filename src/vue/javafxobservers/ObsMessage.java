package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ObsMessage implements Initializable {
    @FXML private GridPane gridPaneContenuMessage;
    @FXML private GridPane gridPaneImages;
    @FXML private JFXButton boutonImageSuivante;
    @FXML private JFXButton boutonImagePrecedente;
    private List<Image> images;
    private int indexImage;
    @FXML private ImageView image;

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

        linkModifier.setOnAction(event -> {
            System.out.println("modifier " + texteTitre);
        });

        linkSupprimer.setOnAction(event -> {
            System.out.println("supprimer " + texteTitre);
        });

        boutonImageSuivante.setOnAction(event -> {
            indexImage++;
            if(indexImage == images.size()){
                indexImage = 0;
            }
            image = new ImageView(images.get(indexImage));
        });

        boutonImagePrecedente.setOnAction(event -> {
            indexImage--;
            if(indexImage == 0){
                indexImage = images.size();
            }
            image = new ImageView(images.get(indexImage));
        });
    }

    public void supprimerCommandes(){
        borderPaneMessage.getChildren().remove(boxCommandes);
    }

    public void supprimerImages(){
        gridPaneContenuMessage.getChildren().remove(gridPaneImages);
    }

    public void definirMessage(String nom, String date, String titre, String message, List<Image> imgs){
        texteNom.setText(nom);
        texteDate.setText(date);
        texteTitre.setText(titre);
        texteMessage.setText(message);
        images = imgs;
    }
}
