package com.tickatch.arthallservice.stageseat.presentation.dto.response;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;
import java.util.List;

public record StageSeatUpdateResponse(
    Long id, String seatNumber, StageSeatStatus status, int row, int col, List<Float> vector) {

  public StageSeatUpdateResponse {
    vector = vector == null ? List.of() : List.copyOf(vector);
  }

  @Override
  public List<Float> vector() {
    return List.copyOf(vector);
  }

  public static StageSeatUpdateResponse from(StageSeatResult result) {
    return new StageSeatUpdateResponse(
        result.id(),
        result.seatNumber(),
        result.status(),
        result.row(),
        result.col(),
        result.vector());
  }
}
