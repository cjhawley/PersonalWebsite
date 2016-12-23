package com.cjhawley.personal;

import java.util.HashMap;
import java.util.Map;

import com.cjhawley.personal.persistence.PersonalEventDao;
import com.cjhawley.personal.persistence.S3PersonalEventDaoImpl;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
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

	private final CacheManager cacheManager;
	private final PersonalEventDao personalEventDao;

	public PersonalWebsiteService() {
		this.cacheManager = cacheManager();
		this.personalEventDao = new S3PersonalEventDaoImpl(cacheManager);
	}

	public void init() {
		port(8080);
		staticFiles.location("/web");

		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put(PERSONAL_EVENTS, personalEventDao.getPersonalEvents());
			model.put("date", new DateTool());

			// The wm files are located under the resources directory
			return new ModelAndView(model, "/web/jsp/index.vm");
		}, new VelocityTemplateEngine());

		Gson gson = new Gson();
		get("/personalevents", (request, response) -> {
			response.header("content-type", "application/json");
			return personalEventDao.getPersonalEvents();
		}, gson::toJson);
	}

	private CacheManager cacheManager() {

		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setName("cjhawley_cache");
		cacheConfiguration.maxEntriesLocalHeap(1000);
		cacheConfiguration.timeToIdleSeconds(60*60);
		cacheConfiguration.timeToLiveSeconds(60*60);
		cacheConfiguration.eternal(false);
		cacheConfiguration.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO);

		Cache cache = new Cache(cacheConfiguration);
		CacheManager cacheManager = CacheManager.create();
		cacheManager.addCache(cache);

		return cacheManager;
	}
}
