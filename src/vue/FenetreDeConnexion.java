package vue;

import controleur.Connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class FenetreDeConnexion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fenetreConnexion.fxml"));
        Pane root = loader.load();
        primaryStage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream("/resources/images/icon.png")));
        primaryStage.setTitle("Polyblogger");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    @Override
    public void stop(){
        Connexion.close();
    }

}