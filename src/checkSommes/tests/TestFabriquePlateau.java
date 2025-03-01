package checkSommes.tests;

import checkSommes.modele.Case;
import checkSommes.modele.fabrique.FabriquePlateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFabriquePlateau {

    @BeforeEach
    void setUp(){

    }
    @Test
    public void testDimensions(){
        FabriquePlateau fabriquePlateau = FabriquePlateau.getInstance();
        Case[][] plateau = fabriquePlateau.getPlateauParDefaut();
        assertTrue( plateau.length == 4);
        assertTrue( plateau[0].length == 3);

    }

    @Test
    public void testGetPlateau() {
        FabriquePlateau fabriquePlateau = FabriquePlateau.getInstance();
        Case[][] plateau = fabriquePlateau.getPlateauParDefaut();

        assertTrue(plateau[0][0].getValeur() == 2 && plateau[0][0].estSolution());
        assertTrue(plateau[0][1].getValeur() == 5 && !plateau[0][1].estSolution());
        assertTrue(plateau[0][2].getValeur() == 3 && plateau[0][2].estSolution());

        assertTrue(plateau[1][0].getValeur() == 4 && !plateau[1][0].estSolution());
        assertTrue(plateau[1][1].getValeur() == 1 && plateau[1][1].estSolution());
        assertTrue(plateau[1][2].getValeur() == 6 && !plateau[1][2].estSolution());

        assertTrue(plateau[2][0].getValeur() == 7 && plateau[2][0].estSolution());
        assertTrue(plateau[2][1].getValeur() == 2 && !plateau[2][1].estSolution());
        assertTrue(plateau[2][2].getValeur() == 8 && plateau[2][2].estSolution());

        assertTrue(plateau[3][0].getValeur() == 3 && !plateau[3][0].estSolution());
        assertTrue(plateau[3][1].getValeur() == 4 && plateau[3][1].estSolution());
        assertTrue(plateau[3][2].getValeur() == 5 && !plateau[3][2].estSolution());
    }


}
