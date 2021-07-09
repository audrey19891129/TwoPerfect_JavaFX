package application.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import application.model.Coordinates;
import application.model.Description;
import application.model.Employee;
import application.model.Intervention;

public class EmployeeController {
	public static String errorMessage;
	private static BufferedReader br;
	private static String jsonContent;
	private static JSONTokener jsonToken;
	private static JSONObject obj;
	private static final String BASE_URL = "https://twoperfect.herokuapp.com/twoperfect/services/";

	public static Employee getEmployeeById(int id) {
		Employee emp = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(BASE_URL + "employee/"+id);
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() != 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				obj = new JSONObject(jsonToken);
				errorMessage = obj.getString("ERROR");
				System.out.println(obj.getString("ERROR")); 		
			}else {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				obj = new JSONObject(jsonToken);
				ObjectMapper objectMapper = new ObjectMapper();
	            emp = objectMapper.readValue(jsonContent, Employee.class);
				System.out.println(emp);
				
			}
		} catch (IOException | JSONException e) {
			errorMessage = e.getMessage();
			return null;
		}
		return emp;
	}
	
	public static Employee login(String user, String pass) {
		Employee emp = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(BASE_URL + "employee/"+user+"/"+pass);
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() != 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				obj = new JSONObject(jsonToken);
				errorMessage = obj.getString("ERROR");		
			}else {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				obj = new JSONObject(jsonToken);
				
				ObjectMapper objectMapper = new ObjectMapper();
	            emp = objectMapper.readValue(jsonContent, Employee.class);
				System.out.println(emp);
				if(!(emp.getTitle().equalsIgnoreCase("prepose"))){ 
					errorMessage = "Access Denied : only prepose can have access"; 
				}
			}
		}catch(IOException | JSONException e) {
			errorMessage = e.getMessage();
			return null;
		}
		return emp;
	}
	
	public static List<Employee> AllTechnicians(){
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(BASE_URL + "employee");
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() != 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				obj = new JSONObject(jsonToken);
				errorMessage = obj.getString("ERROR");
				System.out.println(obj.getString("ERROR")); 		
			}else {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				ObjectMapper objectMapper = new ObjectMapper();
				List<Employee> tech = objectMapper.readValue(jsonContent, new TypeReference<List<Employee>>() {});
				return tech;
				
			}
		} catch (IOException | JSONException e) {
			errorMessage = e.getMessage();
		}
		return null;
	}
	
	public static List<Intervention> technicianInterventions(int id){
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(BASE_URL + "intervention/byEmployee/" + id);
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() != 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				obj = new JSONObject(jsonToken);
				errorMessage = obj.getString("ERROR");
				System.out.println(obj.getString("ERROR")); 		
			}else {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				ObjectMapper objectMapper = new ObjectMapper();
				List<Intervention> tech = objectMapper.readValue(jsonContent, new TypeReference<List<Intervention>>() {});
				return tech;
				
			}
		} catch (IOException | JSONException e) {
			errorMessage = e.getMessage();
		}
		return null;
	}
	
	public static Coordinates getLocation(int techId){
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(BASE_URL + "coordinates/" + techId);
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() != 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				obj = new JSONObject(jsonToken);
				errorMessage = obj.getString("ERROR");
				System.out.println(obj.getString("ERROR")); 		
			}else {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				ObjectMapper objectMapper = new ObjectMapper();
				Coordinates c = objectMapper.readValue(jsonContent, Coordinates.class);
				return c;
				
			}
		} catch (IOException | JSONException e) {
			errorMessage = e.getMessage();
		}
		return null;
		
	}
	
}
