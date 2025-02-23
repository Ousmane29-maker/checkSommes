package checkSommes.tests;

import checkSommes.modele.Case;
import checkSommes.modele.FabriquePlateau;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFabriquePlateau {
    private FabriquePlateau fabriquePlateau;

    @BeforeEach
    void setUp() {
        fabriquePlateau = new FabriquePlateau("jeu1.txt"); // Initialise avant chaque test
    }
    @Test
    public void testDimensions(){
        assertTrue( fabriquePlateau.getNbLignes() == 4);
        assertTrue( fabriquePlateau.getNbColonnes() == 5);

    }

    @Test
    public void testGetPlateau(){
        Case[][] plateau = fabriquePlateau.getPlateau();
        assertTrue(plateau[0][0].getValeur() == 1 && !plateau[0][0].estSolution());
        assertTrue( plateau[0][1].getValeur() == 2 && plateau[0][1].estSolution());
        assertTrue( plateau[0][2].getValeur() == 1 && !plateau[0][2].estSolution());
        assertTrue( plateau[0][3].getValeur() == 2 && plateau[0][3].estSolution());
        assertTrue( plateau[0][4].getValeur() == 2 && plateau[0][4].estSolution());

        assertTrue( plateau[3][0].getValeur() == 3 && !plateau[3][0].estSolution());
        assertTrue( plateau[3][1].getValeur() == 1 && plateau[3][1].estSolution());
        assertTrue( plateau[3][2].getValeur() == 5 && !plateau[3][2].estSolution());
        assertTrue( plateau[3][3].getValeur() == 1 && !plateau[3][3].estSolution());
        assertTrue( plateau[3][4].getValeur() == 6 && plateau[3][4].estSolution());




    }


}
