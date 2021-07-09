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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import application.model.Description;
import application.model.Employee;

public class DescriptionController {

	private static BufferedReader br;
	private static String jsonContent;
	private static JSONTokener jsonToken;
	private static JSONArray jsonList;
	private static JSONObject obj;
	public static List<Description> descriptionsList;
	private static final String BASE_URL = HttpService.baseUri;
	
	
	public static List<Description> getDescriptionsList() {
		return descriptionsList;
	}

	public static List<Description> getDescriptions(){
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(BASE_URL + "description");
			request.addHeader("accept", "application/json");
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == 200) {
				br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), Charsets.UTF_8));
				jsonContent = br.readLine();
				jsonToken = new JSONTokener(jsonContent);
				ObjectMapper objectMapper = new ObjectMapper();
				List<Description> descr = objectMapper.readValue(jsonContent, new TypeReference<List<Description>>() {});
				descriptionsList = descr;
				return descr;
			}
		} catch (IOException | JSONException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static ArrayList<String> getDescriptionStrings(List<Description> desc) {
		ArrayList<String> list = new ArrayList<>();
		for(Description d : desc) {
			list.add(d.toString());
		}
		return list;
	}
}
