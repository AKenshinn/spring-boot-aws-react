package com.anat.aws.filestore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileStore {
  
  @Autowired
  private AmazonS3 amazonS3;

  public void save(String path
      , String filename
      , Optional<Map<String, String>> optionalMetadata
      , InputStream inputStream) {
    
    ObjectMetadata metadata = new ObjectMetadata();
    optionalMetadata.ifPresent(map -> {
      if (!map.isEmpty()) {
        map.forEach((key, value) -> metadata.addUserMetadata(key, value));
      }
    });

    try {
      amazonS3.putObject(path, filename, inputStream, metadata);
    } catch (AmazonServiceException e) {
      throw new IllegalStateException("Fail to store file to S3", e);
    }
  }

  public byte[] download(String path, String key) {
    try {
      S3Object object = amazonS3.getObject(path, key);
      S3ObjectInputStream inputStream = object.getObjectContent();
      return IOUtils.toByteArray(inputStream);
    } catch (AmazonServiceException | IOException e) {
      throw new IllegalStateException("Fail to download file from S3", e);
    }
  }
  
}
