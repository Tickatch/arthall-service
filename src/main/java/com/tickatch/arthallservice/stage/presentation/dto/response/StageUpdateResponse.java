package com.tickatch.arthallservice.stage.presentation.dto.response;

import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageUpdateResponse(Long stageId, String name, StageStatus status) {

  public static StageUpdateResponse from(StageResult result) {
    return new StageUpdateResponse(result.stageId(), result.name(), result.status());
  }
}
