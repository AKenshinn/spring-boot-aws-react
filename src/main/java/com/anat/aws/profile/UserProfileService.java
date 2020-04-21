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

  private static final List<String> ACCEPT_IMAGE_CONTENT_TYPES = Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
      ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_GIF.getMimeType());

  @Autowired
  private FileStore fileStore;

  @Autowired
  private UserProfileDataAccessService userProfileDataAccessService;

  List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }

  void uploadUserProfileImage(UUID uuid, MultipartFile file) {
    // 1. Check if image is not empty
    if (file.isEmpty()) {
      throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
    }

    // 2. Check if file is an image
    if (!ACCEPT_IMAGE_CONTENT_TYPES.contains(file.getContentType())) {
      throw new IllegalStateException("File must be an image");
    }

    // 3. Check the user exist in database
    UserProfile user = userProfileDataAccessService.getUserProfiles().stream()
        .filter(userProfile -> userProfile.getUuid().equals(uuid)).findFirst()
        .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", uuid)));

    // 4. Grab some metadata from file if any
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));

    // 5. Store the image in s3 and update database with s3 image link
    String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUuid());
    String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

    try {
      fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
