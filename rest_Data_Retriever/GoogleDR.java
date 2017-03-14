package rest_Data_Retriever;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GoogleDR {
	
	/**
	 * Retrieves the requested map from google maps API
	 * @param Location Location ID used for retrieving map
	 * @return google maps API response
	 * @throws UnirestException If there was an error when sending the http request to google
	 */
	public static Response googleAPIRetriever(String Location) throws UnirestException{
		HttpResponse<InputStream> response =
		Unirest.get("https://maps.googleapis.com/maps/api/staticmap?center=" + Location + "&zoom=13&size=600x300&key=AIzaSyBTQ67ylZO2ayd8rH_c1rzDkDlf2QoQTBs")
		 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0").asBinary();
		return Response.status(200).entity(response.getBody()).build();
	}
}
