package rest_Data_Retreiver;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GoogleDR {
	
	public static Response imageRetriever(String URI) throws UnirestException{
		HttpResponse<InputStream> response =
		Unirest.get("http://maps.googleapis.com/maps/api/sticmap?" + URI)
		 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0").asBinary();
		return Response.status(200).entity(response.getBody()).build();
	}
}
