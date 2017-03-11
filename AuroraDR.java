import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;

/**
 * receive uri from data controller. send http request to aurora api. receive api response.
 * sends response back to data controller.
 *
 */
public class AuroraDR {

	public static Response bar(String URI) throws UnirestException{
		JSONObject jsonObject = new JSONObject();
		System.out.println("http://api.auroras.live/v1/" + URI);
		HttpResponse<JsonNode> response =
		Unirest.get("http://api.auroras.live/v1/" + URI)
		 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0")
		 .asJson();
		jsonObject = response.getBody().getObject();
		String att = "Powered by Auroras.live";
		jsonObject.put("Attribution", att);
		return Response.status(200).entity(response.getBody().toString()).build();

	}
}
