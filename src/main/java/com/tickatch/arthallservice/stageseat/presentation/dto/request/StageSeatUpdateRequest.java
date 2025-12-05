package com.tickatch.arthallservice.stageseat.presentation.dto.request;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatUpdateCommand;

public record StageSeatUpdateRequest(int row, int col, float vectorX, float vectorY) {

  public StageSeatUpdateCommand toCommand(Long seatId) {
    return new StageSeatUpdateCommand(seatId, row, col, vectorX, vectorY);
  }
}
