package com.cjhawley.personal;

import java.util.HashMap;
import java.util.Map;

import com.cjhawley.personal.persistence.S3PersonalEvents;

import org.apache.velocity.tools.generic.DateTool;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import com.google.gson.Gson;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;


/**
 * Servlet implementation class PersonalWebsiteController
 */
public class PersonalWebsiteService {
	private static final String PERSONAL_EVENTS = "personal_events";

	private final S3PersonalEvents personalEvents;

	public PersonalWebsiteService() {
		this.personalEvents = new S3PersonalEvents();
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

		Gson gson = new Gson();
		get("/personalevents", (request, response) -> {
			response.header("content-type", "application/json");
			return personalEvents.getPersonalEvents();
		}, gson::toJson);
	}
}
