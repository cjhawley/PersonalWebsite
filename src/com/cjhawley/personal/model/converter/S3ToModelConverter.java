package com.cjhawley.personal.model.converter;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to convert s3 objects to data models.
 * @author cjhawley
 */

public class S3ToModelConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(S3ToModelConverter.class);
	
	public static <T> T convertS3DataToModel(S3Object obj, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			byte[] jsonBytes = IOUtils.toByteArray(obj.getObjectContent());
			return mapper.readValue(jsonBytes, clazz);
		} catch (IOException e) {
			LOGGER.error(String.format("Error converting S3 object to '%s' object.", clazz.getSimpleName()), e);
		}
		
		return null;
	}
}
