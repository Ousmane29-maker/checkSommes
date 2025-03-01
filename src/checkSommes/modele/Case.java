package checkSommes.modele;

public class Case {
    private int valeur;
    private boolean estSolution;
    private boolean choisie;

    private int couleur;
    public Case(int valeur, boolean estSolution){
        this.valeur = valeur;
        this.estSolution  = estSolution;
        this.choisie = false;
        this.couleur = 0;
    }

    public int getValeur() {
        return valeur;
    }

    public boolean estSolution() {
        return estSolution;
    }

    public boolean getChoisie() {
        return choisie;
    }

    public void setChoisie(boolean b) {
        this.choisie = b;
    }

    public void setCouleur(int i) {
        this.couleur = i ;
    }

    public int getCouleur() {
        return this.couleur;
    }

    public void resetCase(){
        this.choisie = false;
        this.couleur = 0;
    }

    @Override
    public String toString() {
        return "Case{" +
                "valeur=" + valeur +
                ", estSolution=" + estSolution +
                ", choisie=" + choisie +
                ", couleur=" + couleur +
                '}';
    }
}
