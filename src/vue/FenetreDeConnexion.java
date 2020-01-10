package vue;

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
        primaryStage.getIcons().add(new Image(FenetreDeConnexion.class.getResourceAsStream( "/resources/icon.png" )));
        primaryStage.setTitle("Polyblog");
        primaryStage.setScene(new Scene(root, 600, 400));

        ObsFenetreDeConnexion c = loader.getController();

        primaryStage.show();
    }
}
