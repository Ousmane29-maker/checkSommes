package checkSommes.ig;

import checkSommes.modele.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EcouteurBoutonCase implements EventHandler<ActionEvent> {
    private Jeu jeu;
    private  int ligne;
    private int colonne;
    public EcouteurBoutonCase(Jeu jeu, int ligne, int colonne) {
        this.jeu = jeu;
        this.ligne = ligne;
        this.colonne = colonne;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        jeu.choisirCase(ligne, colonne);
    }
}
