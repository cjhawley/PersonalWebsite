package com.cjhawley.personal.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.mvc.Viewable;

import com.cjhawley.personal.resource.rest.PersonalEventResource;

/**
 * Servlet implementation class PersonalWebsiteController
 */
@Path("/")
public class PersonalWebsiteMainResource {
	
	private static Map<String, Object> model = new HashMap<String, Object>();
	static {
		model.put("personal_events", PersonalEventResource.getInstance().personalEvents());
	}
	
	@GET
	public Viewable mainView() {
		return new Viewable("/index.jsp", model);
	}
}
