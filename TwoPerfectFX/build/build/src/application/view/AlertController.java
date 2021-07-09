package application.view;

import javafx.scene.control.Alert;
//import javafx.stage.StageStyle;

public class AlertController extends Alert {	
	public AlertController(AlertType alertType, String message) {
		super(alertType);
		switch(alertType) {
			case ERROR : {
				setTitle("Server Response");
				setHeaderText("Error");
				//initStyle(StageStyle.UNDECORATED);
				setContentText(message);
				showAndWait();
				break;
			}
		case CONFIRMATION:
			setContentText(message);
			showAndWait();
			break;
		case INFORMATION:
			setContentText(message);
			showAndWait();
			break;
		case NONE:
			break;
		case WARNING:
			break;
		default:
			break;
		}
	}
}
