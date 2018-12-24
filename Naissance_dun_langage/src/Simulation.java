
import java.io.IOException;
import java.util.ArrayList;

import ihm.Controleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import systeme.Individu;
import systeme.Systeme;

public class Simulation extends Application
{
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage)
	{
		Systeme.routine();
		
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene.fxml"));   

        BorderPane root = null;
		
        try {
			root = (BorderPane)loader.load();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("scene.css").toExternalForm());
		stage.setScene(scene);
		
		Controleur controleur = (Controleur)loader.getController();
		controleur.lancerListeners();
		
		stage.setTitle("Application");
		stage.show();
	}
}