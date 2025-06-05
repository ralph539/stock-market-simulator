package src;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class Banque {
        
        private List<CompteBancaire> comptes;

        private static final String FICHIER = "comptes.json";
        private static final Gson gson = new Gson();

        //Sauvegarde les comptes dans le fichier json
        public void sauvegarderComptes() {
                try (FileWriter writer = new FileWriter(FICHIER)) {
                        gson.toJson(this.comptes, writer);
                } catch (IOException e) {
                        System.out.println("La sauvegarde des comptes a échoué.");
                }
        }

        //Charge les ComptesBancaires
        public void chargerComptes() {
                try (FileReader reader = new FileReader(FICHIER)) {
                        Type listType = new TypeToken<List<CompteBancaire>>() {}.getType();
                        this.comptes = gson.fromJson(reader, listType);
                        if (this.comptes == null) {
                                this.comptes = new ArrayList<>();
                        }
                } catch (IOException e) {
                        System.out.println("Le chargement des comptes a échoué.");
                        this.comptes = new ArrayList<>();
                }
        }

        //Enregistre un nouveau CompteBancaire
        public void enregistrerCompte(CompteBancaire compte) {
                chargerComptes();
                this.comptes.add(compte);
                sauvegarderComptes();
        }

        public Banque() {
                this.comptes = new ArrayList<>();
        }
}
