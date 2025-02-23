package checkSommes.modele;

public class Coup {
    private int ligne ;
    private int colonne;
    private int sommeLigne;
    private int sommeColonne;
    private boolean aide ;

    public Coup(int ligne, int colonne, int sommeLigne, int sommeColonne, boolean aide) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.sommeLigne = sommeLigne;
        this.sommeColonne = sommeColonne;
        this.aide = aide;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public int getSommeLigne() {
        return sommeLigne;
    }

    public int getSommeColonne() {
        return sommeColonne;
    }

    public boolean estAide() {
        return aide;
    }

    @Override
    public String toString() {
        return "<L"+ligne+", C"+colonne+"> / <"+sommeLigne+", "+sommeColonne+"> ";
    }
}
