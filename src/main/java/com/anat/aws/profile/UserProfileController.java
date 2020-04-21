package com.anat.aws.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user-profile")
public class UserProfileController {

  @Autowired
  private UserProfileService userProfileService;

  @GetMapping
  public List<UserProfile> getUserProfiles() {
    return userProfileService.getUserProfiles();
  }

}
