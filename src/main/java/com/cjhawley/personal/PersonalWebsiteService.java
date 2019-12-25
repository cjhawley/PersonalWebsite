package com.cjhawley.personal;

import com.cjhawley.personal.persistence.DiskPersonalEvents;
import com.cjhawley.personal.persistence.PersonalEventsLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.norberg.automatter.jackson.AutoMatterModule;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


import org.apache.velocity.tools.generic.DateTool;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import com.google.gson.Gson;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

/**
 * Implementation of the service
 */
public class PersonalWebsiteService {
	private static final String PERSONAL_EVENTS = "personal_events";
	private static final ObjectMapper MAPPER = new ObjectMapper()
			.registerModule(new AutoMatterModule());

	private final PersonalEventsLoader personalEvents;

	/**
	 * Constructor
	 * @param args We expect a single argument: the path to the personal events JSON file.
	 * @throws Exception Throw an exception if we cannot load and parse the personal events file
	 */
	public PersonalWebsiteService(String[] args) throws Exception  {
		File personalEventsFile = new File(args[0]);

		this.personalEvents = new DiskPersonalEvents(MAPPER, personalEventsFile);
	}

	public void init() {
		port(8080);
		staticFiles.location("/web");

		get("/ping", ((request, response) -> "pong"));
		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put(PERSONAL_EVENTS, personalEvents.getPersonalEvents());
			model.put("date", new DateTool());

			// The wm files are located under the resources directory
			return new ModelAndView(model, "/web/jsp/index.vm");
		}, new VelocityTemplateEngine());

		final Gson gson = new Gson();
		get("/personalevents", (request, response) -> {
			response.header("content-type", "application/json");
			return personalEvents.getPersonalEvents();
		}, gson::toJson);
	}
}
