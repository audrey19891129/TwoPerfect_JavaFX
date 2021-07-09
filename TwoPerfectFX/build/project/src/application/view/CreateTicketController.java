package application.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import application.Main;
import application.controller.DescriptionController;
import application.controller.EmployeeController;
import application.controller.HttpService;
import application.model.Address;
import application.model.Client;
import application.model.ClientSearch;
import application.model.Description;
import application.model.Intervention;
import application.model.Ticket;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CreateTicketController {
	static Stage stage;
	private double xOffset = 0;
    private double yOffset = 0; 
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public ArrayList<String> AddressList;
    
	AlertController alert;
	
	@FXML 
	Button btn_Cancel;
	
	@FXML
	BorderPane bp_NewTicket;
	
	@FXML 
	Label lbl_ClientName;
	
	@FXML 
	TextField tb_CreationDate;
	
	@FXML 
	ComboBox cb_Description;
	
	@FXML 
	ComboBox cb_Address;
	
	
	@FXML
	public void initialize() {
		
		bp_NewTicket.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });	
		bp_NewTicket.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Main.stage.setX(event.getScreenX() - xOffset);
            	Main.stage.setY(event.getScreenY() - yOffset);
            }
        });	

		AddressList = new ArrayList<>();
		Client client = ClientSearch.getClient();
		lbl_ClientName.setText(client.getFirstname() + " " + client.getLastname());
		tb_CreationDate.setText(sdf.format(new Date()));
		initDescCombo();
		initAddressCombo();
	}
	
	
	
	@FXML
	public void initDescCombo() {
		List<Description> desc = DescriptionController.getDescriptions();
		ArrayList<String> list = DescriptionController.getDescriptionStrings(desc);
		cb_Description.setItems(FXCollections.observableArrayList(list));
		cb_Description.getSelectionModel().select(0);
	}
	
	@FXML
	public void initAddressCombo() {
		ArrayList<Address> addresses = (ArrayList<Address>) ClientSearch.getClient().getAddresses();
		for(Address a : addresses) {
			AddressList.add(a.toString());
		}
		cb_Address.setItems(FXCollections.observableArrayList(AddressList));
		cb_Address.getSelectionModel().select(0);
	}
	
	@FXML 
	public void cancel() {
		stage = (Stage) btn_Cancel.getScene().getWindow();
		stage.close();
		IndexController.main.setDisable(false);
	}
	
	@FXML 
	public void saveTicket() {
		ArrayList<Address> addresses = (ArrayList<Address>) ClientSearch.getClient().getAddresses();
		List<Description> descriptions = DescriptionController.getDescriptionsList();
		int addressIndex = cb_Address.getSelectionModel().getSelectedIndex();
		int id = 0;
		int addressId = addresses.get(addressIndex).getId();
		int clientId = ClientSearch.getClient().getId();
		String status = "incomplete";
		int descIndex = cb_Description.getSelectionModel().getSelectedIndex();
		int descriptionId = descriptions.get(descIndex).getId();
		String creationDate = tb_CreationDate.getText();
		String closingDate = "0000-00-00";
		
			Ticket ticket = new Ticket(id, addressId, clientId, status, descriptionId, creationDate, closingDate);
			Gson gson = new Gson();
			String json = gson.toJson(ticket);
			System.out.println(json);
			try {
				int response = HttpService.post("ticket", json);
				if(response >= 200 && response < 300) {
					IndexController.IndexStage.close();
					alert = new AlertController(AlertType.INFORMATION, "Ticket was successfuly created.");
					IndexController.main.setDisable(false);
				}
			} catch (IOException e) {
				alert = new AlertController(AlertType.ERROR, "Ticket could not be created because "+ e.getMessage());
				e.printStackTrace();
			}
	}
}
