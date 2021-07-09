package application.view;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import application.Main;
import application.controller.ClientController;
import application.controller.EmployeeController;
import application.controller.HttpService;
import application.controller.InterventionController;
import application.controller.TicketController;
import application.model.Address;
import application.model.Client;
import application.model.ClientSearch;
import application.model.Coordinates;
import application.model.Description;
import application.model.Employee;
import application.model.Entry;
import application.model.Intervention;
import application.model.Product;
import application.model.Ticket;
import application.model.Package;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.regexp.joni.Option;

public class IndexController {
	public static AnchorPane main;
	private double xOffset = 0;
    private double yOffset = 0; 
    public static int clickCount = 0;
	static AlertController alert;
	public static Client client;
	public static Ticket T;
	public static ToggleGroup group;
	ObservableList<String> list = FXCollections.observableArrayList("Email", "Phone");
	static Stage IndexStage;
	private List<Employee> cbo_Technicians;
	private Employee technician;
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public Timeline timeline = new Timeline();
	public static HashMap<Integer, String> months;
	public static String selected = "#00b4d8;";
	public static String not_selected = "transparent;";
	public static ObservableList<Intervention> listInterventions;
	public static int month;
	
	@FXML ScrollPane SP_Calendar;
	@FXML BorderPane BP_Maps;
	@FXML AnchorPane TP_Technician;
	@FXML TableView TV_TechTasksToday;
	@FXML Label lbl_techId;
	@FXML Label lbl_techName;
	@FXML Label lbl_techPhone;
	@FXML Label lbl_techEmail;
	@FXML Label lbl_techStatus;
	@FXML ComboBox cb_technicians;
	@FXML SplitPane SP_ClientInfos;
	@FXML ScrollPane SP_Packages;
	@FXML AnchorPane AP_Index;
	@FXML AnchorPane AP_Tickets;
	@FXML ImageView imgBackground;
	@FXML TableView TV_Addresses;
	@FXML Accordion ACC_Packages;
	@FXML Accordion ACC_Tickets;
	@FXML BorderPane SearchClientView;
	@FXML BorderPane AP_Live;
	@FXML TabPane TP_Client;
	@FXML Tab tabInfos;
	@FXML Tab tabTickets;
	@FXML Pane root;
	@FXML Label lblEmployee;
	@FXML Button btnSearchClient;
	@FXML Button btn_SearchTech;
	@FXML Button btn_Tickets;
	@FXML Button btnLive;
	@FXML Button btnCreateTicket;
	@FXML ComboBox<String> cmbSearchBy;
	@FXML TextField txtEmail;
	@FXML TextField txtPhone1;
	@FXML TextField txtPhone2;
	@FXML TextField txtPhone3;
	@FXML Label lblClientId;
	@FXML Label lblClientName;
	@FXML Label lblClientPhone;
	@FXML Label lblClientEmail;
	@FXML Label lblClientStatus;
	@FXML AnchorPane AP_Technician;
	@FXML SplitPane TP_Technician_Details;
	@FXML ProgressIndicator progress_loading;
	@FXML BorderPane front_main_index;
	@FXML Label lbl_num_ticket;
	@FXML ImageView IV_TechPicture;
	@FXML HBox hbox_display_btn_live_track_tech;
	@FXML TableView tableTechnicianLive;
	@FXML RadioButton rb_Complete;
	@FXML RadioButton rb_Incomplete;
	@FXML RadioButton rb_All;
	@FXML TableView TV_Tickets_Search;
	@FXML TableView TV_Intervention_By_Ticket;
	@FXML ComboBox cb_Months;
	@FXML ComboBox cb_Years;
	
	// INIT [ EXECUTE ] before Application Run on screen
	@FXML
	public void initialize() {
		progress_loading.setVisible(false);
		SearchClientView.setVisible(false);
		AP_Technician.setVisible(false);
		AP_Tickets.setVisible(false);
		cmbSearchBy.setItems(list);
		SP_Packages.setHbarPolicy(ScrollBarPolicy.NEVER);
		SP_Packages.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		cmbSearchBy.getSelectionModel().selectFirst();
		cmbSearchBy.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) -> {
			switch(newValue) {
			case "Email" :
				txtEmail.setVisible(true);
				txtPhone1.setVisible(false);
				txtPhone2.setVisible(false);
				txtPhone3.setVisible(false);
				break;
			case "Phone" :
				txtEmail.setVisible(false);
				txtPhone1.setVisible(true);
				txtPhone2.setVisible(true);
				txtPhone3.setVisible(true);
				break;
			}
		});
		
		txtPhone1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("\\d*")) {
					txtPhone1.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if(txtPhone1.getText().length() >= 3) {
					txtPhone2.requestFocus();
					String phone = txtPhone1.getText().substring(0, 3);
					txtPhone1.setText(phone);
				}
			}
		});
		
		txtPhone2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("\\d*")) {
					txtPhone2.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if(txtPhone2.getText().length() >= 3) {
					txtPhone3.requestFocus();
					String phone = txtPhone2.getText().substring(0, 3);
					txtPhone2.setText(phone);
				}
			}
		});
		
		txtPhone3.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("\\d*")) {
					txtPhone3.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if(txtPhone3.getText().length() > 4) {
					String phone = txtPhone3.getText().substring(0, 4);
					txtPhone3.setText(phone);
				}
			}
			
		});
		
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
            	LoginController.IndexStage.setX(event.getScreenX() - xOffset);
            	LoginController.IndexStage.setY(event.getScreenY() - yOffset);
            }
        });
		
		if(LoginController.employee == null) { lblEmployee.setText("TESTING MODE"); }
		else { lblEmployee.setText("Welcome " + LoginController.employee.getFirstname() + " " + LoginController.employee.getLastname()); }
		main = AP_Index;
		
		group = new ToggleGroup();
		rb_Complete.setToggleGroup(group);
		rb_Incomplete.setToggleGroup(group);
		rb_All.setToggleGroup(group);
		rb_Complete.setSelected(true);
		group.selectedToggleProperty().addListener(v -> {	
			RadioButton selected = (RadioButton) group.getSelectedToggle();
			if(selected.getText().equalsIgnoreCase("All")) {
				cb_Months.setDisable(true);
				cb_Years.setDisable(true);
			}else {
				cb_Months.setDisable(false);
				cb_Years.setDisable(false);
			}
		});
			
		
		months = new HashMap<Integer, String>();
		months.put(0, "All");
		months.put(1, "January");
		months.put(2, "February");
		months.put(3, "March");
		months.put(4, "April");
		months.put(5, "May");
		months.put(6, "June");
		months.put(7, "July");
		months.put(8, "August");
		months.put(9, "September");
		months.put(10, "October");
		months.put(11, "November");
		months.put(12, "December");
		
		ObservableList<String> list_years = FXCollections.observableArrayList("2021" ,"2020", "2019", "2018");
		ObservableList<String> list_months = FXCollections.observableArrayList(months.values());
		cb_Months.setItems(list_months);
		cb_Months.getSelectionModel().selectFirst();
		cb_Years.setItems(list_years);
		cb_Years.getSelectionModel().selectFirst();
		
