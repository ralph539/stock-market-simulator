 package src;

/**
 * Le « compte-titres ordinaire » (CTO), également appelé « compte-titres » est 
 * un produit d’épargne sur le moyen-long terme. Il permet aux particuliers qui 
 * le souhaitent d’investir des capitaux sur les marchés financiers en acquérant 
 * des valeurs mobilières diverses (obligations, actions, fonds, bons, warrants, etc.).
 * Ce type de produit d’épargne s’adresse avant toute chose aux investisseurs qui 
 * cherchent à diversifier leurs placements sur plusieurs marchés mondiales.

 * Cette classe represente un compte CTO.
 */

public class CompteCTO extends CompteBancaire {
    private String description; // description du plan
    private double exonerations;  // exoneration de taxe

    /**
     * Constructeur d'un compte CTO.
     *
     * @param soldeInitial    le solde initial
     * @param plafond         somme maximale
     * @param exonerations    somme exonéeré de tax
     * @param description     description de ce compte
     */
    public CompteCTO(double soldeInitial, double plafond, double exonerations, String description) {
        super(soldeInitial, plafond, "CTO");
        this.description = description;
        this.exonerations = exonerations;
    }

    /**
     * Obtenir la description du compte.
     * @return la description 
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Obtenir les exonerations du compte.
     * @return les exoneration 
     */
    public double getExonerations() {
        return exonerations;
    }

    @Override
    public String toString() {
        return super.toString() +
               ", exonerations=" + exonerations +
               ", description='" + description + "'";
    }
}

