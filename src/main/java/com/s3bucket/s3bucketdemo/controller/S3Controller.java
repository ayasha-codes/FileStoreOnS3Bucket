package com.s3bucket.s3bucketdemo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.s3bucket.s3bucketdemo.service.S3Service;

@RestController
public class S3Controller {
	
	@Autowired
	private S3Service s3Service;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String result = s3Service.uploadFile(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
	}
	
	
	@DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        String message = s3Service.deleteFile(fileName);
        return ResponseEntity.ok(message);
    }
	
	

	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
		
	    byte[] fileData = s3Service.downloadFile(fileName);// Ensure bucketName is defined correctly
		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
		        .body(fileData);
	}

}
