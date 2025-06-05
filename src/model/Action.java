package src;

/** Cette classe représente une action en bourse */
public abstract class Action extends ProduitFinancier {
    
    /** Constructeur */
    //@ requires nom != null && prixCourant >= 0;
    //@ ensures this.nom = nom && this.prixCourant = prixCourant;
    public Action(String nom, String symbole, double prixCourant) {
        super(nom, symbole, prixCourant, Type.ACTION);
    }

}
