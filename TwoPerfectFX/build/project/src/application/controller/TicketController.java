package application.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.SyncBasicHttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import application.model.Description;
import application.model.Ticket;

public class TicketController {
	private static BufferedReader br;
	private static String jsonContent;
	private static JSONTokener jsonToken;
	private static JSONObject obj;
	private static final String BASE_URL = HttpService.baseUri;
	
	public static Collection<Ticket> getAllTickets(){
		ArrayList<Ticket> list = new ArrayList<>();
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(BASE_URL + "ticket");
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				ObjectMapper objectMapper = new ObjectMapper();
				list = objectMapper.readValue(jsonContent, new TypeReference<ArrayList<Ticket>>() {});
				return list;
			}
		} catch (IOException | JSONException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	public static Collection<Ticket> getAllTicketByStatus(String status){
		ArrayList<Ticket> list = new ArrayList<>();
		try {
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(BASE_URL + "ticket/byStatus/" + status);
				request.addHeader("accept", "application/json");
				HttpResponse response = client.execute(request);
				if(response.getStatusLine().getStatusCode() == 200) {
					br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
					jsonContent = br.readLine();
					jsonToken = new JSONTokener(jsonContent);
					ObjectMapper objectMapper = new ObjectMapper();
					list = objectMapper.readValue(jsonContent, new TypeReference<ArrayList<Ticket>>() {});
					return list;
				}
		}catch(IOException | JSONException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
}
	
