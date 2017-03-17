package rest_Data_Retriever;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Receives URI from DataController. Sends HTTP request to Google Maps API. Receives API response.
 * Sends response back to DataController.
 */
public class GoogleDR {
	
	/**
	 * Retrieves the requested map from google maps API
	 * @param Location Location ID used for retrieving map
	 * @return google maps API response
	 * @throws UnirestException If there was an error when sending the http request to google
	 */
	public static Response googleAPIRetriever(String location) throws UnirestException{
		
		//Get the locations JSONArray provided by Auroras.live
		JSONArray jArray = locationsJSONRetriever();
		
		//Parse out the JSONObject that contains the location string
		JSONObject jObject = null;
		int length = jArray.length();
		String id = null;
		for(int i = 0; i < length; i++){
			jObject = jArray.getJSONObject(i);
			id = jObject.getString("id");
			if(id.equals(location)){
				break;
			}
		}
		
		//Error handling - If the location is not found, then return an error message
		if(!id.equals(location)){
			JSONObject jsonObject = new JSONObject();
			String module = "map";
			jsonObject.put("module", module);
			String message = "Location not found";
			jsonObject.put("message", message);
			int status = 404;
			jsonObject.put("statuscode", status);
			return Response.status(404).type("application/json").entity(jsonObject.toString()).build();
		}

		//Create string for latitude and longitue positions
		String latLongString = jObject.getString("lat") + "," + jObject.getString("long");
		//Create string to add marker to the map, using latitude and longitude
		String markerString = "&markers=color:blue%7C" + latLongString;
		
		HttpResponse<InputStream> response =
		Unirest.get("https://maps.googleapis.com/maps/api/staticmap?center=" + latLongString + markerString + "&zoom=13&size=1920x10800&key=AIzaSyBTQ67ylZO2ayd8rH_c1rzDkDlf2QoQTBs")
		 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0").asBinary();
		return Response.status(200).type("image/jpeg").entity(response.getBody()).build();
	}
	
	
	/**
	 * Helper to retrieve the locations JSONArray from Auroras.live
	 * @return The locations JSONArray
	 * @throws UnirestException
	 */
	private static JSONArray locationsJSONRetriever() throws UnirestException{
		
		JSONArray jsonArray = new JSONArray();
		
		HttpResponse<JsonNode> response =
		Unirest.get("http://api.auroras.live/v1/?type=locations")
		 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0")
		 .asJson();
		
		jsonArray = response.getBody().getArray();
		
		return jsonArray;
	}
}
