package checkSommes.ig;

import checkSommes.modele.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class EcouteurOuvrir implements EventHandler<ActionEvent> {
    private Jeu jeu ;
    private Stage stage;
    public EcouteurOuvrir(Jeu jeu, Stage mainStage) {
        this.jeu = jeu;
        stage = mainStage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                jeu.ouvrir(selectedFile);  // Remonte à `Jeu`, qui utilisera `FabriquePlateau`
            } catch (FileNotFoundException e) {
                afficherErreur("Fichier introuvable", "Le fichier sélectionné n'existe pas.");
            } catch (IllegalArgumentException e) {
                afficherErreur("Erreur de format", e.getMessage());
            }
        }
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait() ;
    }
}
