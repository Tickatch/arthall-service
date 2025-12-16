package com.tickatch.arthallservice.arthall.presentation.dto.response;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import java.time.LocalDateTime;

public record ArtHallDetailResponse(
    Long id,
    String name,
    String address,
    String status,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime updatedAt,
    String updatedBy,
    LocalDateTime deletedAt) {

  public static ArtHallDetailResponse from(ArtHall entity) {
    return new ArtHallDetailResponse(
        entity.getArtHallId(),
        entity.getName().getValue(),
        entity.getAddress().getValue(),
        entity.getStatus().name(),
        entity.getCreatedAt(),
        entity.getCreatedBy(),
        entity.getUpdatedAt(),
        entity.getUpdatedBy(),
        entity.getDeletedAt());
  }
}
