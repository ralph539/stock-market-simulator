package src;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class CompteBancaire extends Observable {
    
    private double solde;
    private User proprietaire;
    private ServiceNotification serviceNotification;
    private Banque banquePossesseur;
    private Map<Integer, Portefeuille> portefeuilles;
    private Map<Integer, CTO> ctos;
    private Map<Integer, PEA> peas;

    public CompteBancaire(User user, double solde, ServiceNotification serviceNotification) {
        this.proprietaire = user;
        this.solde = solde;
        this.serviceNotification = serviceNotification;
        this.portefeuilles = new HashMap<>();
        this.ctos = new HashMap<>();
        this.peas = new HashMap<>();
    }

    public CompteBancaire(User user) {
        this(user, 0, null);
    }

    public double getSolde() {
        return this.solde;
    }

    public void crediter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        solde += montant;
        setChanged();
        notifyObservers();
    }

    public void debiter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        double nouveauSolde = solde - montant;
        if (nouveauSolde < 0) {
            System.out.println("Vous êtes en découvert de " + Math.abs(nouveauSolde));
        }
        solde = nouveauSolde;
        setChanged();
        notifyObservers();
    }

    public <T> int enregistrer(Map<Integer, T> liste, T element) {
        int key_max = 0;
        for (int key : liste.keySet()) {
            if (key > key_max) {
                key_max = key;
            }
        }
        int newKey = key_max + 1;
        liste.put(newKey, element);
        return newKey;
    }

    public int enregistrerPortefeuille(Portefeuille ptf) {
        return enregistrer(portefeuilles, ptf);
    }

    public int enregistrerPortefeuille(double soldeDepart) {
        return enregistrer(portefeuilles, new Portefeuille(soldeDepart, serviceNotification, this));
    }

    public int enregistrerCTO() {
        return enregistrer(ctos, new CTO());
    }

    public int enregistrerPEA() {
        return enregistrer(peas, new PEA());
    }

    public void creerPortefeuille(JList<ProduitFinancier> listProduitsFinanciers) {
        JFrame fenetre = new JFrame("Portefeuille");
        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());

        Portefeuille ptf = new Portefeuille(0, serviceNotification, this);
        enregistrerPortefeuille(ptf);

        VuePortefeuille vue = new VuePortefeuille(ptf);
        fenetre.add(vue, BorderLayout.NORTH);

        ControlerPortefeuille controleur = new ControlerPortefeuille(ptf, listProduitsFinanciers);
        fenetre.add(controleur, BorderLayout.SOUTH);

        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }

    @Override
    public String toString() {
        return "CompteBancaire{solde=" + solde + "}";
    }
}
