package checkSommes.ig;

import checkSommes.modele.Jeu;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Infos extends HBox implements Observateur {
    private ArrayList<Label> vies ;
    private Jeu jeu ;
    public Infos(Jeu jeu) {
        this.jeu = jeu ;
        this.vies = new ArrayList<>(jeu.getNbVies()) ;

        Image imageCoeur = new Image(getClass().getResourceAsStream("/images/coeur.png"), 40, 40, true, true);
        for (int i = 0; i < jeu.getNbVies(); i++) {
            Label vie = new Label();
            ImageView iconCoeur = new ImageView(imageCoeur);
            vie.setGraphic(iconCoeur);
            vies.add(vie); // Ajouter le Label à la liste
            this.getChildren().add(vie); // Ajouter au HBox
        }
        this.setAlignment(Pos.CENTER);
        this.jeu.ajouterObservateur(this) ;
    }
    public void reagir() {
        if(vies.size() != jeu.getNbVies()){
            // Effacer toutes les vies actuelles
            vies.clear();
            this.getChildren().clear();

            // Recréer les vies avec le bon nombre
            for (int i = 0; i < jeu.getNbVies(); i++) {
                Label vie = new Label();
                ImageView iconCoeur = new ImageView(new Image(getClass().getResourceAsStream("/images/coeur.png"), 40, 40, true, true));
                vie.setGraphic(iconCoeur);
                vies.add(vie);
                this.getChildren().add(vie);
            }
        }
        // detection de fin de partie (j'ai choisi de le faire ici)
        if(jeu.jeuTermine()){
            if(jeu.getNbVies() == 0){
                Label mort = new Label();
                ImageView iconMort = new ImageView(new Image(getClass().getResourceAsStream("/images/mort.png"), 110, 110, true, true));
                mort.setGraphic(iconMort);
                //this.setAlignment(Pos.CENTER);
                this.getChildren().add(mort);
            }
            afficherDialogue();
            jeu.reinitialiser();
        }
    }

    private void afficherDialogue(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin De Partie");
        alert.setHeaderText(null);
        if (jeu.getNbVies() == 0) {
            alert.setContentText("Vous avez perdu, appuyez sur OK pour recommencer !");
        } else {
            alert.setContentText("Vous avez gagné, appuyez sur OK pour recommencer !");
        }
        alert.showAndWait();
    }

}