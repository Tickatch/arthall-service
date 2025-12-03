package com.tickatch.arthallservice.arthall.presentation.dto.response;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;

public record ArtHallUpdateResponse(Long id, String name, String address, String status) {

  public static ArtHallUpdateResponse from(ArtHallResult result) {
    return new ArtHallUpdateResponse(result.id(), result.name(), result.address(), result.status());
  }
}
