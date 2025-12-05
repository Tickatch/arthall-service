package com.tickatch.arthallservice.stageseat.presentation;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatDetailResult;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatListResult;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatDetailService;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatListService;
import com.tickatch.arthallservice.stageseat.presentation.dto.response.StageSeatDetailResponse;
import com.tickatch.arthallservice.stageseat.presentation.dto.response.StageSeatListResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/arthalls")
public class StageSeatQueryApi {

  private final StageSeatDetailService stageSeatDetailService;
  private final StageSeatListService stageSeatListService;

  @Operation(
      summary = "좌석 상세 조회",
      description = "stageSeatId로 특정 좌석을 조회합니다. 삭제된 좌석은 조회되지 않습니다."
  )
  @GetMapping("/stage-seats/{stageSeatId}")
  public ApiResponse<?> getStageSeatDetail(@PathVariable Long stageSeatId) {

    StageSeatDetailResult result = stageSeatDetailService.getDetail(stageSeatId);

    return ApiResponse.success(StageSeatDetailResponse.from(result), "좌석 상세 조회가 완료되었습니다.");
  }

  @Operation(
      summary = "좌석 목록 조회",
      description = "특정 스테이지(stageId)에 속한 좌석 목록을 조회합니다. "
          + "키워드 검색(좌석번호)과 페이징을 지원하며, 삭제된 좌석은 제외됩니다."
  )
  @GetMapping("/stages/{stageId}/stage-seats")
  public ApiResponse<?> getStageSeatList(
      @PathVariable Long stageId,
      @RequestParam(required = false) String keyword,
      Pageable pageable
  ) {
    Page<StageSeatListResult> result = stageSeatListService.getList(stageId, keyword, pageable);

    return ApiResponse.success(
        StageSeatListResponse.from(result),
        "좌석 목록 조회가 완료되었습니다."
    );
  }

}
