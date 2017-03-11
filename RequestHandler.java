import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Have functions for api module, will just "pass" the uri to the data controller
 * returns a json object.
 */
@Path ("/")
public class RequestHandler {
	
	@Path ("{URI}")
	@GET
	@Produces ("application/json")
	public Response handleRequest(@PathParam ("URI") String URI) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		//pass uri to data controller
		//jsonObject = DataController.foo(URI);
		//String result = jsonObject.toString();
		//return Response.status(200).entity(result).build();
		return DataController.foo(URI);
		
	}
}
