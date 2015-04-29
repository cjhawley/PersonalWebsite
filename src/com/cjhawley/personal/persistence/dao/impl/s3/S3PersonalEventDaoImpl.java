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
import com.amazonaws.services.s3.model.S3Object;
import com.cjhawley.personal.client.S3Client;
import com.cjhawley.personal.model.PersonalEvent;
import com.cjhawley.personal.persistence.dao.PersonalEventDao;
import com.fasterxml.jackson.databind.ObjectMapper;

public class S3PersonalEventDaoImpl implements PersonalEventDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(S3PersonalEventDaoImpl.class);

	private static final Cache CACHE;
	private static final String PERSONAL_EVENTS_CACHE_KEY = "PersonalEvents";

	// TODO - find a better way to keep the "object content" folder name
	private static final String PERSONAL_EVENTS_S3_FOLDER = "personal-events";
	
	// only need one json mapper, since we will be converting the same type of data.
	private final ObjectMapper MAPPER = new ObjectMapper();

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
		if ((element = CACHE.get(PERSONAL_EVENTS_CACHE_KEY)) != null) {
			return (List<PersonalEvent>) element.getObjectValue();
		} else {
			List<PersonalEvent> events = loadPersonalEventsFromS3();
			CACHE.put(new Element(PERSONAL_EVENTS_CACHE_KEY, events));
			return events;
		}
	}

	private List<PersonalEvent> loadPersonalEventsFromS3() {
		AmazonS3 client = S3Client.getInstance().getS3Client();
		ObjectListing listing = client.listObjects(S3Client.getRootBucketName(), PERSONAL_EVENTS_S3_FOLDER);

		List<String> dataKeys = listing.getObjectSummaries().stream()
				.filter(s -> s.getKey().endsWith("json")).map(s -> s.getKey())
				.collect(Collectors.toList());

		List<PersonalEvent> personalEvents = new ArrayList<PersonalEvent>();

		for (String key : dataKeys) {
			S3Object obj = null;
			try {
				obj = client.getObject(S3Client.getRootBucketName(), key);
			} catch (AmazonServiceException e) {
				LOGGER.error(String.format("Error with S3 Service while trying to retrieve data for '%s'", key), e);
			} catch (AmazonClientException e) {
				LOGGER.error(String.format("Error with S3 Client while trying to retrieve data for '%s'", key), e);
			} finally {
				if (obj == null) {
					continue;
				}
			}
			PersonalEvent event = convertS3ObjectToPersonalEvent(obj);
			if (event != null) {
				personalEvents.add(event);
			}
		}

		// Sort by descending date.
		Collections.sort(personalEvents, (p1, p2) -> p2.getDate().compareTo(p1.getDate()));

		return personalEvents;
	}

	/**
	 * Convert an S3Object with proper json formatting into a PersonalEvent object.
	 * @param obj S3Object with proper json formatting for a PersonalEvent object
	 * @return PersonalEvent object, or null if there was an exception, or null input.
	 */
	private PersonalEvent convertS3ObjectToPersonalEvent(S3Object obj) {
		PersonalEvent event = null;
		try {
			byte[] jsonBytes = IOUtils.toByteArray(obj.getObjectContent());
			event = MAPPER.readValue(jsonBytes, PersonalEvent.class);
		} catch (IOException e) {
			LOGGER.error("Error converting S3 object to PersonalEvent object.", e);
		}

		return event;
	}
}
