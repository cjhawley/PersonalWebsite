package com.cjhawley.personal.resource.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cjhawley.personal.model.PersonalEvent;
import com.cjhawley.personal.persistence.manager.PersistenceManager;
import com.cjhawley.personal.persistence.manager.s3.S3PersistenceManager;

/**
 * Resource for Personal Events.
 * 
 * @author cjhawley
 *
 */

@Path("/personalevent")
public class PersonalEventResource {
	
	private static final PersonalEventResource INSTANCE = new PersonalEventResource();
	public static PersonalEventResource getInstance() {
		return INSTANCE;
	} 
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PersonalEvent> personalEvents() {
		/**
		 * Right now, there is no DB, so work with a local persistence manager.
		 */
		PersistenceManager persistenceManager = S3PersistenceManager.getInstance();
		return persistenceManager.getPersonalEventDao().getPersonalEvents();
	}
}
