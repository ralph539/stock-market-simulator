package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gère le stockage et la suppression des utilisateurs
 */
public class UserStorage {
    
   
    private List<User> users;
    private List<RegistredUser> registredUsers;
    
    public UserStorage() {
        this.users = new ArrayList<>();
        this.registredUsers = new ArrayList<>();
    }
    
    /**
     * Ajoute un utilisateur simple à la liste de stockage
     * @param user L'utilisateur à ajouter
     * @return true si l'ajout a réussi
     */
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }
        
        for (User existingUser : users) {
            if (existingUser.getPseudo().equals(user.getPseudo())) {
                System.out.println("Un utilisateur avec ce pseudo existe déjà : " + user.getPseudo());
                return false;
            }
        }
        
        return users.add(user);
    }
    
    /**
     * Ajoute un utilisateur enregistré aux deux listes de stockage
     * @param user L'utilisateur enregistré à ajouter
     * @return true si l'ajout a réussi
     */
    public boolean addRegistredUser(RegistredUser user) {
        if (user == null) {
            return false;
        }
        
        for (User existingUser : users) {
            if (existingUser.getPseudo().equals(user.getPseudo())) {
                System.out.println("Un utilisateur avec ce pseudo existe déjà : " + user.getPseudo());
                return false;
            }
        }
        
        for (RegistredUser existingUser : registredUsers) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                System.out.println("Un utilisateur avec cet email existe déjà : " + user.getEmail());
                return false;
            }
        }
        
        // Ajouter aux deux listes
        users.add(user);
        return registredUsers.add(user);
    }
    
    /**
     * Supprime un utilisateur par référence directe
     * @param user L'utilisateur à supprimer
     * @return true si la suppression a réussi
     */
    public boolean deleteUser(User user) {
        if (user == null) {
            return false;
        }
        
        boolean success = false;
        
    
        if (user instanceof RegistredUser) {
            success = registredUsers.remove(user);
        }
        
        // Supprimer de la liste principale
        return users.remove(user) || success;
    }
    
    /**
     * Supprime un utilisateur par son pseudo
     * @param pseudo Le pseudo de l'utilisateur à supprimer
     * @return true si la suppression a réussi
     */
    public boolean deleteUserByPseudo(String pseudo) {
        if (pseudo == null || pseudo.isEmpty()) {
            return false;
        }
        
        RegistredUser registredToRemove = null;
        for (RegistredUser user : registredUsers) {
            if (user.getPseudo().equals(pseudo)) {
                registredToRemove = user;
                break;
            }
        }
        

        if (registredToRemove != null) {
            registredUsers.remove(registredToRemove);
        }
        
        User userToRemove = null;
        for (User user : users) {
            if (user.getPseudo().equals(pseudo)) {
                userToRemove = user;
                break;
            }
        }
        
 
        if (userToRemove != null) {
            return users.remove(userToRemove);
        }
        
        return false;
    }
    
    /**
     * Supprime un utilisateur enregistré par son email
     * @param email L'email de l'utilisateur à supprimer
     * @return true si la suppression a réussi
     */
    public boolean deleteUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
       
        RegistredUser userToRemove = null;
        for (RegistredUser user : registredUsers) {
            if (user.getEmail().equals(email)) {
                userToRemove = user;
                break;
            }
        }
        
  
        if (userToRemove != null) {
            registredUsers.remove(userToRemove);
            return users.remove(userToRemove);
        }
        
        return false;
    }
    
    /**
     * Récupère tous les utilisateurs stockés
     * @return Liste de tous les utilisateurs
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Retourne une copie pour éviter les modifications directes
    }
    
    /**
     * Récupère tous les utilisateurs enregistrés
     * @return Liste de tous les utilisateurs enregistrés
     */
    public List<RegistredUser> getAllRegistredUsers() {
        return new ArrayList<>(registredUsers); // Retourne une copie pour éviter les modifications directes
    }
    
    /**
     * Affiche tous les utilisateurs stockés
     */
    public void displayAllUsers() {
        System.out.println("Liste de tous les utilisateurs (" + users.size() + "):");
        for (User user : users) {
            System.out.println("- " + user.getPseudo());
        }
    }
    
    /**
     * Affiche tous les utilisateurs enregistrés
     */
    public void displayAllRegistredUsers() {
        System.out.println("Liste des utilisateurs enregistrés (" + registredUsers.size() + "):");
        for (RegistredUser user : registredUsers) {
            System.out.println("- " + user.getPseudo() + " (" + user.getEmail() + ")");
        }
    }
}
