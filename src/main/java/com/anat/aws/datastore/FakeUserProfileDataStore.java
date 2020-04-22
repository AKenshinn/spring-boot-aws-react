package com.anat.aws.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.anat.aws.profile.UserProfile;

import org.springframework.stereotype.Repository;

@Repository
public class FakeUserProfileDataStore {

  private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

  static { 
    USER_PROFILES.add(new UserProfile(UUID.fromString("ee961ef4-1692-4bb7-841b-01402b09359a"), "janetjones", null));
    USER_PROFILES.add(new UserProfile(UUID.fromString("bb82c8ac-a99a-455d-9916-d6759d364b5a"), "antoniojunior", null));
  }

  public List<UserProfile> getUserProfiles() {
    return USER_PROFILES;
  }

}
