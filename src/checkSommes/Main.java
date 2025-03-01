package checkSommes;

import checkSommes.ig.*;
import checkSommes.modele.Jeu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CheckSum"); // titre de la fenetre

        Jeu jeu = new Jeu() ;
        BorderPane root = new BorderPane();
        root.setTop(new MenuJeu(jeu, primaryStage));
        root.setBottom(new PanneauControle(jeu));
        root.setCenter(new PlateauGraphique(jeu));
        VBox box = new VBox(20) ;
        box.getChildren().add(new Infos(jeu)) ;
        box.getChildren().add(new Historique(jeu)) ;
        root.setRight(box);
        Scene scene = new Scene(root, 1000, 700);
        // charger styles.css
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lancer l'application
    }

}
