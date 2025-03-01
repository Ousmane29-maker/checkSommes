package checkSommes.modele.fabrique;

import checkSommes.modele.Case;

import java.io.*;
import java.util.Scanner;

public class FabriquePlateau {
    private static FabriquePlateau instance = new FabriquePlateau() ;
    public static FabriquePlateau getInstance() { return instance; }

    private FabriquePlateau() {

    }
    public Case[][] getPlateauParDefaut() {
        Case[][] plateau = new Case[4][3];

        plateau[0][0] = new Case(2, true);
        plateau[0][1] = new Case(5, false);
        plateau[0][2] = new Case(3, true);

        plateau[1][0] = new Case(4, false);
        plateau[1][1] = new Case(1, true);
        plateau[1][2] = new Case(6, false);

        plateau[2][0] = new Case(7, true);
        plateau[2][1] = new Case(2, false);
        plateau[2][2] = new Case(8, true);

        plateau[3][0] = new Case(3, false);
        plateau[3][1] = new Case(4, true);
        plateau[3][2] = new Case(5, false);

        return plateau;
    }

    public Case[][] getPlateau(File nomFichier) throws FileNotFoundException, IllegalArgumentException{
        BufferedReader flot ;
        flot = new BufferedReader(new FileReader(nomFichier)) ;
        Scanner scanner = new Scanner(flot);
        int nbLignes ;
        int nbColonnes ;
        if (scanner.hasNextInt()){
            nbLignes = scanner.nextInt();
        }else {
            throw new IllegalArgumentException("Le nombre de lignes du fichier est mal formé.");
        }

        if (scanner.hasNextInt()){
            nbColonnes = scanner.nextInt();
        }else {
            throw new IllegalArgumentException("Le nombre de colonnes du fichier est mal formé.");
        }

        Case[][] plateau = new Case[nbLignes][nbColonnes] ;
        for (int i = 0; i < nbLignes; i++){
            for (int j = 0; j < nbColonnes; j++){
                if (scanner.hasNext()) {
                    plateau[i][j] = creerCase(scanner.next());
                } else {
                    throw new IllegalArgumentException("Les données du fichier sont insuffisantes.");
                }
            }
        }
        if(scanner.hasNext()){
            throw new IllegalArgumentException("Trop de données dans le fichier.");
        }

        scanner.close();
        return plateau ;

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


}
