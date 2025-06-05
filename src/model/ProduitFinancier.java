package src;

/** Cette classe représente un produit financier générique */
public abstract class ProduitFinancier {
    
    /** Nom du Produit Financier. */
    private String nom;
    
    /** Prix du produit. */
    private double prixCourant;

    /** Symbole du Produit Financier. */
    private String symbole;
    private double prixGraphe;

    public enum Type {
        ACTION, CRYPTO;
    }

    /**type du produit. */
    private Type typePF;

    /** Constructeur. */
    public ProduitFinancier(String nom, String symbole, double prixCourant, Type typePF) {
        this.nom = nom;
        this.prixCourant = prixCourant;
        this.symbole = symbole;
        this.typePF = typePF;
        this.prixGraphe = prixCourant;
    }

    public String getNom() {
        return nom;
    }

    public double getPrixCourant() {
        return prixCourant;
    }

    public String getSymbole() {
        return symbole;
    }
    
    public Type getType() {
        return typePF;
    }

    public double getPrixGraphe() {
        return prixGraphe;
    }

    //@ requires nouveauPrix >= 0;
    //@ ensures prixCourant = nouveauPrix;
    //public abstract void setPrix(double nouveauPrix);
    public void setPrix(double nouveauPrix) {
        this.prixCourant = nouveauPrix;
    }

    public void setPrixGraphe(double nouveauPrix) {
        this.prixGraphe = nouveauPrix;
    }

    @Override
    public String toString() {
        return nom + " ( " + symbole + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProduitFinancier that = (ProduitFinancier) obj;
        return symbole.equals(that.symbole);
    }

    @Override
    public int hashCode() {
        return symbole.hashCode();
    }
}
