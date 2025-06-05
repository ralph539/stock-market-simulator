package src;
import java.util.List;
import java.util.ArrayList;

public class Utilisateur {
    private String nom;
    private String email;
    private String motDePasse;
    private boolean estAuthentifie;
    private List<Portefeuille> portefeuilles;

    public Utilisateur(String nom, String email, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.estAuthentifie = false;
        this.portefeuilles = new ArrayList<>();
    }

    public String getNom() {
        return this.nom;
    }

    public String getEmail() {
        return this.email;
    }

    protected String getMotDePasse() {
        return this.motDePasse;
    }

    public boolean getEstAuthentifie() {
        return this.estAuthentifie;
    }

    public void setNom(String nouveauNom) {
        this.nom = nouveauNom;
    }

    public void setEmail(String nouveauEmail) {
        this.email = nouveauEmail;
    }

    public void setMotDePasse(String nouveauMotDePasse) {
        this.motDePasse = nouveauMotDePasse;
    }

    public void setEstAuthentifie(boolean nouveauEstAuthentifie) {
        this.estAuthentifie = nouveauEstAuthentifie;
    }

    public List<Portefeuille> getPortefeuilles() {
        return portefeuilles;
    }

    public void deconnecter() {
        this.estAuthentifie = false;
    }

    public boolean connecter() {
        this.estAuthentifie = true;
        return true;
    }

    public void ajouterPortefeuille(Portefeuille nouveauPortefeuille) {
        this.portefeuilles.add(nouveauPortefeuille);
    }
}

