package src;

public class Donnees {
    
    private double prixOuverture;
    private double prixCourant;
    private double prixMax;
    private double prixBas;
    private double prixCloture;
    private int volume;

    /** Constructeur */
    public Donnees(double prixOuverture,
                   double prixCourant,
                   double prixMax,
                   double prixBas,
                   double prixCloture,
                   int volume) {
        this.prixOuverture = prixOuverture;
        this.prixCourant = prixCourant;
        this.prixMax = prixMax;
        this.prixBas = prixBas;
        this.prixCloture = prixCloture;
        this.volume = volume;
    }

    public double getPrixOuverture() {
        return prixOuverture;
    }

    public double getPrixCourant() {
        return prixCourant;
    }

    public double getPrixMax() {
        return prixMax;
    }

    public double getPrixBas() {
        return prixBas;
    }

    public double getPrixCloture() {
        return prixCloture;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Donnees{" +
               "prix à l'ouverture : " + prixOuverture +
               ", prix courant : " + prixCourant +
               ", maximum atteint : " + prixMax + 
               ", minimum atteint : " + prixBas +
               ", prix à la fermeture : " + prixCloture +
               ", volume : " + volume + 
               "}";
    }
}
