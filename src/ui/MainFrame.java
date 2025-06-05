package src;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class MainFrame extends JFrame {

    private GraphiquePanel graphiquePanel;
    private ListPanel listPanel;
    private DetailsPanel detailsPanel;
    private ToolBar toolBar;
    private InfoBar infoBar;
    private ServiceNotification serviceNotification;
    private String currentSymbol;

    public MainFrame() {
        setTitle("Simulateur pour apprendre à Boursikoter");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        serviceNotification = new ServiceNotification(2.0, this);
        CompteBancaire compteBancaire = new CompteBancaire(new User("Utilisateur"), 100000, serviceNotification);
        Portefeuille defaultPtf = new Portefeuille(50000, serviceNotification, compteBancaire);
        compteBancaire.enregistrerPortefeuille(defaultPtf);

        graphiquePanel = new GraphiquePanel("Prix de Cloture");
        listPanel = new ListPanel(defaultPtf, compteBancaire);
        detailsPanel = new DetailsPanel();
        infoBar = new InfoBar();
        toolBar = new ToolBar(graphiquePanel, infoBar, this);

        add(toolBar, BorderLayout.NORTH);
        add(graphiquePanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.WEST);
        add(detailsPanel, BorderLayout.EAST);
        add(infoBar, BorderLayout.SOUTH);

        listPanel.getJList().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                ProduitFinancier p = listPanel.getJList().getSelectedValue();
                if (p != null) {
                    traiterRecherche(p.getSymbole());
                }
            }
        });

        graphiquePanel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("prixGraphe".equals(evt.getPropertyName()) && currentSymbol != null) {
                    updateDetailsForCurrentSymbol();
                }
            }
        });

        setVisible(true);
    }

    public JFrame getFrame() {
        return this;
    }

    public void traiterRecherche(String symbole) {
        currentSymbol = symbole;
        DonneAlphaVantage alpha = new DonneAlphaVantage(symbole);
        List<String[]> data = alpha.chercherDonne();
        if (data.isEmpty()) {
            infoBar.definirMessageErreur("Pas de donnée pour « " + symbole + " », veuillez entrez un symbole valide !");
            currentSymbol = null;
            return;
        }

        int n = data.size();
        double prixToday = Double.parseDouble(data.get(n - 1)[4]);
        double prixYesterday = Double.parseDouble(data.get(n - 2)[4]);
        String nom;
        try {
            nom = alpha.chercherNomEntreprise();
        } catch (Exception ex) {
            nom = symbole;
        }

        graphiquePanel.mettreAJourGraphique();
        graphiquePanel.setData(data);
        double prixGraphe = graphiquePanel.getPrixGraphe();
        detailsPanel.updateDetails(symbole, prixToday, prixGraphe, prixYesterday);
        infoBar.mettreAJourInfo("Données pour " + symbole);

        DefaultListModel<ProduitFinancier> model = listPanel.getModel();
        ActionGenerique action = new ActionGenerique(nom, symbole, prixToday);
        action.setPrixGraphe(prixGraphe);
        serviceNotification.surveillerPrix(action, prixYesterday, prixToday);

        boolean trouve = false;
        for (int i = 0; i < model.getSize(); i++) {
            ProduitFinancier p = model.getElementAt(i);
            if (p.getSymbole().equals(symbole)) {
                model.set(i, action);
                trouve = true;
                break;
            }
        }
        if (!trouve) {
            model.addElement(action);
        }
    }

    private void updateDetailsForCurrentSymbol() {
        if (currentSymbol == null) return;
        DonneAlphaVantage alpha = new DonneAlphaVantage(currentSymbol);
        List<String[]> data = alpha.chercherDonne();
        if (data.isEmpty()) return;
        int n = data.size();
        double prixToday = Double.parseDouble(data.get(n - 1)[4]);
        double prixYesterday = Double.parseDouble(data.get(n - 2)[4]);
        double prixGraphe = graphiquePanel.getPrixGraphe();
        detailsPanel.updateDetails(currentSymbol, prixToday, prixGraphe, prixYesterday);
        DefaultListModel<ProduitFinancier> model = listPanel.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            ProduitFinancier p = model.getElementAt(i);
            if (p.getSymbole().equals(currentSymbol)) {
                if (p instanceof ActionGenerique) {
                    ((ActionGenerique) p).setPrixGraphe(prixGraphe);
                }
                break;
            }
        }
    }
}
