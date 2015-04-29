package com.cjhawley.personal.persistence.dao.impl.s3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.cjhawley.personal.client.S3Client;
import com.cjhawley.personal.model.PersonalEvent;
import com.cjhawley.personal.persistence.dao.PersonalEventDao;
import com.fasterxml.jackson.databind.ObjectMapper;

public class S3PersonalEventDaoImpl implements PersonalEventDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(S3PersonalEventDaoImpl.class);
	private static final Cache CACHE;
	private static final String PERSONAL_EVENTS_KEY = "personalEvents";
	
	static {
		CacheManager.getInstance().addCache(S3PersonalEventDaoImpl.class.getSimpleName());
		CACHE = CacheManager.getInstance().getCache(S3PersonalEventDaoImpl.class.getSimpleName());
	}
	
	private static final PersonalEventDao INSTANCE = new S3PersonalEventDaoImpl();
	
	public static PersonalEventDao getInstance() {
		return INSTANCE;
	}
	
	private S3PersonalEventDaoImpl() {}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalEvent> getPersonalEvents() {
		Element element;
		if ((element = CACHE.get(PERSONAL_EVENTS_KEY)) != null) {
			return (List<PersonalEvent>)element.getObjectValue();
		} else {
			List<PersonalEvent> events = loadPersonalEventsFromS3();
			CACHE.put(new Element(PERSONAL_EVENTS_KEY, events));
			return events;
		}
	}
	
	private List<PersonalEvent> loadPersonalEventsFromS3() {
		AmazonS3 client = S3Client.getInstance().getS3Client();
		String bucketName = "personal-site-cjhawley-1";
		ObjectListing listing = client.listObjects(bucketName, "personal-events");

		/*
		 * List<String> schemaKeys = listing.getObjectSummaries().stream()
		 * .filter(s -> s.getKey().endsWith("schema")) .map(s ->
		 * s.getKey()).collect(Collectors.toList());
		 */

		List<String> dataKeys = listing.getObjectSummaries().stream()
				.filter(s -> s.getKey().endsWith("json")).map(s -> s.getKey())
				.collect(Collectors.toList());

		/*
		 * JSONObject schema;
		 * 
		 * for (String key : schemaKeys) { schema = new JSONObject(new
		 * String(IOUtils.toByteArray(client.getObject(bucket,
		 * key).getObjectContent()))); }
		 */

		ObjectMapper mapper = new ObjectMapper();
		List<PersonalEvent> personalEvents = new ArrayList<PersonalEvent>();

		try {
			for (String key : dataKeys) {
				byte[] jsonBytes = IOUtils.toByteArray(client.getObject(bucketName, key).getObjectContent());
				PersonalEvent event = mapper.readValue(jsonBytes, PersonalEvent.class);
				personalEvents.add(event);
			}
		} catch (AmazonServiceException ase) {
			LOGGER.error("Error with Amazon S3.", ase);
		} catch (AmazonClientException ace) {
			LOGGER.error("Error with Amazon S3 Client.", ace);
		} catch (IOException ioe) {
			LOGGER.error("Unexpected error (probably with converting data from S3 to JSON", ioe);
		}

		Collections.sort(personalEvents, (p1, p2) -> p2.getDate().compareTo(p1.getDate()));
		
		return personalEvents;
	}

}
