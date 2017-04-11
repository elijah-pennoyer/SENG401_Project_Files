package rest_Data_Retriever;

import java.io.InputStream;
import java.security.InvalidParameterException;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Receives URI from DataController. Sends HTTP request to Aurora API. Receives API response.
 * Sends response back to DataController.
 */
public class AuroraDR {

	/**
	 * Retrieves the requested image from auroras.live APIs
	 * @param URI Query parameters needed in retrieving the desired object
	 * @return auroras.live API response
	 * @throws UnirestException If there was an error when sending the http request to auroras.live
	 */
	public static Response auroraAPI_ImageRetriever(String URI) throws UnirestException{
		HttpResponse<InputStream> response = null;
		//send HTTP request to Auroras.live and get response
		try {
			response = Unirest.get("http://api.auroras.live/v1/?" + URI)
					.header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0").asBinary();
		} catch (IllegalArgumentException e) {
			//if an exception is thrown, return an error message
			JSONObject jsonObject = new JSONObject();
			String message = "Illegal character in query.";
			jsonObject.put("message", message);
			int status = 404;
			jsonObject.put("statuscode", status);
			return Response.status(404).type("application/json").entity(jsonObject.toString()).build();
		}
		
		//get the status of the response from Auroras.live
		int status = response.getStatus();
		
		//based on status the response will be of different types, return a response of the appropriate type
		if(status == 200){
			return Response.status(200).type("image/jpeg").entity(response.getBody()).build();
		}
		else if(status == 404){
			return Response.status(404).type("application/json").entity(response.getBody()).build();
		}
		else{
			throw new InvalidParameterException("Unexpected return status from Aurora API");
		}
	}
	
	/**
	 * Retrieves the requested JSON object from auroras.live APIs
	 * @param URI Query parameters needed in retrieving the desired object
	 * @return auroras.live API response
	 * @throws UnirestException If there was an error when sending the HTTP request to auroras.live
	 */
	public static Response auroraAPI_JSONRetriever(String URI) throws UnirestException{
		JSONArray jsonArray = new JSONArray();
		HttpResponse<JsonNode> response = null;
		//send HTTP request to Auroras.live and get response
		try {
			response = Unirest.get("http://api.auroras.live/v1/?" + URI)
					.header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0").asJson();
		} catch (IllegalArgumentException e) {
			//if an exception is thrown, return an error message
			JSONObject jsonObject = new JSONObject();
			String message = "Illegal character in query.";
			jsonObject.put("message", message);
			int status = 404;
			jsonObject.put("statuscode", status);
			return Response.status(404).type("application/json").entity(jsonObject.toString()).build();
		}
		jsonArray = response.getBody().getArray();
		int status = response.getStatus();
		//add attribution and return the response from Auroras.live
		String att = "Powered by Auroras.live";
		jsonArray.getJSONObject(jsonArray.length()-1).put("Attribution", att);
		return Response.status(status).type("application/json").entity(response.getBody().toString()).build();
	}
}
