package com.tickatch.arthallservice.arthall.presentation.dto.response;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;

public record ArtHallStatusUpdateResponse(Long id, String name, String address, String status) {

  public static ArtHallStatusUpdateResponse from(ArtHallResult result) {
    return new ArtHallStatusUpdateResponse(
        result.id(), result.name(), result.address(), result.status());
  }
}
