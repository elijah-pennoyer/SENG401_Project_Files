package cache_Controller;

import javax.ws.rs.core.Response;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Receives URI info from RequestHandler. Passes URI to AuroraDR or GoogleDR.
 * Receives JSON object or image (HTTP response) from AuroraDR, or a map image from Google DR.
 * Passes the recieved JSON object or image back to RequestHandler.
 */
public class DataController {
	
	public static Response retrieveAuroraImage(String URI){
		try {
			return rest_Data_Retriever.AuroraDR.auroraAPI_ImageRetriever(URI);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//@Produces("application/json")
	public static Response retrieveAuroraJSON(String URI){
		try {
			return rest_Data_Retriever.AuroraDR.auroraAPI_JSONRetriever(URI);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Response retreiveMap(String Location){
		try {
			return rest_Data_Retriever.GoogleDR.googleAPIRetriever(Location);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
