
import java.io.IOException;

import ihm.Controleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lecture.ConfigurationSysteme;
import lecture.LecteurConfigurationSysteme;
import systeme.Systeme;

/**
 * Classe principale de l'application
 * 
 * Elle recupere les differentes ressources du projet, configure le systeme,
 * puis lance la simulation et affiche les resultats dans une IHM
 * 
 * @author Charles MECHERIKI & Yongda LIN
 *
 */
public class Simulation extends Application {
	
	private static final String urlConfig = "config/config.xml";
	private static final String urlFXLM = "scene.fxml";
	private static final String urlCSS = "scene.css";
	private static final String urlIcon = "java-icon.png";
	private static final String titreApp = "Naissance d'un langage";

	/**
	 * Methode main habituelle, pouvant prendre des arguments en parametre
	 * Actuellement, aucun argument n'est considere
	 * 
	 * @param args	les arguments pour l'application
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			LecteurConfigurationSysteme lecteurConfigurationSysteme = new LecteurConfigurationSysteme();
			ConfigurationSysteme configurationSysteme = lecteurConfigurationSysteme.lireConfigSystemeDepuisFichier(urlConfig);
			Systeme.configurer(configurationSysteme);
			Systeme.lancer();
			
			// Lancement de l'IHM
		
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource(urlFXLM));   
	
	        BorderPane root = null;
	        try {
				root = (BorderPane)loader.load();
			}
			catch (IOException exception) {
				exception.printStackTrace();
			}
	
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(urlCSS).toExternalForm());
			stage.setScene(scene);
			
			Controleur controleur = (Controleur)loader.getController();
			controleur.lancerListeners();

			stage.getIcons().add(new Image(urlIcon));
			stage.setTitle(titreApp);
			stage.show();
		}
		catch (Exception exception) {
			System.out.println(exception.getMessage());
			System.exit(1);
		}
	}
}