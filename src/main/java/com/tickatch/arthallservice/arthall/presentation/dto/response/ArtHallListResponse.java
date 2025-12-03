package com.tickatch.arthallservice.arthall.presentation.dto.response;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import java.time.LocalDateTime;

public record ArtHallListResponse(
    Long id, String name, String address, String status, LocalDateTime createdAt) {

  public static ArtHallListResponse from(ArtHall entity) {
    return new ArtHallListResponse(
        entity.getArtHallId(),
        entity.getName().getValue(),
        entity.getAddress().getValue(),
        entity.getStatus().name(),
        entity.getCreatedAt());
  }
}
