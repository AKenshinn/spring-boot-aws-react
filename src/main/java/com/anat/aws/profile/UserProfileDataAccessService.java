package com.anat.aws.profile;

import java.util.List;

import com.anat.aws.datastore.FakeUserProfileDataStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileDataAccessService {

  @Autowired
  private FakeUserProfileDataStore fakeUserProfileDataStore;

  List<UserProfile> getUserProfiles() {
    return fakeUserProfileDataStore.getUserProfiles();
  }
  
}
