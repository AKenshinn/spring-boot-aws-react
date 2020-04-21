package com.anat.aws.profile;

import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

  private UUID uuid;

  private String username;

  private String imageLink;

  public UserProfile(UUID uuid, String username, String imageLink) {
    this.uuid = uuid;
    this.username = username;
    this.imageLink = imageLink;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserProfile that = (UserProfile) o;
    return Objects.equals(uuid, that.getUuid())
        && Objects.equals(username, that.getUsername())
        && Objects.equals(imageLink, that.getImageLink());
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, username, imageLink);
  }

}
