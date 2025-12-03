package com.tickatch.arthallservice.arthall.presentation.dto.request;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallRegisterCommand;

public record ArtHallRegisterRequest(String name, String address, String status) {

  public ArtHallRegisterCommand toCommand() {
    return new ArtHallRegisterCommand(name, address, status);
  }
}
