package com.cjhawley.personal.persistence;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.spotify.futures.CompletableFutures;

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
public class S3PersonalEvents {
	private static final Logger LOGGER = LoggerFactory.getLogger(S3PersonalEvents.class);
	private static final Executor EXECUTOR = new ForkJoinPool();
	private static final String PERSONAL_EVENTS_CACHE_KEY = "PersonalEvents";
	private static final String PERSONAL_EVENTS_S3_FOLDER = "personal-events";

	private final LoadingCache<String, List<PersonalEvent>> personalEventCache;


	public S3PersonalEvents() {
		this.personalEventCache = getPersonalEventCache();
	}

	public S3PersonalEvents(LoadingCache<String, List<PersonalEvent>> personalEventCache) {
		this.personalEventCache = personalEventCache;
	}

	public List<PersonalEvent> getPersonalEvents() {

		try {
			return personalEventCache.get(PERSONAL_EVENTS_CACHE_KEY);
		} catch (Exception e) {
			LOGGER.error("Error fetching personal events from data source.", e);
		}

		return new ArrayList<>();
	}

	private List<PersonalEvent> loadPersonalEventsFromS3() {
		final AmazonS3 client = S3Client.getInstance().getS3Client();



		long startTime = System.currentTimeMillis();
		CompletionStage<List<PersonalEvent>> personalEventsFuture = CompletableFuture
				.supplyAsync(() -> client.listObjects(S3Client.getRootBucketName(), PERSONAL_EVENTS_S3_FOLDER), EXECUTOR)
				.thenApply(this::getDataKeysFromObjectListing)
				.thenApply(keys -> keys.stream()
						.map(key -> getPersonalEventFromS3Object(client, key))
						.collect(Collectors.toList()))
				.thenCompose(CompletableFutures::allAsList)
				.thenApply(personalEvents -> personalEvents.stream()
						.filter(Objects::nonNull)
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
				.filter(s -> s.getKey().endsWith("json")).map(S3ObjectSummary::getKey)
				.collect(Collectors.toList());
	}

	private CompletionStage<PersonalEvent> getPersonalEventFromS3Object(AmazonS3 client, String key) {
		return CompletableFuture.supplyAsync(() -> S3ToModelConverter.convertS3DataToModel(client
				.getObject(S3Client.getRootBucketName(), key), PersonalEvent.class));
	}

	private LoadingCache<String, List<PersonalEvent>> getPersonalEventCache() {

		return CacheBuilder.newBuilder()
			.maximumSize(1000)
			.refreshAfterWrite(12, TimeUnit.HOURS)
			.build(new CacheLoader<String, List<PersonalEvent>>() {
				public List<PersonalEvent> load(String key) throws Exception {
					return loadPersonalEventsFromS3();
				}

				public ListenableFuture<List<PersonalEvent>> reload(final String key, List<PersonalEvent> prevPersonalEvents) {
					ListenableFutureTask<List<PersonalEvent>> task = ListenableFutureTask.create(() -> loadPersonalEventsFromS3());
					EXECUTOR.execute(task);
					return task;
				}
			});
	}
}
