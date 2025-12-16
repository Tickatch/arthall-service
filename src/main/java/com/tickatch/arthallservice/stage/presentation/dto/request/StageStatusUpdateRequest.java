package com.tickatch.arthallservice.stage.presentation.dto.request;

import com.tickatch.arthallservice.stage.application.dto.StageStatusUpdateCommand;
import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageStatusUpdateRequest(String status) {

  public StageStatusUpdateCommand toCommand(Long stageId) {
    return new StageStatusUpdateCommand(stageId, StageStatus.valueOf(status));
  }
}
