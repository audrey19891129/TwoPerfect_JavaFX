package application.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import application.model.Address;
import application.model.Client;
import application.model.Ticket;

public class ClientController {
	public static String errorMessage;
	private static BufferedReader br;
	private static String jsonContent;
	private static JSONTokener jsonToken;
	private static JSONArray jsonList;
	private static JSONObject obj;
	
	private static final String BASE_URL = HttpService.baseUri;
	
	public static Client searchClient(String option, String enter) {
		HttpGet request = null;
		Client client = null;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			switch(option) {
			case "Email" :
				request = new HttpGet(BASE_URL + "clientByEmail/"+enter); break;
			case "Phone" :
				request = new HttpGet(BASE_URL + "clientByPhone/"+enter); break;
			}
			request.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(request);
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
	            client = objectMapper.readValue(jsonContent, Client.class);
				System.out.println(client);
			}
		}catch(IOException | JSONException e) {
			errorMessage = e.getMessage();
			return null;
		}
		return client;
	}
	
	public static Client getClientById(int id) {
		HttpGet request = null;
		Client client = null;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			request = new HttpGet(BASE_URL + "client/" + id);
			request.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(request);
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
	            client = objectMapper.readValue(jsonContent, Client.class);
				System.out.println(client);
			}
		}catch(IOException | JSONException e) {
			errorMessage = e.getMessage();
			return null;
		}
		return client;
	}
}
