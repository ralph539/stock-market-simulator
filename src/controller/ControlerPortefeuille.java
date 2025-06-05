package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlerPortefeuille extends JPanel {
    private Portefeuille ptf;
    private JList<ProduitFinancier> listProduitsFinanciers;
    private JSpinner quantiteSpinner;
    private JCheckBox utiliserPrixGraphe;
    private JTextField montantTransfertField;

    public ControlerPortefeuille(Portefeuille ptf, JList<ProduitFinancier> listProduitsFinanciers) {
        this.ptf = ptf;
        this.listProduitsFinanciers = listProduitsFinanciers;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Quantité:"), gbc);

        gbc.gridy = 1;
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, null, 1);
        quantiteSpinner = new JSpinner(model);
        quantiteSpinner.setPreferredSize(new Dimension(100, 25));
        add(quantiteSpinner, gbc);

        gbc.gridy = 2;
        utiliserPrixGraphe = new JCheckBox("Utiliser prix graphe");
        add(utiliserPrixGraphe, gbc);

        gbc.gridy = 3;
        JButton acheter = new JButton("Acheter");
        acheter.setPreferredSize(new Dimension(100, 30));
        add(acheter, gbc);

        gbc.gridy = 4;
        JButton vendre = new JButton("Vendre");
        vendre.setPreferredSize(new Dimension(100, 30));
        add(vendre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(new JLabel("Montant transfert:"), gbc);

        gbc.gridy = 1;
        montantTransfertField = new JTextField(10);
        montantTransfertField.setPreferredSize(new Dimension(100, 25));
        add(montantTransfertField, gbc);

        gbc.gridy = 2;
        JButton transfererVersCompte = new JButton("Vers Compte");
        transfererVersCompte.setPreferredSize(new Dimension(100, 30));
        add(transfererVersCompte, gbc);

        gbc.gridy = 3;
        JButton transfererDepuisCompte = new JButton("Depuis Compte");
        transfererDepuisCompte.setPreferredSize(new Dimension(100, 30));
        add(transfererDepuisCompte, gbc);

        acheter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProduitFinancier prd = listProduitsFinanciers.getSelectedValue();
                if (prd != null) {
                    int quantite = (int) quantiteSpinner.getValue();
                    double prix = utiliserPrixGraphe.isSelected() ? prd.getPrixGraphe() : prd.getPrixCourant();
                    System.out.println("Achat demandé: " + quantite + " unités de " + prd.getNom());
                    try {
                        ptf.acheter(prd, quantite, prix);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit financier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        vendre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProduitFinancier prd = listProduitsFinanciers.getSelectedValue();
                if (prd != null) {
                    int quantite = (int) quantiteSpinner.getValue();
                    double prix = utiliserPrixGraphe.isSelected() ? prd.getPrixGraphe() : prd.getPrixCourant();
                    System.out.println("Vente demandée: " + quantite + " unités de " + prd.getNom());
                    try {
                        ptf.vendre(prd, quantite, prix);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit financier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        transfererVersCompte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double montant = Double.parseDouble(montantTransfertField.getText());
                    ptf.transfererVersCompteBancaire(montant);
                    montantTransfertField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un montant valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        transfererDepuisCompte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double montant = Double.parseDouble(montantTransfertField.getText());
                    ptf.transfererDepuisCompteBancaire(montant);
                    montantTransfertField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un montant valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
