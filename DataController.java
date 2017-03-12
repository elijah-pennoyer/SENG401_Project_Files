import javax.ws.rs.core.Response;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * receive uri info from request handler. pass uri to auroraDR.
 * receives json object (http response) from auroraDR. passes json object back to request handler.
 */
public class DataController {

	public static Response retreiveJson(String URI){
		try {
			return AuroraDR.jsonRetreiver(URI);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Response retreiveImage(String URI){
		return null;
	}
}
