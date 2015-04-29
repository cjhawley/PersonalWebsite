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
	private static final String PERSONAL_EVENTS = "personal_events";
	
	@GET
	public Viewable mainView() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		// personal events are cached, so this is safe.
		model.put(PERSONAL_EVENTS, PersonalEventResource.getInstance().personalEvents());
		
		return new Viewable("/index.jsp", model);
	}
}
