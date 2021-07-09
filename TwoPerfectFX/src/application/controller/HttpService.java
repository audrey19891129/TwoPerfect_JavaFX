package application.controller;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpService {

	public static String baseUri = "https://twoperfect.herokuapp.com/twoperfect/services/"; //"http://localhost:8080/twoperfect/services/";
			
	
	public static int post(String service, String object) throws ClientProtocolException, IOException {  

		HttpClient client = HttpClientBuilder.create().build();
	    HttpPost httpPost = new HttpPost(baseUri + service);
	    StringEntity entity = new StringEntity(object, "UTF-8");
	    httpPost.setEntity(entity);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
	    HttpResponse response = client.execute(httpPost);
	    System.out.println(response);
	    return response.getStatusLine().getStatusCode();
	}
	
	public static int put(String service, String object) throws ClientProtocolException, IOException {  

		HttpClient client = HttpClientBuilder.create().build();
	    HttpPut httpPut = new HttpPut(baseUri + service);
	    StringEntity entity = new StringEntity(object, "UTF-8");
	    httpPut.setEntity(entity);
	    httpPut.setHeader("Accept", "application/json");
	    httpPut.setHeader("Content-type", "application/json");
	    HttpResponse response = client.execute(httpPut);
	    System.out.println(response);
	    return response.getStatusLine().getStatusCode();
	}
}
