package checkSommes.ig;

import checkSommes.modele.Coup;
import checkSommes.modele.Jeu;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Historique extends VBox implements Observateur {
    private ArrayList<Label> coups;

    private Jeu jeu;

    public Historique(Jeu jeu) {
        this.jeu = jeu;
        this.coups = new ArrayList<>(5);
        Label text = new Label("Historique (case/sommes) ");
        text.getStyleClass().add("historique");
        this.getChildren().add(text);

        //initialement le nombre de coups est null
        jeu.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
        if (jeu.nbCoups() != coups.size() || jeu.nbCoups() == jeu.NB_COUP_MAX) {
            // rafraichir que si le nombre de coups augmente ou si on atteint 16
            coups.clear();
            this.getChildren().clear();

            Label text = new Label("Historique (case/sommes) ");
            text.getStyleClass().add("historique");
            this.getChildren().add(text);
            for (Coup coup : jeu) {
                Label label = new Label(coup.toString());
                if (coup.estAide()) {
                    label.getStyleClass().add("historiqueAidee");
                }else{
                    label.getStyleClass().add("historiqueSansAide");
                }
                this.coups.add(label);
                this.getChildren().add(label);
            }
        }
    }

}
