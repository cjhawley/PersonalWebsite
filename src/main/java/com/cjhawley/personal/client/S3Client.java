package com.cjhawley.personal.client;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Simple S3 client creator.
 * @author cjhawley
 *
 */
public class S3Client {
	private static final String ROOT_BUCKET_NAME = "personal-site-cjhawley-1";
	
	private final AmazonS3 s3Client;
	
	private static S3Client INSTANCE = new S3Client();
	
	public static S3Client getInstance() {
		return INSTANCE;
	}
	
	private S3Client() {
		AWSCredentialsProviderChain credentialsProviderChain = new AWSCredentialsProviderChain(
				new ProfileCredentialsProvider(),
				new InstanceProfileCredentialsProvider(false));
		
		s3Client = new AmazonS3Client(credentialsProviderChain);
	}
	
	public AmazonS3 getS3Client() {
		return s3Client;
	}
	
	public static String getRootBucketName() {
		return ROOT_BUCKET_NAME;
	}
}