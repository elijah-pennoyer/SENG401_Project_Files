package cache_Controller;

import javax.ws.rs.core.Response;

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
 * Passes the received JSON object or image back to RequestHandler.
 */
public class DataController {
	
	public static long cacheHitCount;
	public static long cacheMissCount;
	
	public static HashMap<String, Object[]> cache;
	public static boolean cacheGoodToGo = false;
	
	public static Response retrieveAuroraImage(String URI, boolean doCaching){
		try {
			if(doCaching){
				//cache = new HashMap<String, Object[]>();
				if(cache.containsKey(URI)){
					Object[] temp = cache.get(URI);
					if((long)temp[1]<System.currentTimeMillis()){
						cacheHitCount++;
						return (Response)temp[0];
					}
				}
			}
			cacheMissCount++;
			Response rv = rest_Data_Retriever.AuroraDR.auroraAPI_ImageRetriever(URI);
			Object[] cached = new Object[2];
			cached[0] = rv;
			
			cached[1] = getCacheTime(URI, "images");
			System.out.println("retrieveAuroraImage cache time: " + cached[1]);
			
			cache.put(URI, cached);
			return rv;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Response retrieveAuroraJSON(String URI, boolean doCaching){
		try {
			if(doCaching){
				//cache = new HashMap<String, Object[]>();
				if(cache.containsKey(URI)){
					Object[] temp = cache.get(URI);
					if((long)temp[1]<System.currentTimeMillis()){
						cacheHitCount++;
						return (Response)temp[0];
					}
				}
			}
			cacheMissCount++;
			Response rv = rest_Data_Retriever.AuroraDR.auroraAPI_JSONRetriever(URI);
			Object[] cached = new Object[2];
			cached[0] = rv;
			
			cached[1] = getCacheTime(URI,"json");
			System.out.println("retrieveAuroraJSON cache time: " + cached[1]);
			
			cache.put(URI, cached);
			return rv;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Response retrieveMap(String Location, boolean doCaching){
		try {
			if(doCaching){
				//cache = new HashMap<String, Object[]>();
				if(cache.containsKey(Location)){
					Object[] temp = cache.get(Location);
					if((long)temp[1]<System.currentTimeMillis()){
						cacheHitCount++;
						
						((ByteArrayInputStream)((Response) temp[0]).getEntity()).reset();
						
						return (Response)temp[0];
					}
				}
			}
			cacheMissCount++;
			Response rv = rest_Data_Retriever.GoogleDR.googleAPIRetriever(Location);
			Object[] cached = new Object[2];
			cached[0] = rv;
			
			cached[1] = getCacheTime(Location, "map");
			System.out.println("retrieveMap cache time: " + cached[1]);
		
			cache.put(Location, cached);
			return rv;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Get the cache time for the specified URI. If the URI isn't specified, get the default for
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
				//TODO pick a proper default
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
		//TODO pick a proper default
		return System.currentTimeMillis() + 500;
	}
	
	
	/*
	public static void main(String args[]){
		//TODO credit this tutorial probably
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("config.properties");

			// set the properties value
			prop.setProperty("database", "localhost");
			prop.setProperty("dbuser", "mkyong");
			prop.setProperty("dbpassword", "password");
			prop.setProperty("stuff", "things");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	    prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("database"));

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
	}*/
}
