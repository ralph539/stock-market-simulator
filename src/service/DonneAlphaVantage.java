package src;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DonneAlphaVantage {

    /** Clé API Alpha Vantage */
    private static final String CLE = "SRD992O312SDZ6LX";

    private String symbole;
    private double prix_Actuel;

    public DonneAlphaVantage(String symbole) {
        this.symbole = symbole;
        this.prix_Actuel = 0.0;
    }

    /**
     * Récupère les données journalières depuis fichier local ou API
     */
    public List<String[]> chercherDonne() {
        List<String[]> lignes = new ArrayList<>();
        String nomFichier = symbole + "_data.json";

        try {
            JSONObject racine;

            // Vérifier si un fichier local existe
            File fichier = new File(nomFichier);
            if (fichier.exists()) {
                String contenu = new String(Files.readAllBytes(Paths.get(nomFichier)));
                racine = new JSONObject(contenu);

                // Vérifie la date de rafraîchissement
                if (racine.has("refresh_date")) {
                    String date = racine.getString("refresh_date");
                    if (!LocalDate.parse(date).isEqual(LocalDate.now())) {
                        racine = telechargerEtSauverDonnees(symbole, nomFichier);
                    } else {
                        System.out.println("Données chargées depuis le fichier local.");
                    }
                } else {
                    racine = telechargerEtSauverDonnees(symbole, nomFichier);
                }
            } else {
                racine = telechargerEtSauverDonnees(symbole, nomFichier);
            }

            // Lire les données de la série temporelle
            JSONObject serie = racine.getJSONObject("Time Series (Daily)");
            List<String> dates = new ArrayList<>(serie.keySet());
            Collections.sort(dates);

            for (String d : dates) {
                JSONObject jour = serie.getJSONObject(d);
                lignes.add(new String[]{
                    d,
                    jour.getString("1. open"),
                    jour.getString("2. high"),
                    jour.getString("3. low"),
                    jour.getString("4. close"),
                    jour.getString("5. volume"),
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lignes;
    }

    /**
     * Télécharge les données depuis l'API et les sauvegarde localement
     */
    private static JSONObject telechargerEtSauverDonnees(String symbole, String nomFichier) throws Exception {
        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY" +
                "&symbol=" + symbole +
                "&apikey=" + CLE +
                "&outputsize=compact";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder reponse = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                reponse.append(inputLine);
            }
        }

        JSONObject racine = new JSONObject(reponse.toString());
        if (racine.has("Information") || racine.has("Note")) {
            throw new IOException("Erreur API : " + racine.toString());
        }

        // Ajouter une date de rafraîchissement
        racine.put("refresh_date", LocalDate.now().toString());

        // Sauvegarde dans un fichier local
        try (FileWriter fichier = new FileWriter(nomFichier)) {
            fichier.write(racine.toString(4)); // indentation 4 espaces
        }

        System.out.println("Données téléchargées et sauvegardées pour " + symbole);
        return racine;
    }


/**
 * Récupère le nom complet de l'entreprise via l'API OVERVIEW.
 * @return le champ "Name" du JSON, ou le symbole en cas d'absence d'information.
 * @throws IOException si l'appel HTTP échoue.
 */
public String chercherNomEntreprise() throws IOException {
    // Construire l'URL pour l'overview
    String urlString = "https://www.alphavantage.co/query?function=OVERVIEW"
                     + "&symbol=" + symbole
                     + "&apikey=" + CLE;
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    // Lire la réponse
    StringBuilder response = new StringBuilder();
    try (BufferedReader in = new BufferedReader(
             new InputStreamReader(connection.getInputStream()))) {
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
    }

    // Parser le JSON et extraire le nom
    JSONObject root = new JSONObject(response.toString());
    // optString renvoie le nom si présent, sinon le symbole par défaut
    return root.optString("Name", symbole);
}


}

