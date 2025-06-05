package src;

import javax.swing.*;
import java.awt.*;

public class InfoBar extends JLabel {

    // Constructeur
    public InfoBar() {
        super();
        setOpaque(true);
        setBackground(Color.WHITE);  // Définir le fond en blanc initialement
        setForeground(Color.BLACK);  // Définir la couleur du texte en noir
        setFont(new Font("Arial", Font.PLAIN, 14));  // Définir la police et la taille du texte
    }

    /**
     * Met à jour l'InfoBar avec des messages réguliers.
     * Cette méthode est utilisée pour afficher des informations normales.
     */
    public void mettreAJourInfo(String message) {
        setText(message);
        setForeground(Color.BLACK);  // Définir la couleur du texte en noir pour les messages réguliers
        setBackground(Color.WHITE);  // Définir le fond en blanc
        setFont(new Font("Arial", Font.PLAIN, 14));  // Définir la police et la taille du texte
        setBorder(null);  // Aucune bordure pour les messages réguliers
    }

    /**
     * Définit et stylise le message d'erreur
     */
    public void definirMessageErreur(String message) {
        setText(message);  // Définir le texte du message d'erreur
        setForeground(Color.RED);  // Définir la couleur du texte en rouge pour les erreurs
        setBackground(Color.YELLOW);  // Définir le fond en jaune pour une meilleure visibilité
        setFont(new Font("Arial", Font.BOLD, 14));  // Rendre le texte en gras
        setBorder(BorderFactory.createLineBorder(Color.RED, 2));  // Bordure rouge pour l'erreur
        setHorizontalAlignment(SwingConstants.CENTER);  // Centrer le texte
    }

    /**
     * Réinitialise le message à un état normal (utilisé pour effacer le message d'erreur)
     */
    public void reinitialiserMessage(String message) {
        setText(message);
        setForeground(Color.BLACK);  // Définir la couleur du texte en noir pour les messages réguliers
        setBackground(Color.WHITE);  // Définir le fond en blanc
        setFont(new Font("Arial", Font.PLAIN, 14));  // Définir la police et la taille du texte
        setBorder(null);  // Supprimer la bordure
    }
}
