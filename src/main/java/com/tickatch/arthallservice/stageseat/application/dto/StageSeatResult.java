package com.tickatch.arthallservice.stageseat.application.dto;

import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;
import java.util.List;

public record StageSeatResult(
    Long id, String seatNumber, StageSeatStatus status, int row, int col, List<Float> vector) {

  public StageSeatResult {
    vector = vector == null ? List.of() : List.copyOf(vector);
  }

  @Override
  public List<Float> vector() {
    return List.copyOf(vector);
  }

  public static StageSeatResult of(
      Long id, String seatNumber, StageSeatStatus status, int row, int col, List<Float> vector) {
    return new StageSeatResult(id, seatNumber, status, row, col, vector);
  }
}
