package src;

/** représente l'action concrète de "Total Energies" */
public class ActionTotalEnergies extends Action {
    
    /** Constructeur */
    public ActionTotalEnergies() {
        super("Total Energies", "TTE", 0.0);
    }

    @Override
    public void setPrix(double nouveauPrix) {
        super.setPrix(nouveauPrix);
    }

    @Override
    public String toString() {
        return "Action{" +
               "Nom : " + getNom() +
               ", Prix courant : " + getPrixCourant() +
               "}";
    }
}
