package com.cjhawley.personal.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;

/**
 * Servlet implementation class PersonalWebsiteController
 */
@Path("/")
public class PersonalWebsiteController {
	
	@GET
	public Viewable mainView() {
		return new Viewable("/index.jsp");
	}

}
