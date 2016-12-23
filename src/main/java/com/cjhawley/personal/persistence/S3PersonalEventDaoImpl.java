package com.cjhawley.personal.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import com.spotify.futures.CompletableFutures;
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
	private static final Executor EXECUTOR = new ForkJoinPool();

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

		long startTime = System.currentTimeMillis();
		CompletionStage<List<PersonalEvent>> personalEventsFuture = CompletableFuture
				.supplyAsync(() -> client.listObjects(S3Client.getRootBucketName(), PERSONAL_EVENTS_S3_FOLDER), EXECUTOR)
				.thenApply(this::getDataKeysFromObjectListing)
				.thenApply(keys -> keys.stream()
						.map(key -> getPersonalEventFromS3Object(client, key))
						.collect(Collectors.toList()))
				.thenCompose(CompletableFutures::allAsList)
				.thenApply(personalEvents -> personalEvents.stream()
						.filter(p -> p != null)
						.sorted((p1, p2) -> p2.date().compareTo(p1.date()))
						.collect(Collectors.toList()))
				.whenComplete((response, throwable) -> {
					if (throwable != null) {
						LOGGER.error("Error loading personal events.", throwable);
					}
					long totalTimeMillis = System.currentTimeMillis() - startTime;
					LOGGER.info("Total time to load personal events: {}", totalTimeMillis);
				})
				.exceptionally(throwable -> new ArrayList<>());

		// ugh need to do this.
		return personalEventsFuture.toCompletableFuture().join();
	}

	private List<String> getDataKeysFromObjectListing(ObjectListing objectListing) {
		return objectListing.getObjectSummaries().stream()
				.filter(s -> s.getKey().endsWith("json")).map(s -> s.getKey())
				.collect(Collectors.toList());
	}

	private CompletionStage<PersonalEvent> getPersonalEventFromS3Object(AmazonS3 client, String key) {
		return CompletableFuture.supplyAsync(() -> S3ToModelConverter.convertS3DataToModel(client
				.getObject(S3Client.getRootBucketName(), key), PersonalEvent.class));
	}
}
