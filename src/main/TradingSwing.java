package src;
import javax.swing.*;

/** Cette classe initialise et affiche la fenêtre principale (mainFrame). */
public class TradingSwing {

    private MainFrame mainframe;
    
    /** Constructeur. */
     public TradingSwing() {

        MainFrame mainframe = new MainFrame();
    }

    /** Méthode principale. */
    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
        
       public void run() {
    	  new TradingSwing(); 
       }
       });
       
   }    
}
