package rest_Request_Handler;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONObject;

/**
 * Decides which function to call in the DataController, then passes the URI to the DataController
 * Returns a JSON object or jpeg image.
 */
@Path ("/")
public class RequestHandler{
	
	/**
	 * Parses the URI of an HTTP request and returns the appropriate Auroras.live JSON object or image
	 * @param uriInfo - A multivalued map containing all of the URI's parameters
	 * @return Either a JSON object or an image, depending on the user request
	 */
	@Path ("")
	@GET
	@Produces({"image/jpeg", "application/json"})
	public Response handleRequest(@Context UriInfo uriInfo){
		
		String query = uriInfo.getRequestUri().getQuery();
		
		MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
		List<String> typeList = queryParameters.get("type");
		String type = typeList.get(typeList.size()-1);
		
		if(type.equals("embed") || (type.equals("images") && !queryParameters.containsKey("action"))){
			
			//Call DataController.retrieveAuroraImage to retrieve the data, either from the Database 
			//or by making a request to an API if the data isn't in the Database.
			return cache_Controller.DataController.retrieveAuroraImage(query);
		}
		else if(type.equals("map")){
			List<String> idList = queryParameters.get("id");
			String id = null;
			if(idList != null && !idList.isEmpty()){
				id = idList.get(idList.size()-1);
			}
			else{
				//TODO: maybe make this in order
				//return a json object explaining to the user that it is NOT ok to not provide a location id
				JSONObject jsonObject = new JSONObject();
				String module = "Map";
				jsonObject.put("module", module);
				String message = "Parameter is missing from request: id";
				jsonObject.put("message", message);
				int status = 404;
				jsonObject.put("statuscode", status);
				return Response.status(200).type("application/json").entity(jsonObject.toString()).build();
			}
			return cache_Controller.DataController.retreiveMap(id);
		}
		else{
			return cache_Controller.DataController.retrieveAuroraJSON(query);
		}
		
	}
}