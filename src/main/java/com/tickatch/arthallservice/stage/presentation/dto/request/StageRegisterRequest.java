package com.tickatch.arthallservice.stage.presentation.dto.request;

import com.tickatch.arthallservice.stage.application.dto.StageRegisterCommand;
import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageRegisterRequest(String name, String status) {

  public StageRegisterCommand toCommand(Long artHallId) {
    return new StageRegisterCommand(artHallId, name, StageStatus.valueOf(status));
  }
}