//		cb_Months.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) -> {
//			for(java.util.Map.Entry<Integer, String> month : months.entrySet()) {
//				if(month.getValue() == newValue) {
//					System.out.println(month.getKey());
//					break;
//				}
//			}
//		});
	}
	
	
	//===============================================================================================================================================
	
	@FXML 
	public void hideTechTab() {
		TP_Technician.setVisible(false);
		stopTimeline();
	}
	
	
	public void stopTimeline() {
		if(!timeline.getKeyFrames().isEmpty()) {
			if(timeline.getStatus() == Status.RUNNING) {
        		timeline.stop();
        		System.out.println("TIMELINE HAS BEEN STOPPED");
        	}
		}
	}
		
	
// --> TECHNICIAN SECTION <--
	
	// BUTTON ON ACTION {[ SEARCH ]} find the Technician by the ComboBox Selected & Set TableView [ Interventions ]
	@FXML
	public void initTechnicianFields() {
		 progress_loading.setVisible(true);
		 front_main_index.setDisable(true);

				if(cb_technicians.getSelectionModel().isEmpty()) {
					alert = new AlertController(AlertType.ERROR, "Please select a technician in the list.");
					progress_loading.setVisible(false);
					front_main_index.setDisable(false);
				}
				else {
						TP_Technician.setVisible(true);
						
						int index = cb_technicians.getSelectionModel().getSelectedIndex();
						technician = cbo_Technicians.get(index);
						Image img = new Image(technician.photo);
						IV_TechPicture.setFitHeight(250);
						IV_TechPicture.setFitWidth(200);
						IV_TechPicture.setImage(img);
						
						
						lbl_techId.setText(String.valueOf(technician.getId()));
						lbl_techName.setText(technician.getFirstname() + " " + technician.getLastname());
						lbl_techPhone.setText(technician.getPhone());
						lbl_techEmail.setText(technician.getEmail());
						lbl_techStatus.setText(technician.getStatus());
						
						List<Intervention> all = EmployeeController.technicianInterventions(technician.getId());
						ArrayList<Intervention> today = new ArrayList<Intervention>();
						String date = sdf.format(new Date());
						
						for(Intervention i : all) {
							if(i.getDate().equalsIgnoreCase(date))
								today.add(i);
						}
						 ObservableList<Intervention> interventions =  FXCollections.observableArrayList((ArrayList<Intervention>) today);
						 TV_TechTasksToday.setItems(interventions);
						
						 TableColumn<Intervention,String> d = new TableColumn<>("Date");
						 TableColumn<Intervention,String> availS = new TableColumn<>("Start");
						 TableColumn<Intervention,String> availE = new TableColumn<>("End");
						 TableColumn<Intervention,String> punchIn = new TableColumn<>("Arrived");
						 TableColumn<Intervention,String> punchOut = new TableColumn<>("Left");
						 TableColumn<Intervention,String> note = new TableColumn<>("Note");
						 TableColumn<Intervention,String> status = new TableColumn<>("Status");
						 TableColumn<Intervention,String> comment = new TableColumn<>("Comment");
						 
						 d.setCellValueFactory(new PropertyValueFactory<>("date"));
						 availS.setCellValueFactory(new PropertyValueFactory<>("clientAvailStart"));
						 availE.setCellValueFactory(new PropertyValueFactory<>("clientAvailEnd"));
						 punchIn.setCellValueFactory(new PropertyValueFactory<>("punchIn"));
						 punchOut.setCellValueFactory(new PropertyValueFactory<>("punchOut"));
						 note.setCellValueFactory(new PropertyValueFactory<>("note"));
						 status.setCellValueFactory(new PropertyValueFactory<>("status"));
						 comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
						 TV_TechTasksToday.getColumns().setAll(d, availS, availE,punchIn, punchOut,status, note, comment);
						 
						 locateTechnician();
						 progress_loading.setVisible(false);
						 front_main_index.setDisable(false);
						 if(today.size() == 0) {
							 
						 }else {
							 TV_TechTasksToday.setFixedCellSize(25);
							 TV_TechTasksToday.prefHeightProperty().bind(TV_TechTasksToday.fixedCellSizeProperty().multiply(Bindings.size(TV_TechTasksToday.getItems()).add(1.01)));
							 TV_TechTasksToday.minHeightProperty().bind(TV_TechTasksToday.prefHeightProperty());
							 TV_TechTasksToday.maxHeightProperty().bind(TV_TechTasksToday.prefHeightProperty());
						 }
						 
				}
	}
	
	// Insert content in ComboBox
	public void init_cb_technicians(){
		new Thread(() ->{
			cbo_Technicians = EmployeeController.AllTechnicians();
			TP_Technician_Details.setVisible(true);
				if(cbo_Technicians == null) {
					progress_loading.setVisible(false);
					front_main_index.setDisable(false);
				}else {
					for(Employee T : cbo_Technicians) {
						cb_technicians.getItems().add(T.getId() + " : " + T.lastname + " " + T.firstname);
						
					}
					progress_loading.setVisible(false);
					front_main_index.setDisable(false);
				}
		}).start();
	}
	
	// Find The Coordinate and Set The Coordinate on Map
	@FXML
	public void locateTechnician() {
			WebView webView = new WebView();
			WebEngine webEngine = webView.getEngine();
			webEngine.load("http://lachapelleaudrey.ca/googlemap.php");
	        
	        //set coordinates
	        
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
				 
				@Override
				public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
					if (newValue == Worker.State.SUCCEEDED) {
						changeLocation(webEngine);
						//timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(20), ev -> {changeLocation(webEngine);}));
					    //timeline.setCycleCount(Animation.INDEFINITE);
					    //timeline.play();
	                }
				}
	        });
	        BP_Maps.setCenter(webView);
		}
	
	// Change Location
	public void changeLocation(WebEngine webEngine) {
		Coordinates c = EmployeeController.getLocation(technician.getId());
    	System.out.println("COORDINATES : " + c.toString());
    	String tech = technician.firstname + " " + technician.lastname;
    	webEngine.executeScript("document.changeLocation(" + c.latitude +", " + c.longitude + ", '" + tech + "')");
	}
	
