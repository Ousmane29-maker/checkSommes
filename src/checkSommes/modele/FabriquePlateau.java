package checkSommes.modele;

import java.io.*;
import java.util.Scanner;

public class FabriquePlateau {
    private  int nbLignes ;
    private int nbColonnes ;
    private String[][] donnees;
    public FabriquePlateau(String nomFichier){
        InputStream inputStream = getClass().getResourceAsStream("/txt/" + nomFichier);
        try{
            if (inputStream == null) {
                throw new FileNotFoundException("Le fichier " + nomFichier + " n'a pas pu être trouvé.");
            }
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNextInt()){
                nbLignes = scanner.nextInt();
            }else {
                throw new IllegalArgumentException("Erreur dans le fichier : le nombre de lignes est mal formé.");
            }

            if (scanner.hasNextInt()){
                nbColonnes = scanner.nextInt();
            }else {
                throw new IllegalArgumentException("Erreur dans le fichier : le nombre de colonnes est mal formé.");
            }
            donnees = new String[nbLignes][nbColonnes];
            for (int i = 0; i < nbLignes; i++){
                for (int j = 0; j < nbColonnes; j++){
                    if (scanner.hasNext()) {
                        donnees[i][j] = scanner.next();
                    } else {
                        throw new IllegalArgumentException("Erreur dans le fichier : données insuffisantes.");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public Case[][] getPlateau(){
        Case[][] lesCases = new Case[nbLignes][nbColonnes] ;
        for (int i = 0; i < nbLignes ; i++){
            for (int j = 0; j < nbColonnes; j++){
                lesCases[i][j] = creerCase(donnees[i][j]) ;
            }
        }
        
        return lesCases;
    }

    private Case creerCase(String s){
        assert(s.length() == 1 || s.length() == 2) : "Erreur, la taille de s doit etre 1 ou 2" ;
        Case newCase;
        if (s.length() == 1){
             newCase = new Case(Integer.parseInt(s), false);
        }else{
            newCase = new Case(s.charAt(1) - '0', true);
        }
        return  newCase;
    }
    public int getNbLignes(){
        return nbLignes;
    }

    public int getNbColonnes(){
        return nbColonnes;
    }

}
