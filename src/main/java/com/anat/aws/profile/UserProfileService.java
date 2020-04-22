package com.anat.aws.profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.anat.aws.bucket.BucketName;
import com.anat.aws.filestore.FileStore;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileService {

  private static final List<String> ACCEPT_IMAGE_CONTENT_TYPES = Arrays.asList(
    ContentType.IMAGE_JPEG.getMimeType(),
    ContentType.IMAGE_PNG.getMimeType(),
    ContentType.IMAGE_GIF.getMimeType()
  );

  @Autowired
  private FileStore fileStore;

  @Autowired
  private UserProfileDataAccessService userProfileDataAccessService;

  public List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }
  
  public void uploadUserProfileImage(UUID uuid, MultipartFile file) {
    // 1. Check if image is not empty
    isFileEmpty(file);

    // 2. Check if file is an image
    isImageFile(file);

    // 3. Check the user exist in database
    UserProfile user = getUserProfileOrThrow(uuid);

    // 4. Grab some metadata from file if any
    Map<String, String> metadata = extractMetadata(file);

    // 5. Store the image in s3 and update database with s3 image link
    String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUuid());
    String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

    try {
      fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
      user.setImageLink(filename);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public byte[] downloadUserProfileImage(UUID uuid) {
    UserProfile user = getUserProfileOrThrow(uuid);
    String path = String.format("%s/%s%s",
        BucketName.PROFILE_IMAGE.getBucketName(),
        user.getUuid());
    
    return user.getImageLink()
        .map(key -> fileStore.download(path, key))
        .orElse(new byte[0]);
  }
  
  private void isFileEmpty(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
    }
  }
  
  private void isImageFile(MultipartFile file) {
    if (!ACCEPT_IMAGE_CONTENT_TYPES.contains(file.getContentType())) {
      throw new IllegalStateException("File must be an image");
    }
  }
  
  private Map<String, String> extractMetadata(MultipartFile file) {
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    
    return metadata;
  }

  private UserProfile getUserProfileOrThrow(UUID uuid) {
    return userProfileDataAccessService.getUserProfiles().stream()
        .filter(userProfile -> userProfile.getUuid().equals(uuid))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", uuid)));
  }

}
