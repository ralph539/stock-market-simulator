package src;
import java.util.*;
import java.util.Date;

/**
 * Cette classe représente l'ensemble des transactions d'achats et de ventes sur un portefeuille.
 * On enregistre dans une transaction la quantité échangée, le prix échangé et date.
 */
public class Transaction {
    private int quantite; // quantité d'achat ou de vente
    private double prixAchat; // prix unitaire d'achat du produit financier
    private Date dateTransaction; // date de la transaction

    /**
     * Constructeur d'une nouvelle transaction .
     *
     * @param quantite la quantité achetée ou vendue 
     * @param prixAchat le prix unitaire échangé
     * @param dateTransaction la date de la transaction
     */
    public Transaction(int quantite, double prixAchat, Date dateTransaction) {
    	assert(quantite > 0);
    	assert(prixAchat > 0);
    	assert(dateTransaction != null);
        this.quantite = quantite;
        this.prixAchat = prixAchat;
        this.dateTransaction = new Date(dateTransaction.getTime());
    }

    /**
     * Obtenir la quantité échangée  .
     * @return quantite 
     */
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int nb) {
        this.quantite = nb;
    }

    /**
     * Obtenir le prix d'achat unitaire du produit échangée  .
     * @return prixAchat 
     */
    public double getPrixAchat() {
        return prixAchat;
    }

    /**
     * Obtenir la date de la transaction.
     * @return dateTransaction 
     */
    public Date getDateTransaction() {
        return new Date(dateTransaction.getTime());
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "quantite=" + quantite +
               ", prixAchat=" + prixAchat +
               ", date=" + dateTransaction +
               '}';
    }
}
