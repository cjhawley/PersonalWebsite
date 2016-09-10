package com.cjhawley.personal.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.cjhawley.personal.persistence.dao.PersonalEventDao;
import com.cjhawley.personal.persistence.dao.S3PersonalEventDaoImpl;
import net.sf.ehcache.CacheManager;
import org.glassfish.jersey.server.mvc.Viewable;

import com.cjhawley.personal.resource.rest.PersonalEventResource;

/**
 * Servlet implementation class PersonalWebsiteController
 */
@Path("/")
public class PersonalWebsiteMainResource {
	private static final String PERSONAL_EVENTS = "personal_events";

	private final PersonalEventResource personalEventResource;

	public PersonalWebsiteMainResource() {
		PersonalEventDao personalEventDao = new S3PersonalEventDaoImpl(CacheManager.getInstance());
		this.personalEventResource = new PersonalEventResource(personalEventDao);
	}
	
	@GET
	public Viewable mainView() {
		Map<String, Object> model = new HashMap<>();
		
		// personal events are cached, so this is safe.
		model.put(PERSONAL_EVENTS, personalEventResource.personalEvents());
		
		return new Viewable("/index.jsp", model);
	}
}