// ---------------------------------------------------------------------------------
	
// -- > CLIENT SECTION <--
	
	// BUTTON ON ACTION {[ SEARCH ]} find the Client by Email or Phone Number 
	@FXML
	public void searchClient(ActionEvent event) {
		reset();
		progress_loading.setVisible(true);
		front_main_index.setDisable(true);
			if((!txtEmail.getText().isEmpty() && cmbSearchBy.getValue().equals("Email")) || (!txtPhone1.getText().isEmpty() && !txtPhone2.getText().isEmpty() && !txtPhone3.getText().isEmpty() && cmbSearchBy.getValue().equals("Phone"))) {
				new Thread(() ->{
				switch(cmbSearchBy.getValue()) {
				case "Email" :
					client = ClientController.searchClient(cmbSearchBy.getValue(), txtEmail.getText()); 
					setFields(client);
					progress_loading.setVisible(false);
					front_main_index.setDisable(false);
					break;
					
				case "Phone" :
					String phoneNumber = txtPhone1.getText()+"-"+txtPhone2.getText()+"-"+txtPhone3.getText();
					client = ClientController.searchClient(cmbSearchBy.getValue(), phoneNumber);
					setFields(client);
					progress_loading.setVisible(false);
					front_main_index.setDisable(false);
					break;
				}
				}).start();
			}else {
				alert = new AlertController(AlertType.ERROR, "You cannot leave an empty field");
				progress_loading.setVisible(false);
				front_main_index.setDisable(false);
				TP_Client.setVisible(false);
			}
	}
	
	// Set Label with [ Client ] information
	public void setFields(Client client) {
		if(client == null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					alert = new AlertController(AlertType.ERROR, ClientController.errorMessage);
				}
			});
		}
		else {
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					ClientSearch.setClient(client);
					TP_Client.setVisible(true);
					setTicketsTable();
					setPackagesTable();
					setClientInfos();
					lblClientId.setText(String.valueOf(client.getId()));
					lblClientName.setText(client.getFirstname() + " " + client.getLastname());
					lblClientPhone.setText(client.getPhone());
					lblClientEmail.setText(client.getEmail());
					lblClientStatus.setText("");
					
				}
				});
		}
	}
	
	// Set The TableView with [ Address ] From Client
	public void setClientInfos() {
		
		ObservableList<Address> addresses =  FXCollections.observableArrayList((ArrayList<Address>) client.getAddresses());
		TV_Addresses.setItems(addresses);
		
		TableColumn<Address,String> civicNumber = new TableColumn<>("civic number");
		TableColumn<Address,String> appart = new TableColumn<>("appartment");
		TableColumn<Address,String> street = new TableColumn<>("street");
		TableColumn<Address,String> city = new TableColumn<>("city");
		TableColumn<Address,String> province = new TableColumn<>("province");
		TableColumn<Address,String> country = new TableColumn<>("country");
		TableColumn<Address,String> zipcode = new TableColumn<>("zipcode");
		civicNumber.setCellValueFactory(new PropertyValueFactory<>("civicnumber"));
		appart.setCellValueFactory(new PropertyValueFactory<>("appartment"));
		street.setCellValueFactory(new PropertyValueFactory<>("street"));
		city.setCellValueFactory(new PropertyValueFactory<>("city"));
		province.setCellValueFactory(new PropertyValueFactory<>("province"));
		country.setCellValueFactory(new PropertyValueFactory<>("country"));
		zipcode.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
		TV_Addresses.getColumns().setAll(civicNumber, appart, street, city, province, country, zipcode);
		TV_Addresses.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	// Set The TableView with [ Ticket ] From Client
	@FXML
	public void setTicketsTable() {
		Collection<Ticket> tickets = client.getTickets();
		
		for(Ticket ticket : tickets) {	
			
			 TableView table = new TableView();
			 ArrayList<Intervention> interventions = ticket.getInterventions();
			 table.setItems(FXCollections.observableArrayList(interventions));
			 
			 TableColumn<Intervention,String> tech = new TableColumn<>("Technician");
			 TableColumn<Intervention,String> date = new TableColumn<>("Date");
			 TableColumn<Intervention,String> availS = new TableColumn<>("Client Avail. Start");
			 TableColumn<Intervention,String> availE = new TableColumn<>("Client Avail. End");
			 TableColumn<Intervention,String> punchIn = new TableColumn<>("Arrived");
			 TableColumn<Intervention,String> punchOut = new TableColumn<>("Left");
			 TableColumn<Intervention,String> note = new TableColumn<>("Note");
			 TableColumn<Intervention,String> status = new TableColumn<>("Status");
			 TableColumn<Intervention,String> comment = new TableColumn<>("Comment");
			 
			 tech.setCellValueFactory(new PropertyValueFactory<>("technicianId"));
			 date.setCellValueFactory(new PropertyValueFactory<>("date"));
			 availS.setCellValueFactory(new PropertyValueFactory<>("clientAvailStart"));
			 availE.setCellValueFactory(new PropertyValueFactory<>("clientAvailEnd"));
			 punchIn.setCellValueFactory(new PropertyValueFactory<>("punchIn"));
			 punchOut.setCellValueFactory(new PropertyValueFactory<>("punchOut"));
			 note.setCellValueFactory(new PropertyValueFactory<>("note"));
			 status.setCellValueFactory(new PropertyValueFactory<>("status"));
			 comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
			 table.getColumns().setAll(date,status, note, tech, availS, availE,punchIn, punchOut, comment);
			 
			 SplitPane splitPane = new SplitPane();
			 splitPane.setStyle("-fx-background-color: #0081a7CC;");
//			 table.prefWidthProperty().bind(splitPane.widthProperty());
			 table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			 splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
			 Image img = new Image("application\\view\\image\\intervention.png");
			 ImageView imgView = new ImageView(img);
			 imgView.setFitHeight(32);
			 imgView.setFitWidth(32);
			 Button btn = new Button("Create Intervention for this ticket");
			 btn.setGraphic(imgView);
			 btn.setStyle("-fx-background-color: #e9c46a; -fx-text-fill: white; -fx-font-size: 16px;");
			 btn.getStyleClass().add("hover");
			 btn.getStyleClass().add("pressed");
			 btn.setCursor(Cursor.HAND);
			 
			 Image img1 = new Image("application\\view\\image\\cancel.png");
			 ImageView imgView1 = new ImageView(img1);
			 Button btnCancel= new Button("Cancel");
			 imgView1.setFitHeight(32);
			 imgView1.setFitWidth(32);
			 btnCancel.setGraphic(imgView1);
			 btnCancel.setStyle("-fx-background-color: #D9514B; -fx-text-fill: white; -fx-font-size: 16px;");
			 btnCancel.getStyleClass().add("hover");
			 btnCancel.getStyleClass().add("pressed");
			 btnCancel.setCursor(Cursor.HAND);
			 
			 
			 // Pop up Window to Create a New Interventions By Ticket Selected
			 btn.setOnMouseClicked((MouseEvent e) -> {
				T = ticket;
				AP_Index.setDisable(true);
				BorderPane index;
				try {
					index = FXMLLoader.load(getClass().getResource("/application/view/CreateIntervention.fxml"));
					index.setBackground(Background.EMPTY);
					Scene scene = new Scene(index);
					scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
					scene.setFill(Color.TRANSPARENT);
					IndexStage = new Stage();
					IndexStage.initStyle(StageStyle.TRANSPARENT);
					IndexStage.setScene(scene);
					IndexStage.toFront();
					IndexStage.setAlwaysOnTop(true);
					IndexStage.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			 });
			 
			 // Pop Up Window to Cancel Ticket selected
			 btnCancel.setOnMouseClicked(v -> {
				 T = ticket;
				 Alert alert;
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("Confirm To Cancel This Ticket #: " + T.getId());
					alert.setContentText("Are your sure ?");
					alert.initStyle(StageStyle.UNDECORATED);	
					alert.initOwner(IndexStage);
					Optional<ButtonType> press = alert.showAndWait();
					if(press.get() == ButtonType.OK) {
						T.setStatus("Cancellé");
						JSONObject obj = new JSONObject(T);
						String json = obj.toString();
						try {
							int code = HttpService.put("ticket", json);
							for(Intervention intern : interventions) {
								if(intern.getStatus().contentEquals("Assignée")) {
									intern.setStatus("Cancellé");
									JSONObject obj1 = new JSONObject(intern);
									String json1 = obj1.toString();
									int code1 = HttpService.put("intervention", json1);
								}
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
			 });
			 
			 Label label = new Label(ticket.Accordion());
			 label.setPrefHeight(150);
			 label.setStyle("-fx-text-fill: white;");
			 VBox UpperControl;
			 if(ticket.getStatus().contentEquals("incomplete")) {
				  UpperControl  = new VBox(label, btn, btnCancel);
			 }else {
				  UpperControl  = new VBox(label);
			 }
			 UpperControl.setSpacing(10);
			 UpperControl.setPadding(new Insets(20, 20, 20, 20)); 
			 Label label1 = new Label("INTERVENTIONS");
			 label1.setStyle("-fx-text-fill: white;");
		     VBox LowerControl = new VBox(label1,table);
		     LowerControl.setSpacing(10);
		     LowerControl.setPadding(new Insets(20, 20, 20, 20)); 
		     splitPane.getItems().addAll(UpperControl, LowerControl);
		     TitledPane pane = new TitledPane("Ticket : " + ticket.getId(), splitPane);
		     pane.setStyle("-fx-background-color: black;");
			 ACC_Tickets.getPanes().addAll(pane);
			 pane.setOnMouseClicked((MouseEvent e) -> {
				 if(e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
					 System.out.println(ticket.toString());
				 }
			 });
			
			 table.setFixedCellSize(30);
			 table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(1.01)));
			 table.minHeightProperty().bind(table.prefHeightProperty());
			 table.maxHeightProperty().bind(table.prefHeightProperty());
			 splitPane.setDividerPositions(1);
		 }
	}
	
	// Set The TableView with [ Package ] From Client
	public void setPackagesTable() {
		
		Collection<Package> packages = client.getPackages();
		System.out.println(packages);
		
		for(Package pack: packages) {
			
			TableView table = new TableView();
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			ArrayList<Entry> entries = (ArrayList<Entry>) pack.getEntries();
			ArrayList<Product> products = new ArrayList<Product>();
			
			for(Entry entry:entries) {
				products.add(entry.getProduct());
			}
			
			ObservableList<Product> pr =  FXCollections.observableArrayList((ArrayList<Product>) products);
			table.setItems(pr);
			TableColumn<Entry,String> id = new TableColumn<>("product id");
			TableColumn<Entry,String> type = new TableColumn<>("type");
			TableColumn<Entry,String> subtype = new TableColumn<>("subtype");
			TableColumn<Entry,String> description = new TableColumn<>("description");
			id.setCellValueFactory(new PropertyValueFactory<>("id"));
			type.setCellValueFactory(new PropertyValueFactory<>("type"));
			subtype.setCellValueFactory(new PropertyValueFactory<>("subtype"));
			description.setCellValueFactory(new PropertyValueFactory<>("description"));
			table.getColumns().setAll(id, type, subtype,description);
			TitledPane pane = new TitledPane("Package " + pack.getId(), table);
			ACC_Packages.getPanes().add(pane);
			
			table.setFixedCellSize(25);
			table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(1.01)));
			table.minHeightProperty().bind(table.prefHeightProperty());
			table.maxHeightProperty().bind(table.prefHeightProperty());
		}
		
		
	}
	
	// Pop Up Window Page To Create a New Ticket
	@FXML
	public void createNewTicket(ActionEvent event) throws IOException {
		AP_Index.setDisable(true);
		BorderPane index = FXMLLoader.load(getClass().getResource("/application/view/CreateTicket.fxml"));
		index.setBackground(Background.EMPTY);
		Scene scene = new Scene(index);
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		IndexStage = new Stage();
		IndexStage.initStyle(StageStyle.TRANSPARENT);
		IndexStage.setScene(scene);
		IndexStage.show();
	}
	
// ---------------------------------------------------------------------------------
	
// -- > TICKETS SECTION <--

	// ---------------------------------------------------------------------------------
	
	// -- > TICKETS SECTION <--
	
	public void initTicketsTable(ActionEvent event){
		ArrayList<Ticket> newList = new ArrayList();
		String selectedMonth = (String) cb_Months.getSelectionModel().getSelectedItem();
		String selectedYear = (String) cb_Years.getSelectionModel().getSelectedItem();
		month = 0; for(java.util.Map.Entry<Integer, String> monthSelected : months.entrySet()) {
				if(monthSelected.getValue().equalsIgnoreCase(selectedMonth)) {
					month = monthSelected.getKey();
				}
			}
		
		TV_Tickets_Search.getItems().clear();
		TV_Intervention_By_Ticket.getItems().clear();
		TV_Tickets_Search.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TV_Intervention_By_Ticket.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		progress_loading.setVisible(true);
		front_main_index.setDisable(true);
		RadioButton selected = (RadioButton) group.getSelectedToggle();

//		System.out.println(selected.getText());
			switch(selected.getText()) {
				case "Complete":
					new Thread(() -> {
						ObservableList<Ticket> list = FXCollections.observableArrayList(TicketController.getAllTicketByStatus("Complete"));
						Platform.runLater(() -> {
							newList.clear();
							for(Ticket ticket : list) {
								String dateTicket = ticket.getCreationDate();
								String[] spliter = dateTicket.split("-");						
								if(month == 0) {
										if(spliter[0].equalsIgnoreCase(selectedYear)) { newList.add(ticket); }
								}else if(spliter[0].equalsIgnoreCase(selectedYear) && Integer.valueOf(spliter[1]).equals(month)){
									newList.add(ticket);
								}
							}
							list.clear();
							ObservableList<Ticket> listChecked = FXCollections.observableArrayList(newList);
							TV_Tickets_Search.setItems(listChecked);
							
							TableColumn<Ticket,Integer> id = new TableColumn<>("id");
							TableColumn<Ticket,Integer> addressId = new TableColumn<>("addressId");
							TableColumn<Ticket,Integer> clientId = new TableColumn<>("clientId");
							TableColumn<Ticket,String> status = new TableColumn<>("status");
							TableColumn<Ticket,Integer> descriptionId = new TableColumn<>("descriptionId");
							TableColumn<Ticket,String> creationDate = new TableColumn<>("creationDate");
							TableColumn<Ticket,String> closingDate = new TableColumn<>("closingDate");
							
							id.setCellValueFactory(new PropertyValueFactory<>("id"));
							addressId.setCellValueFactory(new PropertyValueFactory<>("addressId"));
							clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
							status.setCellValueFactory(new PropertyValueFactory<>("status"));
							descriptionId.setCellValueFactory(new PropertyValueFactory<>("descriptionId"));
							creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
							closingDate.setCellValueFactory(new PropertyValueFactory<>("closingDate"));
							TV_Tickets_Search.getColumns().setAll(id, addressId, clientId, status, descriptionId, creationDate, closingDate);
							
							progress_loading.setVisible(false);
							front_main_index.setDisable(false);
							
						});
					}).start();
					break;
				case "Incomplete":
					new Thread(() -> {
						ObservableList<Ticket> list = FXCollections.observableArrayList(TicketController.getAllTicketByStatus("incomplete"));
						Platform.runLater(() -> {
							newList.clear();
							for(Ticket ticket : list) {
								String dateTicket = ticket.getCreationDate();
								String[] spliter = dateTicket.split("-");						
								if(month == 0) {
										if(spliter[0].equalsIgnoreCase(selectedYear)) { newList.add(ticket); }
								}else if(spliter[0].equalsIgnoreCase(selectedYear) && Integer.valueOf(spliter[1]).equals(month)){
									newList.add(ticket);
								}
							}
							list.clear();
							ObservableList<Ticket> listChecked = FXCollections.observableArrayList(newList);
							TV_Tickets_Search.setItems(listChecked);
							
							TableColumn<Ticket,Integer> id = new TableColumn<>("id");
							TableColumn<Ticket,Integer> addressId = new TableColumn<>("addressId");
							TableColumn<Ticket,Integer> clientId = new TableColumn<>("clientId");
							TableColumn<Ticket,String> status = new TableColumn<>("status");
							TableColumn<Ticket,Integer> descriptionId = new TableColumn<>("descriptionId");
							TableColumn<Ticket,String> creationDate = new TableColumn<>("creationDate");
							TableColumn<Ticket,String> closingDate = new TableColumn<>("closingDate");
							
							id.setCellValueFactory(new PropertyValueFactory<>("id"));
							addressId.setCellValueFactory(new PropertyValueFactory<>("addressId"));
							clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
							status.setCellValueFactory(new PropertyValueFactory<>("status"));
							descriptionId.setCellValueFactory(new PropertyValueFactory<>("descriptionId"));
							creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
							closingDate.setCellValueFactory(new PropertyValueFactory<>("closingDate"));
							TV_Tickets_Search.getColumns().setAll(id, addressId, clientId, status, descriptionId, creationDate, closingDate);
							
							progress_loading.setVisible(false);
							front_main_index.setDisable(false);
						});
					}).start();
					break;
				case "All":
					new Thread(() -> {
						ObservableList<Ticket> list =  FXCollections.observableArrayList(TicketController.getAllTickets());	
						Platform.runLater(() -> {
								TV_Tickets_Search.setItems(list);
								
								TableColumn<Ticket,Integer> id = new TableColumn<>("id");
								TableColumn<Ticket,Integer> addressId = new TableColumn<>("addressId");
								TableColumn<Ticket,Integer> clientId = new TableColumn<>("clientId");
								TableColumn<Ticket,String> status = new TableColumn<>("status");
								TableColumn<Ticket,Integer> descriptionId = new TableColumn<>("descriptionId");
								TableColumn<Ticket,String> creationDate = new TableColumn<>("creationDate");
								TableColumn<Ticket,String> closingDate = new TableColumn<>("closingDate");
								
								id.setCellValueFactory(new PropertyValueFactory<>("id"));
								addressId.setCellValueFactory(new PropertyValueFactory<>("addressId"));
								clientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
								status.setCellValueFactory(new PropertyValueFactory<>("status"));
								descriptionId.setCellValueFactory(new PropertyValueFactory<>("descriptionId"));
								creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
								closingDate.setCellValueFactory(new PropertyValueFactory<>("closingDate"));
								TV_Tickets_Search.getColumns().setAll(id, addressId, clientId, status, descriptionId, creationDate, closingDate);
								
								progress_loading.setVisible(false);
								front_main_index.setDisable(false);
						});
					}).start();
					break;
			}
	}

	
	public void setTableInterventionForTickets() {
		TV_Tickets_Search.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			Ticket ticket = (Ticket) obs.getValue();
			progress_loading.setVisible(true);
			front_main_index.setDisable(true);
			new Thread(() -> {
				try {
					listInterventions =  FXCollections.observableArrayList(InterventionController.getAllInterventionByTicketID(ticket.getId()));	
				}catch(NullPointerException e) {
					listInterventions = null;
				}
				Platform.runLater(() -> {
						if(listInterventions != null) {
						TV_Intervention_By_Ticket.setItems(listInterventions);
						TableColumn<Ticket,Integer> id = new TableColumn<>("id");
						TableColumn<Ticket,Integer> ticketId = new TableColumn<>("ticketId");
						TableColumn<Ticket,Integer> technicianId = new TableColumn<>("technicianId");
						TableColumn<Ticket,String> date = new TableColumn<>("date");
						TableColumn<Ticket,String> clientAvailStart = new TableColumn<>("clientAvailStart");
						TableColumn<Ticket,String> clientAvailEnd = new TableColumn<>("clientAvailEnd");
						TableColumn<Ticket,String> punchIn = new TableColumn<>("punchIn");
						TableColumn<Ticket,String> punchOut = new TableColumn<>("punchOut");
						TableColumn<Ticket,String> status = new TableColumn<>("status");
						TableColumn<Ticket,String> note = new TableColumn<>("note");
						TableColumn<Ticket,String> comment = new TableColumn<>("comment");
						TableColumn<Ticket,String> communicationType = new TableColumn<>("communicationType");
						
						id.setCellValueFactory(new PropertyValueFactory<>("id"));
						ticketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
						technicianId.setCellValueFactory(new PropertyValueFactory<>("technicianId"));
						date.setCellValueFactory(new PropertyValueFactory<>("date"));
						clientAvailStart.setCellValueFactory(new PropertyValueFactory<>("clientAvailStart"));
						clientAvailEnd.setCellValueFactory(new PropertyValueFactory<>("clientAvailEnd"));
						punchIn.setCellValueFactory(new PropertyValueFactory<>("punchIn"));
						punchOut.setCellValueFactory(new PropertyValueFactory<>("punchOut"));
						status.setCellValueFactory(new PropertyValueFactory<>("status"));
						note.setCellValueFactory(new PropertyValueFactory<>("note"));
						comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
						communicationType.setCellValueFactory(new PropertyValueFactory<>("communicationType"));
						TV_Intervention_By_Ticket.getColumns().setAll(id, ticketId, technicianId, date, clientAvailStart, clientAvailEnd, punchIn, punchOut, status, comment, communicationType);
						
						progress_loading.setVisible(false);
						front_main_index.setDisable(false);
					}
				});
				
			}).start();
		});
	}

