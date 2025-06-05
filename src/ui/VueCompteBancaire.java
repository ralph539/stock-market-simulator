package src;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.BoxLayout;

public class VueCompteBancaire extends JPanel {

    private CompteBancaire modele;
    private JLabel solde;
    
    public VueCompteBancaire(CompteBancaire modele) {
        this.modele = modele;
	setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.solde = new JLabel();
	setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.add(solde);

        maj();
        modele.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                System.out.println("Observer notifié: Mise à jour de VueCompteBancaire");
                maj();
            }
        });
    }

    public void maj() {
        solde.setText("Solde Compte Bancaire : " + modele.getSolde() + "  ");
        System.out.println("Mise à jour du solde affichée: " + modele.getSolde());
    }

}
