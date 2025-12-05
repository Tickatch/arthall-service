package com.tickatch.arthallservice.stageseat.application.dto;

import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import java.time.LocalDateTime;
import java.util.Arrays;

public record StageSeatDetailResult(
    Long stageSeatId,
    String seatNumber,
    String status,
    Integer row,
    Integer col,
    float[] vector,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime updatedAt,
    String updatedBy) {

  public StageSeatDetailResult {
    if (vector != null) {
      vector = Arrays.copyOf(vector, vector.length);
    }
  }

  @Override
  public float[] vector() {
    return vector == null ? null : Arrays.copyOf(vector, vector.length);
  }

  public static StageSeatDetailResult from(StageSeat seat) {

    float[] vector = seat.getLocation().getVector().getValues();

    return new StageSeatDetailResult(
        seat.getStageSeatId(),
        seat.getSeatNumber().getValue(),
        seat.getStatus().name(),
        seat.getLocation().getRow(),
        seat.getLocation().getCol(),
        vector,
        seat.getCreatedAt(),
        seat.getCreatedBy(),
        seat.getUpdatedAt(),
        seat.getUpdatedBy());
  }
}
