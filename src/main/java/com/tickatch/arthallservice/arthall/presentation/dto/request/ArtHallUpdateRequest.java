package com.tickatch.arthallservice.arthall.presentation.dto.request;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallUpdateCommand;
import jakarta.validation.constraints.NotBlank;

public record ArtHallUpdateRequest(
    @NotBlank String name, @NotBlank String address, @NotBlank String status) {

  public ArtHallUpdateCommand toCommand(Long id) {
    return new ArtHallUpdateCommand(id, name, address, status);
  }
}
