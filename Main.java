package application;
	
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent view = FXMLLoader.load(getClass().getResource("/application/Mainscreen.fxml"));
		String  style= getClass().getResource("application.css").toExternalForm();
		view.getStylesheets().add(style);
		Image icon = new Image("f41043ba7dc8673047dd26242ca676df.png");
		primaryStage.getIcons().add(icon);
		primaryStage.setScene(new Scene(view));
		primaryStage.show();
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);

	}
}
