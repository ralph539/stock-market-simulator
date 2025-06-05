package src;
import java.util.List;
import java.util.ArrayList;

public class User {

        private String pseudo;
        //private List<Portefeuille> portefeuilles; // L'utilisateur peut avoir un ou plusieurs portefeuilles (PEA, CTO...)

        private CompteBancaire compte;
        
        public void setPseudo(String nom) {
                this.pseudo = nom;
        }

        public User() {
                this.pseudo = "user";
                //Portefeuille portefeuille = new Portefeuille();
                //this.portefeuilles = new ArrayList <Portefeuille>();
                //this.portefeuilles.add(portefeuille);
                this.compte = new CompteBancaire(this);

        }

         public User(String nom) {
                this.pseudo = nom;
                //Portefeuille portefeuille = new Portefeuille();
                //this.portefeuilles = new ArrayList <Portefeuille>();
                //this.portefeuilles.add(portefeuille);
                this.compte = new CompteBancaire(this);
        
        }

        public User(String nom, CompteBancaire compte) {
                this.pseudo = nom;
                this.compte = compte;
        }
        
        public String getPseudo() {
                return this.pseudo;
        }

}
