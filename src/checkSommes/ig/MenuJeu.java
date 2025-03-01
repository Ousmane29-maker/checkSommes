package checkSommes.ig;

import checkSommes.modele.Jeu;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class MenuJeu extends MenuBar implements Observateur {
    private Jeu jeu;

    public MenuJeu(Jeu jeu, Stage mainStage){
        this.jeu = jeu;
        Menu fichier = new Menu("Fichier");
        MenuItem recommencer = new MenuItem("Recommencer");
        MenuItem ouvrir = new MenuItem("Ouvrir");
        MenuItem quitter = new MenuItem("Quitter");
        fichier.getItems().addAll(recommencer, ouvrir, quitter);

        recommencer.setOnAction(e-> jeu.reinitialiser());
        ouvrir.setOnAction(new EcouteurOuvrir(jeu, mainStage));
        quitter.setOnAction(e-> Platform.exit());
        this.getMenus().add(fichier) ;

        this.jeu.ajouterObservateur(this);
        // menu-bar et menu sont des classes CSS par défaut en JavaFX. Elles sont
        // automatiquement appliquées aux éléments correspondants.
    }
    @Override
    public void reagir() {

    }
}
