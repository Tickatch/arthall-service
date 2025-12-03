package com.tickatch.arthallservice.arthall.presentation.dto.response;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;

public record ArtHallRegisterResponse(Long id, String name, String address, String status) {

  public static ArtHallRegisterResponse from(ArtHallResult result) {
    return new ArtHallRegisterResponse(
        result.id(), result.name(), result.address(), result.status());
  }
}
