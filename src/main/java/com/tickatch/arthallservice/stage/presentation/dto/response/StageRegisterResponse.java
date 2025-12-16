package com.tickatch.arthallservice.stage.presentation.dto.response;

import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageRegisterResponse(Long stageId, Long artHallId, String name, StageStatus status) {

  public static StageRegisterResponse from(StageResult result) {
    return new StageRegisterResponse(
        result.stageId(), result.artHallId(), result.name(), result.status());
  }
}
