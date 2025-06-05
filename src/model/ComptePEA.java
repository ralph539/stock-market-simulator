 package src;

/**
 * Le Plan d'Epargne en Actions (PEA) est un compte boursier spécial qui permet 
 * aux investisseurs de bénéficier d'une enveloppe fiscale à l'imposition réduite 
 * par rapport aux comptes-titres ordinaires (CTO)
 * Contrairement aux comptes-titres, les gains réalisés sur un PEA ne sont pas 
 * soumis à l'impôt sur le revenu. Au lieu de cela, les dividendes et 
 * plus-values générés par les produits éligibles au PEA sont soumis à une taxe de 
 * cotisations sociales réduite à 17,2%, ce qui est nettement inférieur au t
 * aux d'imposition habituel de 30% appliqué aux comptes-titres.

 * Cette classe represente un compte PEA.
 */
  
public class ComptePEA extends CompteBancaire {
    private String description; // description du plan
    private double exonerations;  // exoneration de taxe

    /**
     * Constructeur d'un compte PEA.
     *
     * @param soldeInitial    le solde initial
     * @param plafond         somme maximale
     * @param exonerations    somme exonéeré de tax
     * @param description     description de ce compte
     */
    public ComptePEA(double soldeInitial, double plafond, double exonerations, String description) {
        super(soldeInitial, plafond, "PEA");
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
