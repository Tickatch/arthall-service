package com.tickatch.arthallservice.stageseat.presentation.dto.response;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatDetailResult;

public record StageSeatDetailResponse(StageSeatDetailResult content) {

  public static StageSeatDetailResponse from(StageSeatDetailResult result) {
    return new StageSeatDetailResponse(result);
  }
}
