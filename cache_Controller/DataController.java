package cache_Controller;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Receives URI info from RequestHandler. Passes URI to AuroraDR or GoogleDR.
 * Receives JSON object or image (HTTP response) from AuroraDR, or a map image from Google DR.
 * Returns the received JSON object or image back to RequestHandler.
 */
public class DataController {
	
	//integers to track the number of cache hits (successful cache retreivals) and misses (unsuccessful cache retreivals)
	private static long cacheHitCount;
	private static long cacheMissCount;
	
	//HashMap to serve as the system cache
	//to clear the cache and reset the hit/miss counters the server must be restarted by an admin user
	public static HashMap<String, Object[]> cache;
	//boolean to indicate whether the cache has been created
	public static boolean cacheGoodToGo = false;
	
	/**
	 * Retreives a JPEG image from Auroras.live or the cache
	 * @param URI URI of the requested image
	 * @param doCaching Boolean to indicate whether or not the image should be cached/retreived from the cache
	 * @return Requested Auroras.live image
	 */
	public static Response retrieveAuroraImage(String URI, boolean doCaching){
		try {
			//if caching is enabled
			if(doCaching){
				//if the image corresponding to the input URI is stored in the cache
				if(cache.containsKey(URI)){
					Object[] temp = cache.get(URI);
					//if the expirery time of the object stored in the cache has not yet passed
					if((long)temp[1]>System.currentTimeMillis()){
						//successful cache retreival
						cacheHitCount++;
						//if the cached object has a status code of 200
						if(((Response)temp[0]).getStatus()==200){
							//reset the image's ByteArrayInputStream so it can be read again
							((ByteArrayInputStream)((Response) temp[0]).getEntity()).reset();
						}
						//retrun the cached object
						return (Response)temp[0];
					}
				}
			}
			//if caching is not enabled or the object is not found in the cache or the cached object is expired
			//unsuccessful cache retreival
			cacheMissCount++;
			//retreive the image from Auroras.live
			Response rv = rest_Data_Retriever.AuroraDR.auroraAPI_ImageRetriever(URI);
			
			//prepare to cache the image
			//create an array to hold the image at its expirery time
			Object[] cached = new Object[2];
			cached[0] = rv;
			
			//retreive the appropriate cache time from the system config file
			cached[1] = getCacheTime(URI, "images");
			
			//cache the array
			cache.put(URI, cached);
			//return the image
			return rv;
			
		} catch (UnirestException e) {
			//ignore any exceptions thrown
			//return error message to the user detailing that they have not enetered any "id" parameters
			JSONObject jsonObject = new JSONObject();
			String module = "Main";
			jsonObject.put("module", module);
			String message = "Error in fulfilling request. Please try again.";
			jsonObject.put("message", message);
			int status = 500;
			jsonObject.put("statuscode", status);
			return Response.status(200).type("application/json").entity(jsonObject.toString()).build();
		}
	}
	
	public static Response retrieveAuroraJSON(String URI, boolean doCaching){
		try {
			//if caching is enabled
			if(doCaching){
				//if the JSON corresponding to the input URI is stored in the cache
				if(cache.containsKey(URI)){
					Object[] temp = cache.get(URI);
					//if the expirery time of the object stored in the cache has not yet passed
					if((long)temp[1]>System.currentTimeMillis()){
						//successful cache retreival, retrun the cached object
						cacheHitCount++;
						return (Response)temp[0];
					}
				}
			}
			//if caching is not enabled or the object is not found in the cache or the cached object is expired
			//unsuccessful cache retreival
			cacheMissCount++;
			//retreive the JSON from Auroras.live
			Response rv = rest_Data_Retriever.AuroraDR.auroraAPI_JSONRetriever(URI);
			
			//prepare to cache the JSON
			//create an array to hold the JSON at its expirery time
			Object[] cached = new Object[2];
			cached[0] = rv;
			
			//retreive the appropriate cache time from the system config file
			cached[1] = getCacheTime(URI,"json");
			
			//cache the array
			cache.put(URI, cached);
			//return the JSON
			return rv;
			
		} catch (UnirestException e) {
			//ignore any exceptions thrown
			//return error message to the user detailing that they have not enetered any "id" parameters
			JSONObject jsonObject = new JSONObject();
			String module = "Main";
			jsonObject.put("module", module);
			String message = "Error in fulfilling request. Please try again.";
			jsonObject.put("message", message);
			int status = 500;
			jsonObject.put("statuscode", status);
			return Response.status(200).type("application/json").entity(jsonObject.toString()).build();
		}
	}
	
