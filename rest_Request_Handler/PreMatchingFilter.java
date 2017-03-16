package rest_Request_Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * Modifies incoming request headers to correctly accept 
 * image/jpeg or application/json depending on the type of request
 *
 */
@Provider
@PreMatching
public class PreMatchingFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		UriInfo uriInfo = requestContext.getUriInfo();
		
		MultivaluedMap<String, String> headers = requestContext.getHeaders();	
		System.out.println(headers);
		MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();	
		
		List<String> typeList = queryParameters.get("type");
		String type = typeList.get(typeList.size()-1);
		
		//TODO: check the logic here. 
		//It currently sets the request header to ask for an image if type is embed or images. Otherwise sets to ask for json.
		//It seems like Aurora images api always returns list if action=list is anywhere in query params.
		if(type.equals("embed") || (type.equals("images") && !queryParameters.containsKey("action"))){
			
			//make request header say that it accepts image/jpeg
			//(if an image is not available, return json)
			ArrayList<String> acceptTypes = new ArrayList<String>();
			acceptTypes.add("image/jpeg");
			acceptTypes.add("application/json");
			
			headers.put("Accept", acceptTypes);
			System.out.println(headers);
		}
		else{
			
			//make request header say that it accepts application/json
			ArrayList<String> acceptTypes = new ArrayList<String>();
			acceptTypes.add("application/json");
			
			headers.put("Accept", acceptTypes);
		}
		
	}
	
}
