package application.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;

import application.Main;
import application.controller.HttpService;
import application.model.Client;
import application.model.ClientSearch;
import application.model.Description;
import application.model.Employee;
import application.model.Intervention;
import application.model.Ticket;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CreateInterventionController {
	static Stage stage;
	private double xOffset = 0;
    private double yOffset = 0; 
    private static BufferedReader br;
	private static String jsonContent;
	private static JSONTokener jsonToken;
	private static JSONArray jsonList;
	private static JSONObject obj;
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    AlertController alert;
    public static List<Employee> availableEmployees;
    public static Employee selectedEmployee;
    public Ticket ticket;
    public String day;
    public String date;
    public String startTime;
    public String endTime;
    
    @FXML 
    TextArea TA_Note;
    
    @FXML
    ComboBox cb_Communication;
    
    @FXML
	BorderPane bp_NewIntervention;
    
    @FXML
    AnchorPane AP_SelectTech;
    
    @FXML
    DatePicker dp_clientAvail;
    
    @FXML 
	Label lbl_ClientName;
    
    @FXML 
	Label lbl_Description;
    
    @FXML 
	ComboBox cb_EndTime;
    
    @FXML 
	ComboBox cb_StartTime;
    
    @FXML
    ComboBox cb_Technician;
    
    @FXML 
	Button btn_Cancel;
    
    @FXML 
	Button btn_Assign;
    
    @FXML Label lbl_num_ticket;
    
    @FXML
	public void initialize() {
    	ticket = IndexController.T;
    	lbl_num_ticket.setText(String.valueOf(ticket.getId()));
		
    	bp_NewIntervention.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });	
    	bp_NewIntervention.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Main.stage.setX(event.getScreenX() - xOffset);
            	Main.stage.setY(event.getScreenY() - yOffset);
            }
        });	
    	
    	Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();
                      //Removes dates before (including) today and past the next 3 months
                        setDisable(empty || item.compareTo(today) < 0 || item.isAfter(today.plusDays(90))); 
                    }
                };
            }
        };
        dp_clientAvail.setDayCellFactory(callB);
        
        
        initTimeCombos();
		Client client = ClientSearch.getClient();
		lbl_ClientName.setText(client.getFirstname() + " " + client.getLastname());
		lbl_Description.setText(ticket.getDescription().toString());
		hideSections();
		cb_Communication.getItems().add("SMS");
		cb_Communication.getItems().add("Phone");
    }
    
    @FXML
	public void initTimeCombos() {
		
		ArrayList<String> hours = new ArrayList<>();
		for(int i = 8; i<= 22; i++) {
			String time ="";
			if(i < 10) 
				time = "0" + i + ":00";
			else
				time = i + ":00";
			hours.add(time);
		}
		
		cb_EndTime.setItems(FXCollections.observableArrayList(hours));
		cb_EndTime.getSelectionModel().select(hours.size()-1);
		hours.remove(hours.size()-1);
		cb_StartTime.setItems(FXCollections.observableArrayList(hours));
		cb_StartTime.getSelectionModel().select(0);
		
	}
    
    @FXML 
	public void cancel() {
		stage = (Stage) btn_Cancel.getScene().getWindow();
		stage.close();
		IndexController.main.setDisable(false);
	}
    
    @FXML
	public void searchAvailableEmployees() throws ParseException {
    	
    	if(dp_clientAvail.getValue() != null) {
			date = dp_clientAvail.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String end = cb_EndTime.getValue().toString();
			String start = cb_StartTime.getValue().toString();
			
			if(end.compareToIgnoreCase(start) > 0) {
				startTime = cb_StartTime.getValue().toString() + ":00";
				endTime = cb_EndTime.getValue().toString() + ":00";
				LocalDate d = LocalDate.parse(date);
				DayOfWeek dow = d.getDayOfWeek();
				day = dow.toString().toLowerCase();
				List<Employee> employees = getAvailableEmployeesList(day, date, startTime, endTime);
				availableEmployees = employees;
				initTechCombo(employees);
				if(!employees.isEmpty()) {
					showSections();
				}
			}
			else
				alert = new AlertController(AlertType.ERROR, "End time can not be inferior or equal to start time.");
    	}
    	else
    		alert = new AlertController(AlertType.ERROR, "Date field can not be left empty.");
    }
    

    public void initTechCombo(List<Employee> employees) {
    	if(cb_Technician.getItems().size() > 0)
    		cb_Technician.getItems().clear();
    	if(employees == null || employees.isEmpty())
    		alert = new AlertController(AlertType.INFORMATION, "There is no available technician at the selected date and time interval.");
    	else {	
	    	for(Employee e: employees) {
	    		cb_Technician.getItems().add(e.getLastname() + " " + e.getFirstname());
	    	}
    	}
    }
    
    @FXML 
    public void hideSections() {
    	AP_SelectTech.setVisible(false);
		btn_Assign.setVisible(false);
    }
    
    public void showSections() {
    	AP_SelectTech.setVisible(true);
		btn_Assign.setVisible(true);
    }
    
    @FXML
    public void createIntervention() {
    	int comboIndex = cb_Technician.getSelectionModel().getSelectedIndex();
    	selectedEmployee = availableEmployees.get(comboIndex);
    	String note = TA_Note.getText();
    	if(note.length()>300) {
    		alert = new AlertController(AlertType.ERROR, "The note can not exceed 300 characters (spaces included).");
    	}
    	else {
    		
    		if(cb_Communication.getSelectionModel().isEmpty()) {
    			alert = new AlertController(AlertType.ERROR, "You must choose a prefered way to contact client.");
    		}
    		else {
    			
		    	String mode = "null";
		    	if(cb_Communication.getSelectionModel().getSelectedItem() == "SMS")
		    		mode = "sms";
		    	else if(cb_Communication.getSelectionModel().getSelectedItem() == "Phone")
		    		mode = "phone";
		    		
		    	Intervention intervention = new Intervention(0, ticket.id, selectedEmployee.getId(), date, startTime, endTime, "00:00:00", "00:00:00", note, "Assignée", "aucun commentaire", mode);
		    	Gson gson = new Gson();
				String json = gson.toJson(intervention);
				System.out.println(json);
				try {
					int response = HttpService.post("intervention", json);
					if(response >= 200 && response < 300) {
						IndexController.IndexStage.close();
						alert = new AlertController(AlertType.INFORMATION, "Intervention was successfuly created.");
						IndexController.main.setDisable(false);
					}
					else {
						alert = new AlertController(AlertType.ERROR, "Intervention could not be created.");
					}
				} catch (IOException e) {
					alert = new AlertController(AlertType.ERROR, "Intervention could not be created because "+ e.getMessage());
					e.printStackTrace();
				}
    		}
    	}
    }
    
    
    public static List<Employee> getAvailableEmployeesList(String day, String date, String startTime, String endTime){
    	System.out.println(day + " " + date + " " + startTime + " " + endTime);
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet("https://twoperfect.herokuapp.com/twoperfect/services/empSearch/" + day + "/" + date + "/" + startTime + "/" + endTime);
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				ObjectMapper objectMapper = new ObjectMapper();
				List<Employee> employees = objectMapper.readValue(jsonContent, new TypeReference<List<Employee>>() {});
				System.out.println("LIST OF EMPLOYEES : " + employees);
				return employees;
			}
		} catch (IOException | JSONException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
    
}
