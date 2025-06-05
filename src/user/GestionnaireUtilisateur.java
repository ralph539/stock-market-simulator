package src;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class GestionnaireUtilisateur {

    private static final String FICHIER = "utilisateur.json";
    private static final Gson gson = new Gson();

    // Méthode pour connecter un utilisateur
    public static Utilisateur connecterUtilisateur(String email, String motDePasse) {
        List<User> utilisateurs = chargerUtilisateurs();

        for (User user : utilisateurs) {
            if (user instanceof RegistredUser) {
                RegistredUser registredUser = (RegistredUser) user;
                if (registredUser.getEmail().equals(email) && registredUser.getPassword().equals(motDePasse)) {
                    // Si l'email et le mot de passe correspondent, on connecte l'utilisateur
                    Utilisateur utilisateur = new Utilisateur(registredUser.getPseudo(), email, motDePasse);
                    utilisateur.connecter(); // Authentifier l'utilisateur
                    return utilisateur;
                }
            }
        }
        return null; // Retourne null si l'utilisateur n'est pas trouvé ou si les informations sont incorrectes
    }

    // Méthode pour sauvegarder les utilisateurs dans un fichier
    public static void sauvegarderUtilisateurs(List<User> utilisateurs) {
        try (FileWriter writer = new FileWriter(FICHIER)) {
            gson.toJson(utilisateurs, writer);
        } catch (IOException e) {
            System.out.println("La sauvegarde des utilisateurs a échoué.");
        }
    }

    // Méthode pour charger les utilisateurs depuis le fichier
    public static List<User> chargerUtilisateurs() {
        try (FileReader reader = new FileReader(FICHIER)) {
            Type listType = new TypeToken<List<RegistredUser>>() {}.getType();
            List<User> utilisateurs = gson.fromJson(reader, listType);
            return (utilisateurs != null ? utilisateurs : new ArrayList<User>());
        } catch (IOException e) {
            System.out.println("Le chargement des utilisateurs a échoué.");
            return new ArrayList<>();
        }
    }

    // Méthode pour enregistrer un nouvel utilisateur
    public static void enregistrerUtilisateur(User utilisateur) {
        List<User> utilisateurs = chargerUtilisateurs();
        utilisateurs.add(utilisateur);
        sauvegarderUtilisateurs(utilisateurs); // Sauvegarder la liste mise à jour
    }
}

