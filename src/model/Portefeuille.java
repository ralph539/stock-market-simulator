package src;
import java.util.*;

public class Portefeuille extends Observable {
    
    private double solde;
    private Map<ProduitFinancier, List<Transaction>> possessions;
    private ServiceNotification serviceNotification;
    private CompteBancaire compteBancaire;


    public Portefeuille(double newSolde, ServiceNotification serviceNotification, CompteBancaire compteBancaire) {
        this.solde = newSolde;
        this.possessions = new HashMap<>();
        this.serviceNotification = serviceNotification;
        this.compteBancaire = compteBancaire;
    }

    public Portefeuille(CompteBancaire compteBancaire) {
        this(50000, null, compteBancaire);
    }

    public double getSolde() {
        return solde;
    }

    public Map<ProduitFinancier, List<Transaction>> getPossessions() {
        return Collections.unmodifiableMap(possessions);
    }

    public void transfererVersCompteBancaire(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        if (montant > solde) {
            throw new IllegalArgumentException("Solde insuffisant dans le portefeuille.");
        }
        solde -= montant;
        compteBancaire.crediter(montant);
        System.out.println("Transfert de " + montant + " du portefeuille vers le compte bancaire.");
        setChanged();
        notifyObservers();
    }

    public void transfererDepuisCompteBancaire(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        
        if (montant > compteBancaire.getSolde()) {
            throw new IllegalArgumentException("Solde insuffisant dans le compte bancaire.");
        }
        solde += montant;
        this.compteBancaire.debiter(montant);
        System.out.println("Transfert de " + montant + " du compte bancaire vers le portefeuille.");
        setChanged();
        notifyObservers();
    }

    public int getQuantite(ProduitFinancier produit) {
        List<Transaction> liste = possessions.get(produit);
        int total = 0;
        if (liste == null) {
            return 0;
        }
        for (Transaction t : liste) {
            total += t.getQuantite();
        }
        return total;
    }

    public void acheter(ProduitFinancier produit, int quantite, double prix) {
        double montant = prix * quantite;
        if (solde < montant) {
            throw new IllegalArgumentException("Somme insuffisante sur le portefeuille.");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("Veuillez entrer un nombre positif pour la quantité souhaitée.");
        }

        Transaction trans = new Transaction(quantite, prix, new Date());
        possessions.computeIfAbsent(produit, k -> new ArrayList<>()).add(trans);

        System.out.println("Achat effectué: " + quantite + " unités de " + produit.getNom() + " à " + prix);
        debiter(montant);
        if (serviceNotification != null) {
            serviceNotification.notifierAchat(produit, quantite, prix);
        }
    }

    public void vendre(ProduitFinancier produit, int quantite, double prix) {
        double montant = prix * quantite;
        if (quantite <= 0) {
            throw new IllegalArgumentException("Veuillez entrer un nombre positif pour la quantité souhaitée.");
        }
        List<Transaction> liste = possessions.get(produit);
        int quantiteDisponible = getQuantite(produit);
        if (liste == null || quantiteDisponible < quantite) {
            throw new IllegalArgumentException("Quantité insuffisante pour ce produit sur le portefeuille.");
        }

        Iterator<Transaction> it = liste.iterator();
        while (it.hasNext() && quantite > 0) {
            Transaction t = it.next();
            int q = t.getQuantite();
            if (q <= quantite) {
                quantite -= q;
                it.remove();
            } else {
                t.setQuantite(q - quantite);
                quantite = 0;
            }
        }

        System.out.println("Vente effectuée: " + quantite + " unités de " + produit.getNom() + " à " + prix);
        crediter(montant);
        if (serviceNotification != null) {
            serviceNotification.notifierVente(produit, quantite, prix);
        }
    }

    public void crediter(double montant) {
        this.solde += montant;
        System.out.println("Crédit: " + montant + ", nouveau solde: " + solde);
        setChanged();
        notifyObservers();
    }

    public void debiter(double montant) {
        this.solde -= montant;
        System.out.println("Débit: " + montant + ", nouveau solde: " + solde);
        setChanged();
        notifyObservers();
    }
}
