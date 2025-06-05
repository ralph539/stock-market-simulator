package src;

/** Représente une action passée par symbole, nom et cours dynamiques */
public class ActionGenerique extends Action {
    public ActionGenerique(String nom, String symbole, double prixCourant) {
        super(nom, symbole, prixCourant);
    }

    @Override
    public String toString() {
        return "{Nom : " + getNom() + ", Prix courant : " + getPrixCourant() + "}";
    }
}
