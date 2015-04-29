package com.cjhawley.personal.client;


import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3Client {
	private final AmazonS3 S3_CLIENT;
	private AWSCredentialsProviderChain credentialsProviderChain;
	
	private static S3Client INSTANCE = new S3Client();
	
	public static S3Client getInstance() {
		return INSTANCE;
	}
	
	private S3Client() {
		credentialsProviderChain = new AWSCredentialsProviderChain(new ProfileCredentialsProvider(),
				new InstanceProfileCredentialsProvider());
		S3_CLIENT = new AmazonS3Client(credentialsProviderChain);
	}
	
	public AmazonS3 getS3Client() {
		return S3_CLIENT;
	}
	
}
