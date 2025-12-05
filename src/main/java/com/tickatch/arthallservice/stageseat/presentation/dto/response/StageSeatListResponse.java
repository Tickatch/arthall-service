package com.tickatch.arthallservice.stageseat.presentation.dto.response;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatListResult;
import java.util.List;

public record StageSeatListResponse(List<StageSeatListResult> content) {

  public StageSeatListResponse {
    content = List.copyOf(content);
  }

  public static StageSeatListResponse from(List<StageSeatListResult> list) {
    return new StageSeatListResponse(list);
  }

  @Override
  public List<StageSeatListResult> content() {
    return content;
  }
}
