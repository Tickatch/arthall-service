package com.tickatch.arthallservice.stageseat.presentation.dto.request;

import java.util.List;

public record StageSeatDeleteRequest(List<Long> seatIds) {

  public StageSeatDeleteRequest {
    seatIds = List.copyOf(seatIds);
  }
}
