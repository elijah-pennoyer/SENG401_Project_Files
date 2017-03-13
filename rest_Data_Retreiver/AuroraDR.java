package rest_Data_Retreiver;

import org.json.JSONArray;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

/**
 * receive uri from data controller. send http request to aurora api. receive api response.
 * sends response back to data controller.
 *
 */
public class AuroraDR {

	public static Response jsonRetriever(String URI) throws UnirestException{
		JSONArray jsonArray = new JSONArray();
		HttpResponse<JsonNode> response =
		Unirest.get("http://api.auroras.live/v1/" + URI)
		 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0")
		 .asJson();
		jsonArray = response.getBody().getArray();
		String att = "Powered by Auroras.live";
		jsonArray.getJSONObject(jsonArray.length()-1).put("Attribution", att);
		return Response.status(200).entity(response.getBody().toString()).build();
	}
	
	public static Response imageRetriever(String URI) throws UnirestException{
		HttpResponse<InputStream> response =
		Unirest.get("http://api.auroras.live/v1/" + URI)
		 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0").asBinary();
		return Response.status(200).entity(response.getBody()).build();
	}
}
