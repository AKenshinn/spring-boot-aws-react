package com.anat.aws.profile;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user-profile")
public class UserProfileController {

  @Autowired
  private UserProfileService userProfileService;

  @GetMapping
  public List<UserProfile> getUserProfiles() {
    return userProfileService.getUserProfiles();
  }

  @PostMapping(
    path = "{uuid}/image/upload"
    , consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    , produces = MediaType.APPLICATION_JSON_VALUE
  )
  public void uploadUserProfileImage(@PathVariable("uuid") UUID uuid
      , @RequestParam("file") MultipartFile file) {
    userProfileService.uploadUserProfileImage(uuid, file);
  }

}
