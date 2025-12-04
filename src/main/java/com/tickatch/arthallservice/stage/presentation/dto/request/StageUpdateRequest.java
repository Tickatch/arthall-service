package com.tickatch.arthallservice.stage.presentation.dto.request;

import com.tickatch.arthallservice.stage.application.dto.StageUpdateCommand;
import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageUpdateRequest(String name, String status) {

  public StageUpdateCommand toCommand(Long stageId) {
    return new StageUpdateCommand(stageId, name, StageStatus.valueOf(status));
  }
}
