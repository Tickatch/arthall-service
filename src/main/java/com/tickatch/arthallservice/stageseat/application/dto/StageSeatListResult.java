package com.tickatch.arthallservice.stageseat.application.dto;

import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import java.util.Arrays;

public record StageSeatListResult(
    Long stageSeatId,
    String seatNumber,
    String status,
    Integer row,
    Integer col,
    float[] vector
) {

  public StageSeatListResult {
    if (vector != null) {
      vector = Arrays.copyOf(vector, vector.length);
    }
  }

  @Override
  public float[] vector() {
    return vector == null ? null : Arrays.copyOf(vector, vector.length);
  }

  public static StageSeatListResult from(StageSeat seat) {
    float[] vector = seat.getLocation().getVector().getValues();

    return new StageSeatListResult(
        seat.getStageSeatId(),
        seat.getSeatNumber().getValue(),
        seat.getStatus().name(),
        seat.getLocation().getRow(),
        seat.getLocation().getCol(),
        vector
    );
  }
}
