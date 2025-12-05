package com.tickatch.arthallservice.stageseat.presentation.dto.request;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatStatusUpdateCommand;
import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;
import java.util.List;

public record StageSeatStatusUpdateRequest(List<Long> seatIds, StageSeatStatus status) {

  public StageSeatStatusUpdateRequest {
    seatIds = List.copyOf(seatIds);
  }

  public StageSeatStatusUpdateCommand toCommand() {
    return new StageSeatStatusUpdateCommand(seatIds, status);
  }
}
