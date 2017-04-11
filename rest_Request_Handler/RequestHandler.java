package rest_Request_Handler;

import java.util.HashMap;
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
		
		//if the cache has not yet been created
		//this statement block will run exactly once on server startup
		if(!cache_Controller.DataController.cacheGoodToGo){
			//create a new HashMap to be used as the cache
			cache_Controller.DataController.cache = new HashMap<String, Object[]>();
			cache_Controller.DataController.cacheGoodToGo = true;
		}
		
		//Retrieve the entire URI query (ex. type=XX&action=YYY&ZZZ=AAA)
		String query = uriInfo.getRequestUri().getQuery();
		
		//parse the URI into its component query parameters (ex. type=XX)
		MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
		//Retrieve all parameters corresponding to "type"
		List<String> typeList = queryParameters.get("type");
		//error check the user has entered a type parameter
		if(typeList.isEmpty() || typeList == null){
			//return error message to the user detailing that they have not entered any "type" parameters
			JSONObject jsonObject = new JSONObject();
			String module = "Main";
			jsonObject.put("module", module);
			String message = "Parameter is missing from request: type";
			jsonObject.put("message", message);
			int status = 404;
			jsonObject.put("statuscode", status);
			return Response.status(200).type("application/json").entity(jsonObject.toString()).build();
		}
		//Retrieve the last type parameter input by the user
		String type = typeList.get(typeList.size()-1);
		
		//if the return type is expected to be an image
		if(type.equals("embed") || (type.equals("images") && !queryParameters.containsKey("action"))){
			//if the user has specified a no caching parameter
			if(queryParameters.containsKey("no-caching")){
				//Retrieve the last no-caching parameter specified by the user
				List<String> noCacheList = queryParameters.get("no-caching");
				String noCache = null;
				if(noCacheList != null && !noCacheList.isEmpty()){
					noCache = noCacheList.get(noCacheList.size()-1);
					
					//if the retrieved parameter specifies "true"
					//Call DataController.retrieveAuroraImage to retrieve the data by making a request to an API.
					if(noCache.equals("true")){
						return cache_Controller.DataController.retrieveAuroraImage(query, false);
					}
				}
			}
			//if no "no-caching" parameter was given by the user or "no-caching" was not specified as "true"
			//Call DataController.retrieveAuroraImage to retrieve the data, either from the cache 
			//or by making a request to an API if the data isn't in the cache.
			return cache_Controller.DataController.retrieveAuroraImage(query, true);	
		}
		
		//if the expected return type is a map image
		else if(type.equals("map")){
			//get the last "id" query parameter entered by the user
			List<String> idList = queryParameters.get("id");
			String id = null;
			if(idList != null && !idList.isEmpty()){
				id = idList.get(idList.size()-1);
			}
			//if no "id" parameter was specified
			else{
				//return error message to the user detailing that they have not entered any "id" parameters
				JSONObject jsonObject = new JSONObject();
				String module = "Map";
				jsonObject.put("module", module);
				String message = "Parameter is missing from request: id";
				jsonObject.put("message", message);
				int status = 404;
				jsonObject.put("statuscode", status);
				return Response.status(200).type("application/json").entity(jsonObject.toString()).build();
			}
			//if the user specified a "no-caching" parameter
			if(queryParameters.containsKey("no-caching")){
				//get the last "no-caching" parameter entered by the user
				List<String> cacheList = queryParameters.get("no-caching");
				String cache = null;
				if(cacheList != null && !cacheList.isEmpty()){
					cache = cacheList.get(cacheList.size()-1);
					
					//if the retrieved parameter has been specified as "true"
					//Call DataController.retrieveMap to retrieve the data by making a request to an API.
					if(cache.equals("true")){
						return cache_Controller.DataController.retrieveMap(id, false);
					}
				}
			}
			//if no "no-caching" parameter was given by the user or "no-caching" was not specified as "true"
			//Call DataController.retrieveMap to retrieve the data, either from the cache 
			//or by making a request to an API if the data isn't in the cache.
			return cache_Controller.DataController.retrieveMap(id, true);
		}
		
		//if the expected return type is cache information
		else if(type.equals("cacheInfo")){
			return cache_Controller.DataController.getCacheInfo();
		}
		
		//if the expected return type is a JSON object
		else{
			//if the user specified a "no-caching" parameter
			if(queryParameters.containsKey("no-caching")){
				//get the last "no-caching" parameter entered by the user
				List<String> cacheList = queryParameters.get("no-caching");
				String cache = null;
				if(cacheList != null && !cacheList.isEmpty()){
					cache = cacheList.get(cacheList.size()-1);
					
					//if the retrieved parameter has been specified as "true"
					//Call DataController.retrieveAuroraJSON to retrieve the data by making a request to an API.
					if(cache.equals("true")){
						return cache_Controller.DataController.retrieveAuroraJSON(query, false);
					}
				}
			}
			//if no "no-caching" parameter was given by the user or "no-caching" was not specified as "true"
			//Call DataController.retrieveAuroraJSON to retrieve the data, either from the cache 
			//or by making a request to an API if the data isn't in the cache.
			return cache_Controller.DataController.retrieveAuroraJSON(query, true);
		}
		
	}
}
