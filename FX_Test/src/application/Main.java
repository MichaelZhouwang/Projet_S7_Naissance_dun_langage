package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;


public class Main extends Application {
	private Circle redCircle;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			
            redCircle = new Circle(20);
            redCircle.getStyleClass().add("circle");
            redCircle.setLayoutX(20);
            redCircle.setLayoutY(20);

            Circle blueCircle = FXMLLoader.load(getClass().getResource("scene.fxml"));
            
            root.getChildren().add(redCircle);
            root.getChildren().add(blueCircle);
            
			Scene scene = new Scene(root, 400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
			primaryStage.setScene(scene);
			primaryStage.show();
			
			new Thread(new Pattern()).start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private class Pattern implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(30);
				}
				catch(Exception e) {
					
				}
	            redCircle.setLayoutX(redCircle.getLayoutX() + 1);
	            redCircle.setLayoutY(redCircle.getLayoutY() + 1);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
