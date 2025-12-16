package com.tickatch.arthallservice.stage.application.dto;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageResult(Long stageId, Long artHallId, String name, StageStatus status) {

  public static StageResult from(Stage stage) {
    return new StageResult(
        stage.getStageId(), stage.getArtHallId(), stage.getName().getValue(), stage.getStatus());
  }
}
