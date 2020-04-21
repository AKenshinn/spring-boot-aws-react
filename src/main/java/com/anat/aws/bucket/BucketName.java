package com.anat.aws.bucket;

import lombok.Getter;

public enum BucketName {

  PROFILE_IMAGE("spring-boot-aws-react");

  @Getter
  private final String bucketName;

  private BucketName(String bucketName) {
    this.bucketName = bucketName;
  }

}
