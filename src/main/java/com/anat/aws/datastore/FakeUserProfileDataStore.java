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
    USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "janet", null));
    USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "antonio", null));
  }

  public List<UserProfile> getUserProfiles() {
    return USER_PROFILES;
  }

}
