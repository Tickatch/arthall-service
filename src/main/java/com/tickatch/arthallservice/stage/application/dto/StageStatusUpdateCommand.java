package com.tickatch.arthallservice.stage.application.dto;

import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageStatusUpdateCommand(
    Long stageId,
    StageStatus status
) {

}
