package cache_Controller;

import javax.ws.rs.core.Response;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Receives URI info from RequestHandler. Passes URI to AuroraDR or GoogleDR.
 * Receives JSON object or image (HTTP response) from AuroraDR, or a map image from Google DR.
 * Passes the recieved JSON object or image back to RequestHandler.
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
			Properties prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream("config.properties");
				
				// load a properties file
				prop.load(input);

				if(prop.getProperty(URI) != null && !prop.getProperty(URI).equals("")){
					cached[1] = System.currentTimeMillis() + Integer.getInteger(prop.getProperty(URI));
				}
				else if(prop.getProperty("images") != null && prop.getProperty("images").equals("")){
					cached[1] = System.currentTimeMillis() + Integer.getInteger(prop.getProperty("images"));
				}
				else{
					//TODO pick a proper default
					cached[1] = System.currentTimeMillis() + 500;
				}

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
			cache.put(URI, cached);
			return rv;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//@Produces("application/json")
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
			Properties prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream("config.properties");
				
				// load a properties file
				prop.load(input);

				if(prop.getProperty(URI) != null && !prop.getProperty(URI).equals("")){
					cached[1] = System.currentTimeMillis() + Integer.getInteger(prop.getProperty(URI));
				}
				else if(prop.getProperty("json") != null && prop.getProperty("json").equals("")){
					cached[1] = System.currentTimeMillis() + Integer.getInteger(prop.getProperty("json"));
				}
				else{
					//TODO pick a proper default
					cached[1] = System.currentTimeMillis() + 500;
				}

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
						return (Response)temp[0];
					}
				}
			}
			cacheMissCount++;
			Response rv = rest_Data_Retriever.GoogleDR.googleAPIRetriever(Location);
			Object[] cached = new Object[2];
			cached[0] = rv;
			Properties prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream("config.properties");
				
				// load a properties file
				prop.load(input);

				if(prop.getProperty(Location) != null && !prop.getProperty(Location).equals("")){
					cached[1] = System.currentTimeMillis() + Integer.getInteger(prop.getProperty(Location));
				}
				else if(prop.getProperty("map") != null && prop.getProperty("map").equals("")){
					cached[1] = System.currentTimeMillis() + Integer.getInteger(prop.getProperty("map"));
				}
				else{
					//TODO pick a proper default
					cached[1] = System.currentTimeMillis() + 500;
				}

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
			cache.put(Location, cached);
			return rv;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
