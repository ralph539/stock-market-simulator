package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurCompteBancaire extends JPanel {
    
    private CompteBancaire cb;
    private JList<ProduitFinancier> listProduitsFinanciers;

    public ControleurCompteBancaire(CompteBancaire cb, JList<ProduitFinancier> listProduitsFinanciers) {
        this.cb = cb;
        this.listProduitsFinanciers = listProduitsFinanciers;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JButton creerPortefeuille = new JButton("Créer Portefeuille");

        add(creerPortefeuille);

        creerPortefeuille.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ControleurCompteBancaire.this.cb.creerPortefeuille(ControleurCompteBancaire.this.listProduitsFinanciers);
            }
        });
    }
}