// ---------------------------------------------------------------------------------
	
// -- > LIVE SECTION <--
	
	// Live show all Technician on Map Live with recent Coordinate
	
	@FXML
	public void initMapLiveTechnician(){
		AP_Live.setVisible(false);
		hbox_display_btn_live_track_tech.getChildren().clear();
		 progress_loading.setVisible(true);
		 front_main_index.setDisable(true);
		 WebView webView = new WebView();
		 WebEngine webEngine = webView.getEngine();
		 webEngine.load("http://lachapelleaudrey.ca/googlemap.php");
	        
        //set coordinates
    	webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
				 
			@Override
			public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
				if (newValue == Worker.State.SUCCEEDED) {
					new Thread(() ->{
						List<Employee> list = EmployeeController.AllTechnicians();
						ObservableList<Employee> technicians =  FXCollections.observableArrayList(list);
						tableTechnicianLive.setItems(technicians);
						
						TableColumn<Employee,String> firstname = new TableColumn<>("Firstname");
						TableColumn<Employee,String> lastname = new TableColumn<>("LastName");
						TableColumn<Employee,String> statu = new TableColumn<>("Status");
						
						firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
						lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
						statu.setCellValueFactory(new PropertyValueFactory<>("status"));
						tableTechnicianLive.getColumns().setAll(firstname, lastname, statu);
						for(Employee emp : list) {
							String tech = emp.firstname + " " + emp.lastname;
							String status = " : " + emp.getStatus();
							System.out.println(emp.getFirstname());
							Coordinates c = EmployeeController.getLocation(emp.getId());
							System.out.println("COORDINATES : " + c.toString());
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									webEngine.executeScript("document.changeLocation(" + c.latitude +", " + c.longitude + ", '" + tech + status + "')");
								}
							});
						}
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								AP_Live.setCenter(webView);
						        progress_loading.setVisible(false);
						        front_main_index.setDisable(false);	
						        AP_Live.setVisible(true);
						        tableTechnicianLive.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
									Employee em = (Employee) obs.getValue();
									 progress_loading.setVisible(true);
									 front_main_index.setDisable(true);
									 AP_Live.setDisable(true);
									new Thread(() -> {
										Coordinates c = EmployeeController.getLocation(em.getId());
										String tech = em.firstname + " " + em.lastname;
										String status = " : " + em.getStatus();
										Platform.runLater(new Runnable() {
											@Override
											public void run() {
												webEngine.executeScript("document.changeLocation(" + c.latitude +", " + c.longitude + ", '" + tech + status + "')");
												progress_loading.setVisible(false);
										        front_main_index.setDisable(false);	
										        AP_Live.setDisable(false);
											}});
									}).start();
						        });
							}
						});
					}).start();
                }
			}
        });  
	}
	
	
