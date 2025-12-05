package com.tickatch.arthallservice.stageseat.presentation.dto.response;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatListResult;
import org.springframework.data.domain.Page;

public record StageSeatListResponse(
    Page<StageSeatListResult> content
) {

  public static StageSeatListResponse from(Page<StageSeatListResult> page) {
    return new StageSeatListResponse(page);
  }
}
