package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;


public class Main extends Application {
	public static Stage stage; 
	
	@Override
	public void start(Stage LoginStage) {
		try {
			stage = LoginStage;
			AnchorPane root = FXMLLoader.load(getClass().getResource("/application/view/Login.fxml"));
			root.setBackground(Background.EMPTY);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/view/style.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			LoginStage.initStyle(StageStyle.TRANSPARENT);	
			LoginStage.setScene(scene);
			LoginStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}







