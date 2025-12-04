package com.tickatch.arthallservice.stage.presentation;

import com.tickatch.arthallservice.stage.application.service.StageQueryService;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/arthalls/stages")
@RequiredArgsConstructor
public class StageQueryApi {

  private final StageQueryService stageQueryService;

  @Operation(summary = "스테이지 상세 조회", description = "스테이지 정보를 상세 조회합니다.")
  @GetMapping("/{stageId}")
  public StageDetailResponse getDetail(@PathVariable Long stageId) {

    Stage stage = stageQueryService.getStageDetail(stageId);

    return StageDetailResponse.from(stage);
  }
}
