package com.tickatch.arthallservice.stageseat.presentation.dto.response;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;

public record StageSeatStatusUpdateResponse(Long id, String seatNumber, StageSeatStatus status) {

  public static StageSeatStatusUpdateResponse from(StageSeatResult result) {
    return new StageSeatStatusUpdateResponse(result.id(), result.seatNumber(), result.status());
  }
}
