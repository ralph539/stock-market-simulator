package src;


import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.json.JSONObject;
// Lire les données de la réponse HTTP :
import java.io.BufferedReader;
import java.io.InputStreamReader;
// Gérer la connection HTTP :
import java.net.HttpURLConnection;
// Gérer les URL :
import java.net.URL;
// Gérer les dates :
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Cette classe permet de récupérer des données depuis l'API */
public class Api {
	
	private final String cle_Api; //la cle API
   	private final String symbole;  //le symbole des données que l'on veut importer
    private double prix_Actuel; // le prix actuel du produit financier

	public Api (String cle_Api, String symbole) {
		this.cle_Api = cle_Api;
		this.symbole = symbole;
		this.prix_Actuel = 0; //initialisation par défaut
	}
	
	
    public List<String[]> get_Donnees() {
    	List<String[]> donnees = new ArrayList<>();
       	try {
        	String function = "TIME_SERIES_DAILY";
            String outputSize = "full";
	    	
	    	// Constuire l'url sous forme de chaine de caractères :
            String urlString = String.format(
                    "https://www.alphavantage.co/query?function=%s&symbol=%s&apikey=%s&outputsize=%s",
                    function, this.symbole, this.cle_Api, outputSize
            );
			
			// Créer un objet URL à partir de la chaine de caractères :
            URL url = new URL(urlString);
            
            // Ouvrir une connection HTTP à l'URL spécifiée :
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
			
			// Lire la réponse de la connection HTTP :
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            // variable qui stocke chaque ligne de la réponse
            String inputLine;
            
            // variable qui accumule les lignes de texte
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);    // Ajouter chaque ligne lue au StringBuffer
            }
            in.close();   // Fermer le BufferedReader après avoir terminé la lecture
			
			// Analyser les donnees JSON :
            JSONObject json = new JSONObject(response.toString());
            JSONObject dictionnaireDonnees  = json.getJSONObject("Time Series (Daily)");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<String[]> donnees_triees = new ArrayList<>();

            for (String date : dictionnaireDonnees.keySet()) {
                LocalDate dateCourante = LocalDate.parse(date, formatter);
                JSONObject donneesCourantes = dictionnaireDonnees.getJSONObject(date);
                String[] tableauDonnees = {
                        date, 
                        donneesCourantes.getString("1. open"),
                        donneesCourantes.getString("2. high"),
                        donneesCourantes.getString("3. low"),
                        donneesCourantes.getString("4. close"),
                        donneesCourantes.getString("5. volume"),
                    };
                donnees_triees.add(tableauDonnees);
            }

            // Trier les données par date (la plus récente en premier)
            Collections.sort(donnees_triees, (a, b) -> {
                LocalDate dateA = LocalDate.parse(a[0], formatter);
                LocalDate dateB = LocalDate.parse(b[0], formatter);
                return dateB.compareTo(dateA);
            });

            donnees.addAll(donnees_triees); // Ajouter les données triées à la liste à retourner

            // Récupérer le prix actuel (le prix de fermeture du premier élément après le tri)
            if (!donnees_triees.isEmpty()) {
                this.prix_Actuel = Double.parseDouble(donnees_triees.get(0)[4]);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération des données pour le symbole : " + this.symbole);
        }
        return donnees;
    }

    public String getSymbol() {
        return this.symbole;
    }

    public double getPrixActuel() {
        return this.prix_Actuel;
    }

}
		
