package com.cjhawley.personal.persistence.dao;

import java.util.List;
import java.util.stream.Collectors;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.cjhawley.personal.client.S3Client;
import com.cjhawley.personal.model.PersonalEvent;
import com.cjhawley.personal.model.converter.S3ToModelConverter;

/**
 * Implement our DAO using S3
 * @author cjhawley
 *
 */
public class S3PersonalEventDaoImpl implements PersonalEventDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(S3PersonalEventDaoImpl.class);

	private final Ehcache ehcache;
	private static final String PERSONAL_EVENTS_CACHE_KEY = "PersonalEvents";

	// TODO - find a better way to keep the "object content" folder name
	private static final String PERSONAL_EVENTS_S3_FOLDER = "personal-events";

	public S3PersonalEventDaoImpl(CacheManager cacheManager) {
		this.ehcache = cacheManager.addCacheIfAbsent(S3PersonalEventDaoImpl.class.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalEvent> getPersonalEvents() {
		Element element;
		if ((element = ehcache.get(PERSONAL_EVENTS_CACHE_KEY)) != null) {
			LOGGER.debug("Retrieving element {} from cache", element);
			return (List<PersonalEvent>) element.getObjectValue();
		} else {
			List<PersonalEvent> events = loadPersonalEventsFromS3();
			ehcache.put(new Element(PERSONAL_EVENTS_CACHE_KEY, events));
			return events;
		}
	}

	private List<PersonalEvent> loadPersonalEventsFromS3() {
		AmazonS3 client = S3Client.getInstance().getS3Client();
		ObjectListing listing = client.listObjects(S3Client.getRootBucketName(), PERSONAL_EVENTS_S3_FOLDER);

		List<String> dataKeys = listing.getObjectSummaries().stream()
				.filter(s -> s.getKey().endsWith("json")).map(s -> s.getKey())
				.collect(Collectors.toList());

		List<PersonalEvent> personalEvents = dataKeys.stream()
				.map(k ->
						S3ToModelConverter.convertS3DataToModel(client
								.getObject(S3Client.getRootBucketName(), k), PersonalEvent.class))
				.filter(p -> p != null)
				.sorted((p1, p2) -> p2.date().compareTo(p1.date()))
				.collect(Collectors.toList());

		return personalEvents;
	}
}
