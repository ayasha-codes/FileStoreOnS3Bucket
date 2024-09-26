package com.s3bucket.s3bucketdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class S3Config {
	
	@Value("${cloud.aws.credentials.access-key}")
	String accesskey;
	@Value("${cloud.aws.credentials.secret-key}")
	String secretkey;
	
	@Bean
	public S3Client s3Client()
	{
		AwsBasicCredentials credentials = AwsBasicCredentials.create(accesskey, secretkey);
		
		return S3Client.builder()
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(Region.AP_SOUTHEAST_1)
				.build();
	}

}
