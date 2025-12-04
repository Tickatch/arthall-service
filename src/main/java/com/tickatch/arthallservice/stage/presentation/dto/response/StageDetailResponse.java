package com.tickatch.arthallservice.stage.presentation.dto.response;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.StageStatus;
import java.time.LocalDateTime;

public record StageDetailResponse(
    Long stageId,
    Long artHallId,
    String name,
    StageStatus status,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime updatedAt,
    String updatedBy
) {

  public static StageDetailResponse from(Stage stage) {
    return new StageDetailResponse(
        stage.getStageId(),
        stage.getArtHallId(),
        stage.getName().getValue(),
        stage.getStatus(),
        stage.getCreatedAt(),
        stage.getCreatedBy(),
        stage.getUpdatedAt(),
        stage.getUpdatedBy()
    );
  }
}
