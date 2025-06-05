package src;
import java.util.Scanner;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestUtilisateur {

        public static void afficherRegistredUser(List<User> liste) {
                assert(liste != null);
                if (liste.isEmpty()) {
                        System.out.println("Aucun utilisateur enregistré");
                }
                else {
                        for (User user : liste) {
                                if (user instanceof RegistredUser) {
                                        RegistredUser ru = (RegistredUser) user; 
                                        System.out.print("Pseudo : " + user.getPseudo());
                                        System.out.print(", Email : " + ru.getEmail());
                                        System.out.println(", Mdp : " + ru.getPassword());
                                }
                        }
                }
        }

        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                List<User> users = GestionnaireUtilisateur.chargerUtilisateurs();
                while (!scanner.nextLine().equals("exit")) {
                        System.out.println("Enregistrer/Supprimer");
                        String choix = scanner.nextLine();

                        System.out.println("Pseudo : ");
                        String nom = scanner.nextLine();
                        System.out.println("Email : ");
                        String mail = scanner.nextLine();
                        System.out.println("Mdp : ");
                        String mdp = scanner.nextLine();

                        User newUser = new RegistredUser(nom, mdp, mail);

                        if (choix.equals("supprimer") || choix.equals("Supprimer")) {
                               users.remove(newUser);
                        } else {
                               users.add(newUser);
                        }
                        afficherRegistredUser(users);
                }
                GestionnaireUtilisateur.sauvegarderUtilisateurs(users);
        }
}
