package com.tickatch.arthallservice.stage.presentation.dto.response;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.StageStatus;
import java.time.LocalDateTime;

public record StageListResponse(
    Long stageId, Long artHallId, String name, StageStatus status, LocalDateTime createdAt) {

  public static StageListResponse from(Stage stage) {
    return new StageListResponse(
        stage.getStageId(),
        stage.getArtHallId(),
        stage.getName().getValue(),
        stage.getStatus(),
        stage.getCreatedAt());
  }
}
