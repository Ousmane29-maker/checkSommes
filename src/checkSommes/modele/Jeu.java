package checkSommes.modele;

import checkSommes.ig.Observateur;
import checkSommes.modele.fabrique.FabriquePlateau;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class Jeu implements Iterable<Coup>{

    private static final int NB_VIES_INITIAL = 6;
    public static final int NB_COUP_MAX = 16;
    private boolean mode;
    private ArrayList<Observateur> obs ;

    private int ligneCaseAidee;
    private int colonneCaseAidee;

    private Case[][] cases ;


    private ArrayList<Coup> coups;

    private int nbVies;

    private boolean plateauCharge;  // Flag pour savoir si le plateau a changé
    private int nbDeCaseSolutions; // Nombre total de cases qui sont des solutions
    private int nbDeCaseChoisis;   // Nombre de cases actuellement choisies
    public Jeu(){
        this.mode = true; // par defaut, le jeu est en mode "Oui"
        this.obs = new ArrayList<>(5);
        this.coups = new ArrayList<>(5);
        this.nbVies = NB_VIES_INITIAL ; // nombre de vie par defaut

        initialiserPlateau();

        this.plateauCharge = false;
    }

    private void initialiserPlateau(){
        FabriquePlateau fabriquePlateau = FabriquePlateau.getInstance();
        cases = fabriquePlateau.getPlateauParDefaut();
        ligneCaseAidee = -1;
        colonneCaseAidee = -1;

        // Initialisation des compteurs
        nbDeCaseSolutions = calculerNbDeCaseSolutions();
        nbDeCaseChoisis = 0;
    }

    private int calculerNbDeCaseSolutions() {
        int count = 0;
        for (Case[] ligne : cases) {
            for (Case c : ligne) {
                if (c.estSolution()) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean enModeOui(){
        return mode ;
    }

    public int getNbVies(){
        return nbVies;
    }

    public int getNbLignes(){
        return cases.length ;
    }

    public int getNbColonnes(){
        return cases[0].length ;
    }


    public int getLigneCaseAidee() {
        return ligneCaseAidee;
    }

    public int getColonneCaseAidee() {
        return colonneCaseAidee;
    }
    public boolean estPlateauCharge() {
        return plateauCharge;
    }

    public void resetPlateauCharge() {
        plateauCharge = false; // Après avoir réagi, on réinitialise le flag
    }


    public void resetCoordCaseAidee(){
        this.ligneCaseAidee = -1 ;
        this.colonneCaseAidee = -1 ;
    }

    public void switchMode(){
        mode = !mode ;
        this.notifierObservateurs();
    }

    public void ajouterObservateur(Observateur o){
        obs.add(o);
    }

    private void notifierObservateurs() {
        for (Observateur o : this.obs) o.reagir() ;
    }


    public int getValeur(int ligne, int colonne) {
        return cases[ligne][colonne].getValeur() ;
    }

    public int sommeLigne(int ligne){
        int somme = 0;
        for(int j = 0; j < getNbColonnes(); j++){
            if(estSolution(ligne, j)){
                somme+= getValeur(ligne, j);
            }
        }
        return somme;
    }

    public int sommeColonne(int colonne){
        int somme = 0;
        for(int i = 0; i < getNbLignes(); i++){
            if(estSolution(i, colonne)){
                somme+= getValeur(i, colonne);
            }

        }
        return somme;
    }

    public boolean estSolution(int ligne, int colonne) {
        return cases[ligne][colonne].estSolution() ;
    }

    public void choisirCase(int ligne, int colonne) {
        if(enModeOui()){
            if(!cases[ligne][colonne].estSolution()){
                // l'utilisateur a choisi la mauvaise case
                cases[ligne][colonne].setCouleur(1);
                if(nbVies > 0){
                    nbVies--;
                }
            }else {
                nbDeCaseChoisis = cases[ligne][colonne].getCouleur () != 2 ? nbDeCaseChoisis+1 : nbDeCaseChoisis;
                cases[ligne][colonne].setChoisie(true);
                cases[ligne][colonne].setCouleur(2);
                Coup coup = new Coup(ligne, colonne, sommeLigne(ligne), sommeColonne(colonne), false) ;
                this.ajouterCoup(coup);

            }
        }else{
            nbDeCaseChoisis = cases[ligne][colonne].getCouleur () == 2 ? nbDeCaseChoisis-1 : nbDeCaseChoisis;
            cases[ligne][colonne].setChoisie(false);
            cases[ligne][colonne].setCouleur(1);
            Coup coup = new Coup(ligne, colonne, sommeLigne(ligne), sommeColonne(colonne), false) ;
            this.ajouterCoup(coup);
        }
        notifierObservateurs();
    }

    public int getCouleur(int ligne, int colonne) {
        return cases[ligne][colonne].getCouleur();
    }

    public void aider() {
        if(nbVies > 0){
            int i = 0;
            boolean find = false;
            while(i < getNbLignes() && !find){
                int j = 0;
                while(j < getNbColonnes() && !find){
                    if(cases[i][j].getCouleur() == 0){
                        if(cases[i][j].estSolution()){
                            cases[i][j].setChoisie(true);
                            cases[i][j].setCouleur(2);
                        }else{
                            cases[i][j].setCouleur(1);
                        }
                        find = true ;
                        ligneCaseAidee = i ;
                        colonneCaseAidee = j;
                        // ajouter le coup aider
                        Coup coup = new Coup(i, j, sommeLigne(i), sommeColonne(j), true);
                        this.ajouterCoup(coup);
                        nbVies = Math.max(nbVies - 2, 0); // jamais negatif
                        notifierObservateurs();
                    }
                    j++;
                }
                i++;
            }

        }

    }

    public boolean jeuTermine(){
        return nbVies == 0 || nbDeCaseChoisis == nbDeCaseSolutions ;
    }

    public void reinitialiser() {
        resetCases();
        this.nbVies = NB_VIES_INITIAL;
        resetCoordCaseAidee();
        coups.clear();
        nbDeCaseChoisis = 0;
        notifierObservateurs();
    }

    public void ajouterCoup(Coup coup){
        if(nbCoups() == NB_COUP_MAX){
            this.coups.remove(0); // supprimer la premiere avant d'ajouter une autre
        }
        this.coups.add(coup);

    }

    @Override
    public Iterator<Coup> iterator() {
        return coups.iterator();
    }

    public int nbCoups() {
        return coups.size() ;
    }

    public void ouvrir(File fichier) throws FileNotFoundException, IllegalArgumentException  {
        FabriquePlateau fabriquePlateau = FabriquePlateau.getInstance();
        this.cases = fabriquePlateau.getPlateau(fichier);
        this.ligneCaseAidee = -1;
        this.colonneCaseAidee = -1;
        nbDeCaseSolutions = calculerNbDeCaseSolutions();
        nbDeCaseChoisis = 0;
        this.nbVies = NB_VIES_INITIAL ;
        this.coups.clear();
        this.mode = true;
        this.plateauCharge = true; // nouveau plateau a ete charge.
        notifierObservateurs();
    }


    private void resetCases(){
        for(int i = 0; i < getNbLignes(); i++){
            for(int j = 0; j < getNbColonnes(); j++){
                cases[i][j].resetCase();
            }
        }
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Jeu { \n");
        stringBuilder.append(" mode = ").append(mode);
        stringBuilder.append("\n nbLignes = ").append(getNbLignes());
        stringBuilder.append("\n nbColonnes = ").append(getNbColonnes());
        stringBuilder.append("\n nbVies =  ").append(nbVies);
        stringBuilder.append("\n cases { \n") ;
        for(int i = 0; i < getNbLignes(); i++){
            for(int j = 0; j < getNbColonnes(); j++){
                stringBuilder.append("    ");
                stringBuilder.append(cases[i][j]).append('\n');
            }
        }
        stringBuilder.append(" } \n coups { \n");
        for(Coup coup : this){
            stringBuilder.append("    ");
            stringBuilder.append(coup).append('\n');
        }
        stringBuilder.append(" }\n}\n");
        return stringBuilder.toString();
    }
}

