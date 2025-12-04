package com.tickatch.arthallservice.stage.presentation.dto.response;

import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageStatusUpdateResponse(
    Long stageId,
    String name,
    StageStatus status
) {

  public static StageStatusUpdateResponse from(StageResult result) {
    return new StageStatusUpdateResponse(
        result.stageId(),
        result.name(),
        result.status()
    );
  }
}
