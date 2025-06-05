package src;

/** représente l'action concrète de "Apple" */
public class ActionApple extends Action {
    
    /** Constructeur */
    public ActionApple() {
        super("Apple", "AAPL", 0.0);
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
