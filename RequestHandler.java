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
		
		String parameters = "?type=" + type + "&";
		if(type.equals("all")){
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
			List<String> actionList = queryParameters.get("action");
			List<String> startList = queryParameters.get("start");
			List<String> endList = queryParameters.get("end");
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
		}
		else if(type.equals("embed")){
			List<String> imageList = queryParameters.get("image");
			List<String> latList = queryParameters.get("lat");
			List<String> longList = queryParameters.get("long");
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
		}
		else if(type.equals("images")){
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
		}
		else if(type.equals("weather")){
			List<String> latList = queryParameters.get("lat");
			List<String> longList = queryParameters.get("long");
			List<String> forecastList = queryParameters.get("forecast");
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
		return DataController.foo(parameters);
	}
	

}
