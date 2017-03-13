package cache_Controller;

import javax.ws.rs.core.Response;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * receive uri info from request handler. pass uri to auroraDR.
 * receives json object (http response) from auroraDR. passes json object back to request handler.
 */
public class DataController {

	public static Response retreiveJson(String URI){
		try {
			return rest_Data_Retreiver.AuroraDR.jsonRetriever(URI);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Response retreiveImage(String URI){
		try {
			return rest_Data_Retreiver.AuroraDR.imageRetriever(URI);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Response retreiveMap(String URI){
		try {
			return rest_Data_Retreiver.GoogleDR.imageRetriever(URI);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
