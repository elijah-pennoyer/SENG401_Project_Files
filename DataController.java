import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * receive uri info from request handler. pass uri to auroraDR.
 * receives json object (http response) from auroraDR. passes json object back to request handler.
 */
public class DataController {

	public static Response foo(String URI){
		try {
			return AuroraDR.bar(URI);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
