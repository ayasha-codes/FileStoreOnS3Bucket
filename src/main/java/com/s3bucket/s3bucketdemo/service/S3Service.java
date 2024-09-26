package com.s3bucket.s3bucketdemo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {
	
	@Autowired
	private S3Client s3Client;
	
	private final String BUCKET_NAME = "bucketforayasha";
	
	public String uploadFile(MultipartFile file) throws IOException
	{
		String fileName = file.getOriginalFilename();
		
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(BUCKET_NAME)
				.key(fileName)
				.build();
		
		s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
		
		return "File uploaded successfully: " + fileName;
	}
	
	
	public String deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            return "File deleted successfully: " + fileName;
        } catch (S3Exception e) {
            System.err.println("Error deleting file from S3: " + e.getMessage());
            return "File deletion failed: " + e.getMessage();
        } catch (SdkClientException e) {
            System.err.println("Client error occurred: " + e.getMessage());
            return "File deletion failed: " + e.getMessage();
        }
    }
	

	
	 public byte[] downloadFile(String fileName) {
	        try {
	            // Create a GetObjectRequest with bucket name and file name
	            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
	                    .bucket(BUCKET_NAME)
	                    .key(fileName)
	                    .build();

	            // Retrieve the object as bytes
	            ResponseBytes responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
	            return responseBytes.asByteArray(); // Convert to byte array
	        } catch (S3Exception e) {
	            // Handle S3 specific exceptions
	            System.err.println("Error retrieving file from S3: " + e.getMessage());
	            return null;
	        } catch (SdkClientException e) {
	            // Handle client specific exceptions
	            System.err.println("Client error occurred: " + e.getMessage());
	            return null;
	        }
	    }
}
