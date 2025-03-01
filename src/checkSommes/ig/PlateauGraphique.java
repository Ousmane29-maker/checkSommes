package checkSommes.ig;

import checkSommes.modele.Jeu;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

public class PlateauGraphique extends GridPane implements Observateur {
    private Button[][] buttons ;
    private Jeu jeu ;
    public PlateauGraphique(Jeu jeu){
        this.jeu = jeu ;

        this.setHgap(5);  // Espacement horizontal entre les boutons
        this.setVgap(5); //Espacement vertical entre les boutons
        this.setPadding(new Insets(20, 20, 20, 20)); // Marges autour du GridPane

        initialiserPlateau();

        this.jeu.ajouterObservateur(this) ;

    }
    @Override
    public void reagir() {
        if(jeu.estPlateauCharge()){
            chargerNouveauPlateau();
            jeu.resetPlateauCharge(); // remettre le booleen "plateau est charge" a false.
        }else{
            for(int i = 0; i < jeu.getNbLignes(); i++){
                for(int j = 0; j < jeu.getNbColonnes(); j++){
                    buttons[i][j].getStyleClass().removeAll("buttonEnVertClair", "buttonEnMarron"); // enlever les styles avant d'entre remettre
                    if (jeu.getCouleur(i, j) == 0) {
                        buttons[i][j].getStyleClass().add("buttonEnVert");
                    }else{
                        if (jeu.getCouleur(i, j) == 1) {
                            buttons[i][j].getStyleClass().add("buttonEnVertClair");
                        } else if (jeu.getCouleur(i, j) == 2) {
                            buttons[i][j].getStyleClass().add("buttonEnMarron");
                        }
                    }

                    // rotation d'une 1 seconde de la case aidee.
                    if(i == jeu.getLigneCaseAidee() && j == jeu.getColonneCaseAidee()){
                        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), buttons[i][j]);
                        rotateTransition.setByAngle(360); // un tour complet
                        rotateTransition.play();
                        jeu.resetCoordCaseAidee(); // reset des cordonnees
                    }
                }
            }
        }

    }

    private void initialiserPlateau() {
        this.buttons = new Button[jeu.getNbLignes()][jeu.getNbColonnes()];
        for(int j = 0; j < jeu.getNbColonnes(); j++ ){
            Label label = new Label( jeu.sommeColonne(j)+"");
            label.setMaxWidth(Double.MAX_VALUE); // taille max du largeur illimite
            this.setHgrow(label, Priority.ALWAYS); // le label occupe toute la place horizontal disponible
            label.setAlignment(Pos.CENTER); // centrer le texte
            label.getStyleClass().add("labelSomme");
            this.add(label, j+1, 0); // pour le gridPane faut inverser c'est j d'abord puis i.
        }
        for(int i = 0; i < jeu.getNbLignes(); i++ ){
            Label label = new Label(jeu.sommeLigne(i)+"");
            label.setPrefWidth(30); // pour pouvoir contenir 2 chiffre
            label.getStyleClass().add("labelSomme");
            this.add(label, 0, i+1);
        }
        for(int i = 0; i < jeu.getNbLignes(); i++){
            for(int j = 0; j < jeu.getNbColonnes(); j++){
                Button button = new Button(jeu.getValeur(i,j)+"");
                // taille des buttons dynamiques
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                this.setHgrow(button, Priority.ALWAYS);
                this.setVgrow(button, Priority.ALWAYS);
                button.getStyleClass().add("buttonEnVert") ;
                this.buttons[i][j] = button ;
                this.add(button, j+1, i+1); // decaler de 1 pour la premiere ligne et la premiere colonne(sommeColonne, sommeLigne)

                int finalI = i;
                int finalJ = j;
                button.setOnAction(e -> jeu.choisirCase(finalI, finalJ));
            }
        }
    }

    private void chargerNouveauPlateau(){
        this.getChildren().clear();
        initialiserPlateau();
    }

}
