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
    public void testSommeLigne(){
        Jeu jeu = new Jeu() ;
        assertTrue(jeu.sommeLigne(0) == 6);
        assertTrue(jeu.sommeLigne(1) == 5);
        assertTrue(jeu.sommeLigne(2) == 9);
        assertTrue(jeu.sommeLigne(3) == 7);

    }

    @Test
    public void testSommeColonne(){
        Jeu jeu = new Jeu() ;
        assertTrue(jeu.sommeColonne(0) == 2);
        assertTrue(jeu.sommeColonne(1) == 6);
        assertTrue(jeu.sommeColonne(2) == 2);
        assertTrue(jeu.sommeColonne(3) == 9);
        assertTrue(jeu.sommeColonne(4) == 8);

    }

    @Test
    public void testEstSolution(){
        Jeu jeu = new Jeu() ;
        assertFalse(jeu.estSolution(0,0));
        assertTrue(jeu.estSolution(2,0));
        assertTrue(jeu.estSolution(0,1));
        assertFalse(jeu.estSolution(3,2));


    }

    @Test
    public void testChoisirCase(){
        Jeu jeu = new Jeu() ; // mode oui par defaut
        assertFalse(jeu.getCouleur(0,0) == 2);
        jeu.choisirCase(0,0);
        assertTrue(jeu.getCouleur(0,0) == 0); // il a choisi mais c'est pas une case correcte.

        jeu.switchMode();
        jeu.choisirCase(0,0);
        assertTrue(jeu.getCouleur(0,0) == 1);

    }

    @Test
    public void testAider(){
        Jeu jeu = new Jeu() ; // mode oui par defaut
        jeu.choisirCase(0,0); // pas bon on perd une vie
        jeu.aider();
        assertTrue(jeu.getLigneCaseAidee() == 0 && jeu.getColonneCaseAidee() == 0);
        assertTrue(jeu.getCouleur(0,0) == 1); // l'aide
        assertTrue(jeu.getNbVies() == 3); // -(1+2)

        jeu.aider();
        assertTrue(jeu.getLigneCaseAidee() == 0 && jeu.getColonneCaseAidee() == 1);
        assertTrue(jeu.getCouleur(0,1) == 2);
        assertTrue(jeu.getNbVies() == 1);

    }

    @Test
    public void testIterator() {
        Jeu jeu = new Jeu();
        jeu.choisirCase(0,1); // le coup1
        jeu.aider(); // un autre coup apres aide.
        Iterator<Coup> it = jeu.iterator();
        assertTrue(it.hasNext());
        Coup coupRecup;
        coupRecup = it.next();
        assertTrue(coupRecup.getLigne() == 0 && coupRecup.getColonne() == 1 && !coupRecup.estAide());

        assertTrue(it.hasNext());
        coupRecup = it.next();
        assertTrue(coupRecup.getLigne() == 0 && coupRecup.getColonne() == 0 && coupRecup.estAide()); // le deuxiem coup cest (0,0) et c'est une aide.

        assertFalse(it.hasNext());
    }

}
