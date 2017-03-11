import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Have functions for api module, will just "pass" the uri to the data controller
 * returns a json object.
 */
@Path ("/")
public class RequestHandler {
	
	@Path ("")
	@GET
	@Produces ("application/json")
	public Response handleRequest(@Context UriInfo uriInfo) throws JSONException{
		
		MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
		List<String> typeList = queryParameters.get("type");
		String type = typeList.get(typeList.size()-1);
		
		String parameters = "?type=" + type + "&";
		if(type.equals("all")){

		}
		else if(type.equals("ace")){
			List<String> dataList = queryParameters.get("data");
			List<String> latList = queryParameters.get("lat");
			List<String> longList = queryParameters.get("long");
			if(dataList != null && !dataList.isEmpty()){
				parameters += "data=";
				parameters += dataList.get(dataList.size()-1);
				parameters += "&";
			}
			if(latList != null && !latList.isEmpty()){
				parameters += "lat=";
				parameters += latList.get(latList.size()-1);
				parameters += "&";
			}
			if(longList != null && !longList.isEmpty()){
				parameters += "long=";
				parameters += longList.get(longList.size()-1);
			}
		}
		else if(type.equals("archive")){
			
		}
		else if(type.equals("embed")){
			
		}
		else if(type.equals("images")){
			
		}
		else if(type.equals("locations")){
			
		}
		else if(type.equals("weather")){
			
		}
		else if(type.equals("map")){
			
		}
		return DataController.foo(parameters);
	}
	

}
