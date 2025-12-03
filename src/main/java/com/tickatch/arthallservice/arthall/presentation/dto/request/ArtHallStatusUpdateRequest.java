package com.tickatch.arthallservice.arthall.presentation.dto.request;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallStatusUpdateCommand;
import jakarta.validation.constraints.NotBlank;

public record ArtHallStatusUpdateRequest(@NotBlank String status) {

  public ArtHallStatusUpdateCommand toCommand(Long id) {
    return new ArtHallStatusUpdateCommand(id, status);
  }
}
