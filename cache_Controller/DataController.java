package cache_Controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * receive uri info from request handler. pass uri to auroraDR.
 * receives json object (http response) from auroraDR. passes json object back to request handler.
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
