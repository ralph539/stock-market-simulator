package src;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.BoxLayout;

public class VuePortefeuille extends JPanel {

    private Portefeuille modele;
    private JLabel solde;
    private DefaultTableModel tableModel;
    
    public VuePortefeuille(Portefeuille modele) {
        this.modele = modele;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.solde = new JLabel();

        this.add(solde);

        String[] colonnes = {"Nom", "Quantité", "Prix Achat Unitaire"};
        this.tableModel = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        
        maj();
        modele.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                System.out.println("Observer notifié: Mise à jour de VuePortefeuille");
                maj();
            }
        });
    }

    public void maj() {
        solde.setText("Solde : " + modele.getSolde() + "  ");
        System.out.println("Mise à jour du solde affichée: " + modele.getSolde());
        tableModel.setRowCount(0);
        for (ProduitFinancier key : modele.getPossessions().keySet()) {
            List<Transaction> liste = modele.getPossessions().get(key);
            for (Transaction t : liste) {
                tableModel.addRow(new Object[]{key.getNom(), t.getQuantite(), t.getPrixAchat()});
            }
        }
    }
}