	public static Response retrieveMap(String Location, boolean doCaching){
		try {
			//if caching is enabled
			if(doCaching){
				//if the map image corresponding to the input Location ID is stored in the cache
				if(cache.containsKey(Location)){
					Object[] temp = cache.get(Location);
					//if the expirery time of the object stored in the cache has not yet passed
					if((long)temp[1]>System.currentTimeMillis()){
						//successful cache retreival
						cacheHitCount++;
						//if the cached object has a status code of 200
						if(((Response)temp[0]).getStatus()==200){
							//reset the map image's ByteArrayInputStream so it can be read again
							((ByteArrayInputStream)((Response) temp[0]).getEntity()).reset();
						}
						//retrun the cached object
						return (Response)temp[0];
					}
				}
			}
			//if caching is not enabled or the object is not found in the cache or the cached object is expired
			//unsuccessful cache retreival
			cacheMissCount++;
			//retreive the map image from Google
			Response rv = rest_Data_Retriever.GoogleDR.googleAPIRetriever(Location);
			
			//prepare to cache the map image
			//create an array to hold the map image at its expirery time
			Object[] cached = new Object[2];
			cached[0] = rv;
			
			//retreive the appropriate cache time from the system config file
			cached[1] = getCacheTime(Location, "map");
		
			//cache the array
			cache.put(Location, cached);
			//return the map image
			return rv;
			
		} catch (UnirestException e) {
			//ignore any exceptions thrown
			//return error message to the user detailing that they have not enetered any "id" parameters
			JSONObject jsonObject = new JSONObject();
			String module = "Main";
			jsonObject.put("module", module);
			String message = "Error in fulfilling request. Please try again.";
			jsonObject.put("message", message);
			int status = 500;
			jsonObject.put("statuscode", status);
			return Response.status(200).type("application/json").entity(jsonObject.toString()).build();
		}
	}
	
	
	/**
	 * Get the cache time for the specified URI. If the URI isn't specified, get the default for the given object type
	 * @param URI The URI that will be sent to Auroras.live or Google Maps API for object retrieval
	 * @param objectType Either "json", "images" or "map"
	 * @return The amount of time the object in the URI should be cached for
	 */
	private static Long getCacheTime(String URI, String objectType){
		Properties prop = new Properties();
		InputStream input = null;
		try {
			//Access input stream
			input = new FileInputStream("config.properties");
			
			//Load the properties file
			prop.load(input);
			
			//If the URI is special and has a specific cache time set by an admin, 
			//return that value
			if(prop.getProperty(URI) != null && !prop.getProperty(URI).equals("")){
				return System.currentTimeMillis() + Integer.parseInt(prop.getProperty(URI));
			}
			//Else, find the objectType's cache time and return that value
			else if(prop.getProperty(objectType) != null && !prop.getProperty(objectType).equals("")){
				return System.currentTimeMillis() + Integer.parseInt(prop.getProperty(objectType));
			}
			//If neither the URI or the objectType are in the config file, return a default cache time
			else{
				return System.currentTimeMillis() + 500;
			}

		//Exception handling and closing the input stream
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//Return default
		return System.currentTimeMillis() + 500;
	}
	
	public static Response getCacheInfo(){
		//return error message to the user detailing that they have not enetered any "id" parameters
		String message = "Number of cache hits since last cache clear: " + cacheHitCount + ", Number of cache misses since last cache clear: " + cacheMissCount;
		return Response.status(200).type("application/json").entity(message).build();
	}
}
