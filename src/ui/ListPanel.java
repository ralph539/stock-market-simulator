package src;
import javax.swing.*;
import java.util.List; // Correct import
import java.awt.*;

public class ListPanel extends JPanel {
    private JList<ProduitFinancier> listProduitsFinanciers;
    private DefaultListModel<ProduitFinancier> model;

    public ListPanel(Portefeuille defaultPtf, CompteBancaire compteBancaire) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setPreferredSize(new Dimension(400, 0));
	
        model = new DefaultListModel<>();
        listProduitsFinanciers = new JList<>(model);
        add(new JScrollPane(listProduitsFinanciers));

        add(new VueCompteBancaire(compteBancaire));
        add(new ControleurCompteBancaire(compteBancaire, listProduitsFinanciers));
        add(new VuePortefeuille(defaultPtf));
        add(new ControlerPortefeuille(defaultPtf, listProduitsFinanciers));
    }

    public void updateListProduitsFinanciers(List<ProduitFinancier> produits) {
        model.clear();
        for (ProduitFinancier produit : produits) {
            model.addElement(produit);
        }
    }

    public DefaultListModel<ProduitFinancier> getModel() {
        return model;
    }

    public JList<ProduitFinancier> getJList() {
        return listProduitsFinanciers;
    }
}
