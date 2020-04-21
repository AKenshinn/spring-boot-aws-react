package com.anat.aws.profile;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileService {

  @Autowired
  private UserProfileDataAccessService userProfileDataAccessService;

  List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }

  void uploadUserProfileImage(UUID uuid, MultipartFile file) {
    /**
     * 1. Check if image is not empty
     * 2. Check if file is an image
     * 3. Check the user exist in database
     * 4. Grab some metadata from file
     * 5. Store the image in s3 and update database with s3 image link
    */
  }

}
