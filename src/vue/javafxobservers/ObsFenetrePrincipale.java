package vue.javafxobservers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controleur.MessageDAO;
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
import modele.Utilisateur;
import vue.FenetreDeConnexion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ObsFenetrePrincipale implements Initializable {

    private static Utilisateur utilisateurConnecte;
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

        boutonSupprimerFiltres.setOnAction(event -> {
            datePicker.setValue(null);
            fieldMotsCles.setText(null);
            rafraichirMessages();
        });

        boutonChercher.setOnAction(event -> {
            rafraichirMessages();
        });

        boutonPosterUnMessage.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fenetreCreerMessage.fxml"));
                root = loader.load();
                Stage stage = new Stage();
                ObsFenetreCreerMessage obsCreerMessage = loader.getController();
                obsCreerMessage.setUtilisateurConnecte(utilisateurConnecte);
                stage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/icon.png")));
                stage.setTitle("Poster un nouveau message");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        rafraichirMessages();
    }

    private void rafraichirMessages(){

        LocalDate localDate = datePicker.getValue();
        System.out.println(localDate);

        String[] motCles = null;
        if(fieldMotsCles.getText() != null){
            motCles = fieldMotsCles.getText().split(" ");
            System.out.println(Arrays.toString(motCles));
        }

        List<Message> messages = MessageDAO.getAllMessages();
        for(Message m : messages){
            Parent root = null;
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/message.fxml"));
                root = loader.load();
                ObsMessage obsMessage = loader.getController();

                List<javafx.scene.image.Image> listeImages = new ArrayList<>();
                for(modele.Image img : m.getImages()){
                    listeImages.add(new javafx.scene.image.Image(img.getAdresseImage()));
                }

                obsMessage.definirMessage(m.getUtilisateur().getPrenom() + " " + m.getUtilisateur().getNom(), m.getDate().toString(), m.getTitre(), m.getTexte(), listeImages);

                if(!m.getUtilisateur().getAdresseMail().equals(utilisateurConnecte.getAdresseMail())) {
                    obsMessage.supprimerCommandes();
                }

                if(m.getImages().isEmpty()){
                    obsMessage.supprimerImages();
                }

            } catch(IOException e){
                e.printStackTrace();
            }
            vboxPrincipale.getChildren().add(root);
        }
    }

    public static void setUtilisateurConnecte(Utilisateur utilisateur){
        utilisateurConnecte = utilisateur;
    }
}