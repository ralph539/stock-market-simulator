package src;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau qui affiche les informations d’un produit financier sélectionné dans le ListPanel.
 * Les libellés sont maintenant empilés verticalement (un sous l’autre) et le panneau
 * est volontairement étroit pour laisser le maximum d’espace au graphique central.
 */
public class DetailsPanel extends JPanel {

    private final JLabel symboleLabel      = new JLabel();
    private final JLabel prixGrapheLabel   = new JLabel();
    private final JLabel prixLabel         = new JLabel();
    private final JLabel variationLabel    = new JLabel();

    /** Constructeur */
    public DetailsPanel() {

        /* 1. – disposition verticale */
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        /* 2. – largeur réduite pour ne pas rogner le graphe */
        setPreferredSize(new Dimension(200, 0));   // hauteur = 0 → s’étire verticalement

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        for (JLabel lbl : new JLabel[] {symboleLabel, prixGrapheLabel, prixLabel, variationLabel}) {
            lbl.setFont(labelFont);
            lbl.setAlignmentX(LEFT_ALIGNMENT);     // aligne le texte au bord gauche
            add(lbl);
            add(Box.createVerticalStrut(4));       // petit espacement entre les lignes
        }

        /* valeurs par défaut */
        symboleLabel     .setText("Symbole   :");
        prixGrapheLabel  .setText("Prix graphe :");
        prixLabel        .setText("Prix        :");
        variationLabel   .setText("Variation   :");
    }

    /**
     * Met à jour les informations affichées.
     *
     * @param symbol      symbole de l’action
     * @param prixToday   prix de clôture du jour
     * @param prixGraphe  dernier prix pointé sur le graphe
     * @param prixAvant   prix de clôture de la veille
     */
    public void updateDetails(String symbol,
                              double prixToday,
                              double prixGraphe,
                              double prixAvant) {

        symboleLabel.setText("Symbole   : " + symbol);
        prixGrapheLabel.setText(String.format("Prix graphe : %.2f", prixGraphe));
        prixLabel.setText(String.format("Prix        : %.2f", prixToday));

        double variation = ((prixToday - prixAvant) / prixAvant) * 100.0;
        variationLabel.setText(String.format("Variation   : %.2f%%", variation));
    }
}