//----------------------------------------------------------------------------------------------------------------
// 	 DASHBOARD [ LEFT SIDE BUTTON ]
//	 * Search a Client
//	 * Search a Tech.
//	 * Tickets
//	 * Live
//	 * Log Out

	// DASHBOARD LEFT SIDE BUTTON --> Set Visible BUTTON ( Search a Client )
	@FXML
	public void searchClientView(ActionEvent event) {
		stopTimeline();
		SearchClientView.setVisible(true);
		SearchClientView.toFront();
		AP_Technician.setVisible(false);
		
		btnSearchClient.setStyle("-fx-background-color : " + selected +" -fx-background-radius : 30 0 0 30;");
		btnSearchClient.setPrefWidth(250.00);
		
		btn_SearchTech.setStyle("-fx-background-color : " + not_selected);
		btn_SearchTech.setPrefWidth(200.00);
		
		btn_Tickets.setStyle("-fx-background-color : " + not_selected);
		btn_Tickets.setPrefWidth(200.00);
		
		btnLive.setStyle("-fx-background-color : " + not_selected);
		btnLive.setPrefWidth(200.00);
		
		TP_Client.setVisible(false);
		AP_Tickets.setVisible(false);
		AP_Live.setVisible(false);
	}

	// DASHBOARD LEFT SIDE BUTTON --> Set Visible BUTTON ( Search a Tech. ) 
	// & Insert content in ComboBox -> Follow the Method = init_cb_technicians()
	
	// DASHBOARD LEFT SIDE BUTTON --> Set Visible BUTTON ( Search a Tech. ) 
	// & Insert content in ComboBox -> Follow the Method = init_cb_technicians()
	@FXML
	public void searchTechnicianFields() {
		progress_loading.setVisible(true);
		front_main_index.setDisable(true);
		
		stopTimeline();
		
		btnSearchClient.setStyle("-fx-background-color : " + not_selected);
		btnSearchClient.setPrefWidth(200.00);
		
		btn_SearchTech.setStyle("-fx-background-color : " + selected +" -fx-background-radius : 30 0 0 30;");
		btn_SearchTech.setPrefWidth(250.00);
		
		
		btn_Tickets.setStyle("-fx-background-color : " + not_selected);
		btn_Tickets.setPrefWidth(200.00);
		
		btnLive.setStyle("-fx-background-color : " + not_selected);
		btnLive.setPrefWidth(200.00);
	
		AP_Technician.setVisible(true);
		AP_Technician.toFront();
		TP_Technician_Details.setVisible(false);
		
		SearchClientView.setVisible(false);
		AP_Tickets.setVisible(false);
		AP_Live.setVisible(false);
		//TP_Technician.setVisible(false);
		cb_technicians.getItems().clear();
		init_cb_technicians();
		
	}
	
	// DASHBOARD LEFT SIDE BUTTON --> Set Visible BUTTON ( Tickets )
	@FXML
	public void searchTickets() {
		btnSearchClient.setStyle("-fx-background-color : " + not_selected);
		btnSearchClient.setPrefWidth(200.00);
		
		btn_SearchTech.setStyle("-fx-background-color : " + not_selected);
		btn_SearchTech.setPrefWidth(200.00);
		
		btn_Tickets.setStyle("-fx-background-color : " + selected +" -fx-background-radius : 30 0 0 30;");
		btn_Tickets.setPrefWidth(250.00);
		
		btnLive.setStyle("-fx-background-color : " + not_selected);
		btnLive.setPrefWidth(200.00);
		
		AP_Technician.setVisible(false);
		SearchClientView.setVisible(false);
		AP_Tickets.setVisible(true);
		AP_Tickets.toFront();
		AP_Live.setVisible(false);
		if(clickCount == 0) { setTableInterventionForTickets(); clickCount++; }
	}
	
	// DASHBOARD LEFT SIDE BUTTON --> Set Visible BUTTON ( LIVE )
	@FXML
	public void liveDisplay(ActionEvent event) {
		btnSearchClient.setStyle("-fx-background-color : " + not_selected);
		btnSearchClient.setPrefWidth(200.00);
		
		btn_SearchTech.setStyle("-fx-background-color : " + not_selected);
		btnSearchClient.setPrefWidth(200.00);
		
		btn_Tickets.setStyle("-fx-background-color : " + not_selected);
		btnSearchClient.setPrefWidth(200.00);
		
		btnLive.setStyle("-fx-background-color : " + selected +" -fx-background-radius : 30 0 0 30;");
		btnLive.setPrefWidth(250.00);
		
		initMapLiveTechnician();
		AP_Technician.setVisible(false);
		SearchClientView.setVisible(false);
		AP_Tickets.setVisible(false);
		
			}

	// Log Out / Close the Main Index Page and go back to the Login Page
	
	// Log Out / Close the Main Index Page and go back to the Login Page
	@FXML
	public void signout(MouseEvent event) {
			Alert alert;
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Confirm Logout");
			alert.setContentText("Are your sure you want to Logout?");
			alert.initStyle(StageStyle.UNDECORATED);	
			alert.initOwner(IndexStage);
			Optional<ButtonType> press = alert.showAndWait();
			if(press.get() == ButtonType.OK) {
				Main.stage.show();
				LoginController.IndexStage.hide();
			}
		}
		
//----------------------------------------------------------------------------------------------------------------	
	
	// Enable the FullScreen Mode
	@FXML
	public void fullscreen(MouseEvent event) {
		if(LoginController.IndexStage.isFullScreen()) {
			imgBackground.setFitHeight(850.0);
			imgBackground.setFitWidth(1400.0);
			LoginController.IndexStage.setFullScreen(false);
		}else {
			LoginController.IndexStage.setFullScreen(true);
			imgBackground.setFitHeight(1080.0);
			imgBackground.setFitWidth(1920.0);
		}
	}
	
	// Reset Content Show in Index [ REFRESH ]
	@FXML
	public void reset() {
		TV_Addresses.setItems(null);
		ACC_Packages.getPanes().clear();
		ACC_Tickets.getPanes().clear();
	}
		
	// Close The Application
	@FXML
	public void exit(MouseEvent event){
		System.out.println("Fermeture de l'application");
		System.exit(0);
	}
}
