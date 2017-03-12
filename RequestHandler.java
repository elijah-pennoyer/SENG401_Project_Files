import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;


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
		
		//Create the parameters String, which will be used to get data from the Auroras.live API
		String parameters = "?type=" + type + "&";
		
		//Call function to update the parameters string based on the selected type. 
		if(type.equals("all")){
			parameters = allRequestHandler(queryParameters, parameters);
		}
		else if(type.equals("ace")){
			parameters = aceRequestHandler(queryParameters, parameters);
		}
		else if(type.equals("archive")){
			parameters = archiveRequestHandler(queryParameters, parameters);
		}
		else if(type.equals("embed")){
			parameters = embedRequestHandler(queryParameters, parameters);
		}
		else if(type.equals("images")){
<<<<<<< HEAD
			//Get the lists of input parameters
			List<String> actionList = queryParameters.get("action");
			List<String> imageList = queryParameters.get("image");
			
			//If the actionList is not null or empty:
			if(actionList != null && !actionList.isEmpty()){
				//Add action=[parameter] to the parameters string
				parameters += "action=";
				parameters += actionList.get(actionList.size()-1);
				parameters += "&";
			}
			//If the imageList is not null or empty
			if(imageList != null && !imageList.isEmpty()){
				//Add image=[parameter] to the parameters string
				parameters += "image=";
				parameters += imageList.get(imageList.size()-1);
			}
			try {
				return AuroraDR.foo(parameters);
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
=======
			parameters = imagesRequestHandler(queryParameters, parameters);
>>>>>>> refs/remotes/origin/master
		}
		else if(type.equals("weather")){
			parameters = weatherRequestHandler(queryParameters, parameters);
		}
		else if(type.equals("map")){
			List<String> idList = queryParameters.get("id");
			if(idList != null && !idList.isEmpty()){
				parameters += "id=";
				parameters += idList.get(idList.size()-1);
			}
			else{
				//return a json object explaining to the user that it is NOT ok to not provide a location id
				JSONObject jsonObject = new JSONObject();
				String att = "Powered by Auroras.live";
				jsonObject.put("Attribution", att);
				return Response.status(200).entity(jsonObject.toString()).build();
			}
		}
		
		//Call DataController.foo to retrieve the data, either from the Database 
		//or by making a request to an API if the data isn't in the Database.
		return DataController.foo(parameters);
	}
	
	/**
	 * Creates an all request string to be sent to the Auroras.live API
	 * @param queryParameters A hashmap containing parameters input by the user
	 * @param parameters the string to add parameters too
	 * @return the updated parameters string
	 */
	private String allRequestHandler(MultivaluedMap<String, String> queryParameters, String parameters){
		
		//Get the lists of input parameters, in case there are multiple instances of a parameter.
		List<String> latList = queryParameters.get("lat");
		List<String> longList = queryParameters.get("long");
		List<String> aceList = queryParameters.get("ace");
		List<String> archiveList = queryParameters.get("archive");
		List<String> forecastList = queryParameters.get("forecast");
		List<String> imagesList = queryParameters.get("images");
		List<String> probabilityList = queryParameters.get("probability");
		List<String> threedayList = queryParameters.get("threeday");
		List<String> twentysevendayList = queryParameters.get("twentysevenday");
		List<String> weatherList = queryParameters.get("weather");
		
		//Check to see if each variable of the all API is present. If a variable is present, 
		//add the last instance of it in the query list to the string.
		
		if(latList != null && !latList.isEmpty()){
			parameters += "lat=";
			parameters += latList.get(latList.size()-1);
			parameters += "&";
		}
		if(longList != null && !longList.isEmpty()){
			parameters += "long=";
			parameters += longList.get(longList.size()-1);
			parameters += "&";
		}
		if(aceList != null && !aceList.isEmpty()){
			parameters += "ace=";
			parameters += aceList.get(aceList.size()-1);
			parameters += "&";
		}
		if(archiveList != null && !archiveList.isEmpty()){
			parameters += "archive=";
			parameters += archiveList.get(archiveList.size()-1);
			parameters += "&";
		}
		if(forecastList != null && !forecastList.isEmpty()){
			parameters += "forecast=";
			parameters += forecastList.get(forecastList.size()-1);
			parameters += "&";
		}
		if(imagesList != null && !imagesList.isEmpty()){
			parameters += "images=";
			parameters += imagesList.get(imagesList.size()-1);
			parameters += "&";
		}
		if(probabilityList != null && !probabilityList.isEmpty()){
			parameters += "probability=";
			parameters += probabilityList.get(probabilityList.size()-1);
			parameters += "&";
		}
		if(threedayList != null && !threedayList.isEmpty()){
			parameters += "threeday=";
			parameters += threedayList.get(threedayList.size()-1);
			parameters += "&";
		}
		if(twentysevendayList != null && !twentysevendayList.isEmpty()){
			parameters += "twentysevenday=";
			parameters += twentysevendayList.get(twentysevendayList.size()-1);
			parameters += "&";
		}
		if(weatherList != null && !weatherList.isEmpty()){
			parameters += "weather=";
			parameters += weatherList.get(weatherList.size()-1);
			parameters += "&";
		}
		return parameters;
	}
	
	/**
	 * Creates an ace request string to be sent to the Auroras.live API
	 * @param queryParameters A hashmap containing parameters input by the user
	 * @param parameters the string to add parameters too
	 * @return the updated parameters string
	 */
	private String aceRequestHandler(MultivaluedMap<String, String> queryParameters, String parameters){
		
		//Get the lists of input parameters, in case there are multiple instances of a parameter.
		List<String> dataList = queryParameters.get("data");
		List<String> latList = queryParameters.get("lat");
		List<String> longList = queryParameters.get("long");
		
		
		//Check to see if each variable of the ace API is present. If a variable is present, 
		//add the last instance of it in the query list to the string.
		
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
		return parameters;
	}
	
	/**
	 * Creates an archive request string to be sent to the Auroras.live API
	 * @param queryParameters A hashmap containing parameters input by the user
	 * @param parameters the string to add parameters too
	 * @return the updated parameters string
	 */
	private String archiveRequestHandler(MultivaluedMap<String, String> queryParameters, String parameters){
		//Get the lists of input parameters, in case there are multiple instances of a parameter.
		List<String> actionList = queryParameters.get("action");
		List<String> startList = queryParameters.get("start");
		List<String> endList = queryParameters.get("end");
		
		//Check to see if each variable of the ace API is present. If a variable is present, 
		//add the last instance of it in the query list to the string.
		if(actionList != null && !actionList.isEmpty()){
			parameters += "action=";
			parameters += actionList.get(actionList.size()-1);
			parameters += "&";
		}
		if(startList != null && !startList.isEmpty()){
			parameters += "start=";
			parameters += startList.get(startList.size()-1);
			parameters += "&";
		}
		if(endList != null && !endList.isEmpty()){
			parameters += "end=";
			parameters += endList.get(endList.size()-1);
		}
		
		return parameters;
	}

	/**
	 * Creates an embed request string to be sent to the Auroras.live API
	 * @param queryParameters A hashmap containing parameters input by the user
	 * @param parameters the string to add parameters too
	 * @return the updated parameters string
	 */
	private String embedRequestHandler(MultivaluedMap<String, String> queryParameters, String parameters){
		//Get the lists of input parameters, in case there are multiple instances of a parameter.
		List<String> imageList = queryParameters.get("image");
		List<String> latList = queryParameters.get("lat");
		List<String> longList = queryParameters.get("long");
		
		//Check to see if each variable of the embed API is present. If a variable is present, 
		//add the last instance of it in the query list to the string.
		
		if(imageList != null && !imageList.isEmpty()){
			parameters += "image=";
			parameters += imageList.get(imageList.size()-1);
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
		
		return parameters;
	}
	
	/**
	 * Creates an images request string to be sent to the Auroras.live API
	 * @param queryParameters A hashmap containing parameters input by the user
	 * @param parameters the string to add parameters too
	 * @return the updated parameters string
	 */
	private String imagesRequestHandler(MultivaluedMap<String, String> queryParameters, String parameters){
		//Get the lists of input parameters, in case there are multiple instances of a parameter.
		List<String> actionList = queryParameters.get("action");
		List<String> imageList = queryParameters.get("image");
		
		//Check to see if each variable of the images API is present. If a variable is present, 
		//add the last instance of it in the query list to the string.
		
		if(actionList != null && !actionList.isEmpty()){
			parameters += "action=";
			parameters += actionList.get(actionList.size()-1);
			parameters += "&";
		}
		if(imageList != null && !imageList.isEmpty()){
			parameters += "image=";
			parameters += imageList.get(imageList.size()-1);
		}
		
		return parameters;
	}
	
	/**
	 * Creates a weather request string to be sent to the Auroras.live API
	 * @param queryParameters A hashmap containing parameters input by the user
	 * @param parameters the string to add parameters too
	 * @return the updated parameters string
	 */
	private String weatherRequestHandler(MultivaluedMap<String, String> queryParameters, String parameters){
		//Get the lists of input parameters, in case there are multiple instances of a parameter.
		List<String> latList = queryParameters.get("lat");
		List<String> longList = queryParameters.get("long");
		List<String> forecastList = queryParameters.get("forecast");
		
		//Check to see if each variable of the weather API is present. If a variable is present, 
		//add the last instance of it in the query list to the string.
		
		if(latList != null && !latList.isEmpty()){
			parameters += "lat=";
			parameters += latList.get(latList.size()-1);
			parameters += "&";
		}
		if(longList != null && !longList.isEmpty()){
			parameters += "long=";
			parameters += longList.get(longList.size()-1);
		}
		if(forecastList != null && !forecastList.isEmpty()){
			parameters += "image=";
			parameters += forecastList.get(forecastList.size()-1);
			parameters += "&";
		}
		
		return parameters;
	}	
}
