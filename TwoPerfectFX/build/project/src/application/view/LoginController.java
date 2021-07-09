package application.view;

import java.io.IOException;

import application.Main;
import application.controller.EmployeeController;
import application.model.Employee;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	static Stage IndexStage;
	private double xOffset = 0;
    private double yOffset = 0; 
	AlertController alert;
	public static Employee employee;
	
	@FXML
	AnchorPane root;
	
	@FXML
	Pane bg_login;
	
	@FXML
	TextField txtUsername;
	
	@FXML
	PasswordField txtPassword;
	
	@FXML
	Label lblVersion;
	
	@FXML
	public void initialize() {
		lblVersion.setText("1.24.742");
		txtUsername.setStyle("-fx-background-color : transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
		txtPassword.setStyle("-fx-background-color : transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
		
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });	
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Main.stage.setX(event.getScreenX() - xOffset);
            	Main.stage.setY(event.getScreenY() - yOffset);
            }
        });	
	}
	
	@FXML
	public void login(MouseEvent event) throws IOException {
		if(!txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
			employee = EmployeeController.login(txtUsername.getText(), txtPassword.getText());
			txtUsername.setStyle("-fx-background-color : transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
			txtPassword.setStyle("-fx-background-color : transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
			if(employee == null) {
				alert = new AlertController(AlertType.ERROR, EmployeeController.errorMessage);
			}else {
				if(employee.title.contentEquals("prepose")) {
				Main.stage.close();
				AnchorPane index = FXMLLoader.load(getClass().getResource("/application/view/Index.fxml"));
				Scene scene = new Scene(index);
				//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
				IndexStage = new Stage();
				IndexStage.initStyle(StageStyle.UNDECORATED);
				IndexStage.setScene(scene);
				IndexStage.show();
//				IndexStage.toBack();
				System.out.println("LOGIN SUCCESSFUL");
				txtUsername.setText("");
				txtPassword.setText("");
				txtUsername.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
				txtPassword.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
				}
				else {
					alert = new AlertController(AlertType.ERROR, "User not authorized.");
				}
			}
		}else { 
			alert = new AlertController(AlertType.ERROR, "You cannot have empty field"); 
			if(txtUsername.getText().isEmpty() && txtPassword.getText().isEmpty()){
				txtUsername.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 5 0; -fx-border-color : red;");
				txtPassword.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 5 0; -fx-border-color : red;");
				txtUsername.requestFocus();
			}else {
				if(txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
					txtUsername.requestFocus();
					txtUsername.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 5 0; -fx-border-color : red;");
					txtPassword.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
				} else{
					txtPassword.requestFocus();
					txtPassword.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 5 0; -fx-border-color : red;");
					txtUsername.setStyle("-fx-background-color: transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
				}
			}
		}
	}

	@FXML
	public void exit(MouseEvent event){
		System.out.println("Fermeture de l'application");
		System.exit(0);
	}
	
	@FXML
	public void cancel(MouseEvent event) {
		txtUsername.setText("");
		txtPassword.setText("");
		txtUsername.requestFocus();
		txtUsername.setStyle("-fx-background-color : transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
		txtPassword.setStyle("-fx-background-color : transparent; -fx-border-width : 0 0 1 0; -fx-border-color : black;");
	}

	public void minimize(MouseEvent event) {
		Main.stage.setIconified(true);
	}
}
