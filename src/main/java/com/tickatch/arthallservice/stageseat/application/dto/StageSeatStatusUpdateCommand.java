package com.tickatch.arthallservice.stageseat.application.dto;

import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;
import java.util.List;

public record StageSeatStatusUpdateCommand(List<Long> seatIds, StageSeatStatus status) {

  public StageSeatStatusUpdateCommand {
    seatIds = List.copyOf(seatIds);
  }
}
