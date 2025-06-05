package src;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceNotification {
    private List<Notification> notifications;
    private double seuilVariation; // Seuil en pourcentage pour déclencher une alerte
    private JFrame parentFrame;    // Référence à la fenêtre principale pour les notifications GUI

    public ServiceNotification(double seuilVariation, JFrame parentFrame) {
        this.notifications = new ArrayList<>();
        this.seuilVariation = seuilVariation;
        this.parentFrame = parentFrame;
    }

    public void notifierAchat(ProduitFinancier produit, int quantite, double prix) {
        String message = "Achat de " + quantite + " unités de " + produit.getNom() + " à " + prix + "€";
        Notification notification = new Notification(message, "achat");
        notifications.add(notification);
        afficherNotification(notification);
    }

    public void notifierVente(ProduitFinancier produit, int quantite, double prix) {
        String message = "Vente de " + (quantite + 1) + " unités de " + produit.getNom() + " à " + prix + "€";
        Notification notification = new Notification(message, "vente");
        notifications.add(notification);
        afficherNotification(notification);
    }

    public void surveillerPrix(ProduitFinancier produit, double prixPrecedent, double prixActuel) {
        double variation = ((prixActuel - prixPrecedent) / prixPrecedent) * 100;
        if (Math.abs(variation) >= seuilVariation) {
            String type = variation > 0 ? "hausse" : "baisse";
            String message = "Alerte : " + type + " de " + String.format("%.2f", variation) + "% pour " + produit.getNom();
            Notification notification = new Notification(message, "alerte");
            notifications.add(notification);
            afficherNotification(notification);
        }
    }

    private void afficherNotification(Notification notification) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(parentFrame, notification.getMessage(), "Notification", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
