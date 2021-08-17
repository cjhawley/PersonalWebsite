package com.cjhawley.personal;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;


import com.cjhawley.personal.persistence.DiskPersonalEvents;
import com.cjhawley.personal.persistence.PersonalEventsLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import io.norberg.automatter.jackson.AutoMatterModule;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

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
   *
   * @param args We expect a single argument: the path to the personal events JSON file.
   * @throws Exception Throw an exception if we cannot load and parse the personal events file
   */
  public PersonalWebsiteService(String[] args) throws Exception {
    final File personalEventsFile = new File(args[0]);
    this.personalEvents = new DiskPersonalEvents(MAPPER, personalEventsFile);
  }

  public void init() {

    final Configuration freemarkerConfiguration = new Configuration(Configuration.getVersion());
    freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/web/jsp");

    port(8080);
    staticFiles.location("/web");

    get("/ping", ((request, response) -> "pong"));
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<>();
      model.put(PERSONAL_EVENTS, personalEvents.getPersonalEvents());
      // The wm files are located under the resources directory
      return new ModelAndView(model, "index.ftl");
    }, new FreeMarkerEngine(freemarkerConfiguration));

    final Gson gson = new Gson();
    get("/personalevents", (request, response) -> {
      response.header("content-type", "application/json");
      return personalEvents.getPersonalEvents();
    }, gson::toJson);
  }
}
