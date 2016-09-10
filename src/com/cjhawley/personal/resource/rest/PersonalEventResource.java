package com.cjhawley.personal.resource.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cjhawley.personal.model.PersonalEvent;
import com.cjhawley.personal.persistence.dao.PersonalEventDao;

/**
 * Resource for Personal Events.
 * 
 * @author cjhawley
 *
 */

@Path("/personalevent")
public class PersonalEventResource {
	private final PersonalEventDao personalEventDao;
	public PersonalEventResource(PersonalEventDao personalEventDao) {
		this.personalEventDao = personalEventDao;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PersonalEvent> personalEvents() {
		return personalEventDao.getPersonalEvents();
	}
}
