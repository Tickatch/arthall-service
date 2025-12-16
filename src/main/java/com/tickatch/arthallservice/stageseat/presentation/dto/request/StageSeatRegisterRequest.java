package com.tickatch.arthallservice.stageseat.presentation.dto.request;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatRegisterCommand;
import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;

public record StageSeatRegisterRequest(
    String seatNumber, StageSeatStatus status, int row, int col, float vectorX, float vectorY) {

  public StageSeatRegisterCommand toCommand() {
    return new StageSeatRegisterCommand(seatNumber, status, row, col, vectorX, vectorY);
  }
}
