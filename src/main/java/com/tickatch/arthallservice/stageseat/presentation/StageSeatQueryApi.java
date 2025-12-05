package com.tickatch.arthallservice.stageseat.presentation;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatDetailResult;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatDetailService;
import com.tickatch.arthallservice.stageseat.presentation.dto.response.StageSeatDetailResponse;
import io.github.tickatch.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/arthalls")
public class StageSeatQueryApi {

  private final StageSeatDetailService stageSeatDetailService;

  @GetMapping("/stage-seats/{stageSeatId}")
  public ApiResponse<?> getStageSeatDetail(@PathVariable Long stageSeatId) {

    StageSeatDetailResult result = stageSeatDetailService.getDetail(stageSeatId);

    return ApiResponse.success(StageSeatDetailResponse.from(result), "좌석 상세 조회가 완료되었습니다.");
  }
}
