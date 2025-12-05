package com.tickatch.arthallservice.stageseat.presentation.dto.request;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatUpdateCommand;

public record StageSeatUpdateRequest(Long seatId, int row, int col, float vectorX, float vectorY) {

  public StageSeatUpdateCommand toCommand() {
    return new StageSeatUpdateCommand(seatId, row, col, vectorX, vectorY);
  }
}
