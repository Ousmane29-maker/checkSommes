package checkSommes.ig;

import checkSommes.modele.Jeu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;



public class PanneauControle extends HBox implements Observateur{
    private Jeu jeu;
    private Button ouiNon, help;
    private ImageView iconOui, iconNon; // Memoriser les 2 icon pour pouvoir les utiliser directement dans reagir().

    public PanneauControle(Jeu jeu) {
        this.jeu = jeu;
        this.ouiNon = new Button();
        ouiNon.setTooltip(new Tooltip("Appuyer pour changer de mode"));
        this.help = new Button();
        help.setTooltip(new Tooltip("Demander de l'aide, coût = 2 vies"));
        Image imageOui = new Image(getClass().getResourceAsStream("/images/oui.png"), 48, 48, true, true);
        this.iconOui = new ImageView(imageOui);
        Image imageNon = new Image(getClass().getResourceAsStream("/images/non.png"), 48, 48, true, true);
        this.iconNon = new ImageView(imageNon);

        //style button
        this.help.getStyleClass().add("buttonControle");
        this.ouiNon.getStyleClass().add("buttonControle");

        Image imagehelp = new Image(getClass().getResourceAsStream("/images/help.png"), 48, 48, true, true);
        ImageView iconhelp = new ImageView(imagehelp);
        ouiNon.setGraphic(this.iconOui); // oui par defaut.
        help.setGraphic(iconhelp);

        this.getChildren().addAll(ouiNon, help) ; // Ajout des boutons dans le PanneauControle.
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20, 0,30, 0));

        ouiNon.setOnAction(e-> jeu.switchMode());
        help.setOnAction(e-> jeu.aider());

        this.jeu.ajouterObservateur(this); // inscrire PanneauControle.
    }

    @Override
    public void reagir() {
        if (jeu.enModeOui()){
            ouiNon.setGraphic(iconOui);
        }else{
            ouiNon.setGraphic(iconNon);
        }
    }


}