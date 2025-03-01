package checkSommes.tests;

import checkSommes.modele.Coup;
import checkSommes.modele.Jeu;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJeu {

    @Test
    public void testEnModeOui(){
        Jeu jeu = new Jeu();
        assertTrue(jeu.enModeOui());
        jeu.switchMode();
        assertFalse(jeu.enModeOui());
        jeu.switchMode();
        assertTrue(jeu.enModeOui());
    }

    @Test
    public void testSommeLigne() {
        Jeu jeu = new Jeu();
        assertTrue(jeu.sommeLigne(0) == 5); // *2 + *3 = 5
        assertTrue(jeu.sommeLigne(1) == 1); // *1 = 1
        assertTrue(jeu.sommeLigne(2) == 15); // *7 + *8 = 15
        assertTrue(jeu.sommeLigne(3) == 4); // *4 = 4
    }

    @Test
    public void testSommeColonne() {
        Jeu jeu = new Jeu();
        assertTrue(jeu.sommeColonne(0) == 9);  // *2 + *7 = 9
        assertTrue(jeu.sommeColonne(1) == 5);  // *1 + *4 = 5
        assertTrue(jeu.sommeColonne(2) == 11); // *3 + *8 = 11
    }



    @Test
    public void testEstSolution() {
        Jeu jeu = new Jeu();

        assertTrue(jeu.estSolution(0, 0)); // *2 -> true
        assertFalse(jeu.estSolution(0, 1)); // 5 -> false
        assertTrue(jeu.estSolution(0, 2)); // *3 -> true

        assertFalse(jeu.estSolution(1, 0)); // 4 -> false
        assertTrue(jeu.estSolution(1, 1)); // *1 -> true
        assertFalse(jeu.estSolution(1, 2)); // 6 -> false

        assertFalse(jeu.estSolution(3, 0)); // 3 -> false
        assertTrue(jeu.estSolution(3, 1)); // *4 -> true
        assertFalse(jeu.estSolution(3, 2)); // 5 -> false
    }


    @Test
    public void testChoisirCase(){
        Jeu jeu = new Jeu() ; // mode oui par defaut
        assertFalse(jeu.getCouleur(0,0) == 2);
        jeu.choisirCase(0,0);
        assertTrue(jeu.getCouleur(0,0) == 2); // il a choisi une case correcte.

        jeu.switchMode();
        jeu.choisirCase(0,1);
        assertTrue(jeu.getCouleur(0,1) == 1);

    }

    @Test
    public void testAider(){
        Jeu jeu = new Jeu() ; // mode oui par defaut
        jeu.aider();
        assertTrue(jeu.getLigneCaseAidee() == 0 && jeu.getColonneCaseAidee() == 0);
        assertTrue(jeu.getCouleur(0,0) == 2); // l'aide
        assertTrue(jeu.getNbVies() == 4); //6 - 2

        jeu.aider();
        assertTrue(jeu.getLigneCaseAidee() == 0 && jeu.getColonneCaseAidee() == 1);
        assertTrue(jeu.getCouleur(0,1) == 1);
        assertTrue(jeu.getNbVies() == 2);

    }

    @Test
    public void testIterator() {
        Jeu jeu = new Jeu();
        jeu.choisirCase(0,0); // le coup1
        jeu.aider(); // un autre coup apres aide.
        Iterator<Coup> it = jeu.iterator();
        assertTrue(it.hasNext());
        Coup coupRecup;
        coupRecup = it.next();
        assertTrue(coupRecup.getLigne() == 0 && coupRecup.getColonne() == 0 && !coupRecup.estAide());

        assertTrue(it.hasNext());
        coupRecup = it.next();
        assertTrue(coupRecup.getLigne() == 0 && coupRecup.getColonne() == 1 && coupRecup.estAide()); // le deuxiem coup cest (0,1) et c'est une aide.

        assertFalse(it.hasNext());

    }

}
