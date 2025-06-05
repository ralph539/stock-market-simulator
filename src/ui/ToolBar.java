package src;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.List;

public class ToolBar extends JToolBar {

    private GraphiquePanel graphique;
    private InfoBar infoBar;
    private JLabel utilisateurLabel; // JLabel pour afficher le nom de l'utilisateur
    private MainFrame mainframe;

    // Constructeur
    public ToolBar(GraphiquePanel graphique, InfoBar infoBar, MainFrame mainframe) {
        this.graphique = graphique;
        this.infoBar = infoBar;
        this.mainframe = mainframe;

        ajouterBoutton("Avancer", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphique.avancerDansLeTemps();
            }
        });
        
        // Ajouter les boutons d'actions standards
        ajouterBoutton("Bande de Bollinger", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphique.bandeBollinger(20);
            }
        });

        ajouterBoutton("Moyenne à 50", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graphique.moyenneMobile(50);
            }
        });

        //ajouterBoutton("MACD", new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
        //        graphique.indicateurMACD();
        //    }
        //});

        // Champ de recherche de l'action
        JTextField champRecherche = new JTextField(15);
        champRecherche.setText("Rechercher le produit financier souhaité");
        champRecherche.setForeground(Color.LIGHT_GRAY);
        champRecherche.setCaretPosition(0);

        champRecherche.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (champRecherche.getText().equals("Rechercher le produit financier souhaité")) {
                    champRecherche.setText("");
                    champRecherche.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (champRecherche.getText().isEmpty()) {
                    champRecherche.setText("Rechercher le produit financier souhaité");
                    champRecherche.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        JButton boutonRecherche = new JButton("Rechercher");

        boutonRecherche.addActionListener(e -> {
        String symbol = champRecherche.getText().toUpperCase().trim();
        if (!symbol.isEmpty()) {
            DonneAlphaVantage alpha = new DonneAlphaVantage(symbol);
            List<String[]> data = alpha.chercherDonne();
            if (data != null && !data.isEmpty()) {
                graphique.setData(data);
                infoBar.mettreAJourInfo("Données pour " + symbol);
            } else {
                infoBar.definirMessageErreur("Produit financier non trouvé. Veuillez entrer un symbole valide.");
            }
            mainframe.traiterRecherche(symbol);
        } else {
            infoBar.definirMessageErreur("Veuillez entrer un symbole d'action valide.");
        }
        });

        // Quand on appuie sur "Entrée" cela déclenche rechercher
        champRecherche.addActionListener(e -> boutonRecherche.doClick());


        add(champRecherche);
        add(boutonRecherche);

        // Ajouter les boutons d'inscription et de connexion
        ajouterBoutton("Inscription", new ActionInscription());
        ajouterBoutton("Se connecter", new ActionConnecter());

        // Initialiser le JLabel pour le nom de l'utilisateur
        utilisateurLabel = new JLabel("Bienvenue, invité");
        utilisateurLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(utilisateurLabel);

        // Ajouter un bouton pour supprimer le compte
        ajouterBoutton("Supprimer mon compte", new ActionSupprimerCompte());
    }

    private class ActionConnecter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame fenetreSign = new JFrame("Se connecter");
            fenetreSign.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fenetreSign.setSize(400, 300);
            Container contenu = fenetreSign.getContentPane();
            contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

            JTextField mailField = new JTextField("Email");
            JPasswordField passField = new JPasswordField("Mot de passe");
            JButton login = new JButton("Se connecter");

            contenu.add(mailField);
            contenu.add(Box.createVerticalStrut(10));
            contenu.add(passField);
            contenu.add(Box.createVerticalStrut(10));
            contenu.add(login);

            login.addActionListener(ev -> {
                String email = mailField.getText();
                String motDePasse = new String(passField.getPassword());

                // Connexion de l'utilisateur
                Utilisateur utilisateurConnecte = GestionnaireUtilisateur.connecterUtilisateur(email, motDePasse);

                if (utilisateurConnecte != null) {
                    JOptionPane.showMessageDialog(fenetreSign, "Connexion réussie !");

                    // Mettre à jour le nom de l'utilisateur dans la ToolBar
                    utilisateurLabel.setText("Bienvenue, " + utilisateurConnecte.getNom());

                    // Changer l'état de l'application
                    Portefeuille portefeuille = utilisateurConnecte.getPortefeuilles().get(0); // Si l'utilisateur a un portefeuille
                    VuePortefeuille vuePortefeuille = new VuePortefeuille(portefeuille);
                    contenu.add(vuePortefeuille);
                    fenetreSign.revalidate();
                    fenetreSign.repaint();
                } else {
                    JOptionPane.showMessageDialog(fenetreSign, "Email ou mot de passe incorrect !");
                }
            });

            login.setBackground(new Color(70, 130, 180));
            login.setForeground(Color.WHITE);
            login.setFocusPainted(false);

            fenetreSign.setLocationRelativeTo(null);
            fenetreSign.setVisible(true);
        }
    }

    private class ActionInscription implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame fenetreSign = new JFrame("Inscription");
            fenetreSign.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fenetreSign.setSize(400, 300);
            Container contenu = fenetreSign.getContentPane();
            contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));

            JTextField userField = new JTextField("Pseudo");
            JTextField mailField = new JTextField("Email");
            JPasswordField passField = new JPasswordField("Mot de passe");
            JButton login = new JButton("S'inscrire");

            contenu.add(userField);
            contenu.add(Box.createVerticalStrut(10));
            contenu.add(mailField);
            contenu.add(Box.createVerticalStrut(10));
            contenu.add(passField);
            contenu.add(Box.createVerticalStrut(10));
            contenu.add(login);

            login.addActionListener(ev -> {
                String pseudo = userField.getText();
                String email = mailField.getText();
                String motDePasse = new String(passField.getPassword());

                User nouvelUtilisateur = new RegistredUser(pseudo, motDePasse, email);
                GestionnaireUtilisateur.enregistrerUtilisateur(nouvelUtilisateur);
                JOptionPane.showMessageDialog(fenetreSign, "Inscription réussie !");
                fenetreSign.dispose();
            });

            login.setBackground(new Color(70, 130, 180));
            login.setForeground(Color.WHITE);
            login.setFocusPainted(false);

            fenetreSign.setLocationRelativeTo(null);
            fenetreSign.setVisible(true);
        }
    }

    private class ActionSupprimerCompte implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Demander une confirmation avant de supprimer le compte
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {
                // Supprimer l'utilisateur du stockage
                String email = utilisateurLabel.getText().replace("Bienvenue, ", "").trim(); // Récupérer l'email de l'utilisateur connecté
                UserStorage userStorage = new UserStorage();
                userStorage.deleteUserByEmail(email); // Utiliser la méthode de suppression par email

                // Déconnecter l'utilisateur et mettre à jour l'interface
                utilisateurLabel.setText("Bienvenue, invité");
                infoBar.mettreAJourInfo("Votre compte a été supprimé avec succès.");

                // Effectuer une déconnexion
                // Remettre à zéro les portefeuilles, ou vider la session, etc.
            }
        }
    }

    private void ajouterBoutton(String texte, ActionListener listener) {
        JButton boutton = new JButton(texte);
        boutton.addActionListener(listener);
        add(boutton);
    }
}
