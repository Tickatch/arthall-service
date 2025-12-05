package com.tickatch.arthallservice.stageseat.presentation.dto.response;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;
import java.util.List;

public record StageSeatRegisterResponse(
    Long id, String seatNumber, StageSeatStatus status, int row, int col, List<Float> vector) {

  public StageSeatRegisterResponse {
    vector = vector == null ? List.of() : List.copyOf(vector);
  }

  @Override
  public List<Float> vector() {
    return List.copyOf(vector);
  }

  public static StageSeatRegisterResponse from(StageSeatResult result) {
    return new StageSeatRegisterResponse(
        result.id(),
        result.seatNumber(),
        result.status(),
        result.row(),
        result.col(),
        result.vector());
  }
}
