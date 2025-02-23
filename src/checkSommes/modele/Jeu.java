package checkSommes.modele;

import checkSommes.ig.Observateur;

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
    private int nbLignes ;
    private int nbColonnes ;

    private String nomFichier;

    private ArrayList<Coup> coups;

    private int nbVies;
    public Jeu(){
        this.mode = true; // par defaut, le jeu est en mode "Oui"
        this.obs = new ArrayList<>(5);
        this.nbVies = NB_VIES_INITIAL ; // nombre de vie par defaut
        this.nomFichier = "jeu1.txt" ;
        FabriquePlateau fabriquePlateau = new FabriquePlateau(nomFichier);
        this.nbLignes = fabriquePlateau.getNbLignes() ;
        this.nbColonnes = fabriquePlateau.getNbColonnes(); ;
        this.cases = fabriquePlateau.getPlateau() ;

        this.ligneCaseAidee = -1 ;
        this.colonneCaseAidee = -1 ;

        this.coups = new ArrayList<>(5);

    }

    public boolean enModeOui(){
        return mode ;
    }

    public int getNbVies(){
        return nbVies;
    }

    public int getNbLignes(){
        return nbLignes ;
    }

    public int getNbColonnes(){
        return nbColonnes ;
    }


    public int getLigneCaseAidee() {
        return ligneCaseAidee;
    }

    public int getColonneCaseAidee() {
        return colonneCaseAidee;
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
                //l'utilisateur a choisi la mauvaise case
                if(nbVies > 0){
                    nbVies--;
                }
            }else {
                cases[ligne][colonne].setChoisie(true);
                cases[ligne][colonne].setCouleur(2);
                Coup coup = new Coup(ligne, colonne, sommeLigne(ligne), sommeColonne(colonne), false) ;
                this.ajouterCoup(coup);

            }
        }else{
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
            while(i < nbLignes && !find){
                int j = 0;
                while(j < nbColonnes && !find){
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
                        nbVies -=2;
                        if(nbVies < 0){
                            nbVies = 0;
                        }
                        notifierObservateurs();
                    }
                    j++;
                }
                i++;
            }

        }

    }

    public boolean jeuTermine(){
        return nbVies == 0 || casesChoisisSontCorrectes();
    }

    private boolean casesChoisisSontCorrectes() {
        boolean correct = true;
        int i = 0;
        while (i < nbLignes && correct){
            if(sommeLigne(i) != sommeLigneChoisie(i)){
                correct = false;
            }
            i++;
        }
        i = 0 ;
        while (i < nbColonnes && correct){
            if(sommeColonne(i) != sommeColonneeChoisie(i)){
                correct = false;
            }
            i++;
        }
        return correct ;
    }


    private int sommeLigneChoisie(int ligne) {
        int somme = 0;
        for(int j = 0; j < getNbColonnes(); j++){
            if(cases[ligne][j].getChoisie()){
                somme+= getValeur(ligne, j);
            }
        }
        return somme;
    }

    private int sommeColonneeChoisie(int colonne) {
        int somme = 0;
        for(int i = 0; i < getNbLignes(); i++){
            if(cases[i][colonne].getChoisie()){
                somme+= getValeur(i, colonne);
            }

        }
        return somme;
    }


    public void reinitialiser() {
        FabriquePlateau fabriquePlateau = new FabriquePlateau(nomFichier);
        cases = fabriquePlateau.getPlateau(); // le plateau de depart
        this.nbVies = NB_VIES_INITIAL;
        resetCoordCaseAidee();
        coups.clear();
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

}

