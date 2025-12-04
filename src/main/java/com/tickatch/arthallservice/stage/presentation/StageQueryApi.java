package com.tickatch.arthallservice.stage.presentation;

import com.tickatch.arthallservice.stage.application.service.StageQueryService;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageDetailResponse;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageListResponse;
import io.github.tickatch.common.api.PageResponse;
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
@RequestMapping("/api/v1/arthalls")
@RequiredArgsConstructor
public class StageQueryApi {

  private final StageQueryService stageQueryService;

  @Operation(summary = "스테이지 상세 조회", description = "스테이지 ID로 상세 정보를 조회합니다")
  @GetMapping("/stages/{stageId}")
  public StageDetailResponse getDetail(@PathVariable Long stageId) {

    Stage stage = stageQueryService.getStageDetail(stageId);

    return StageDetailResponse.from(stage);
  }

  @Operation(
      summary = "스테이지 목록 조회",
      description =
          """
                  특정 아트홀에 등록된 스테이지 목록을 페이징 형태로 조회합니다.
                  keyword가 있을 경우 스테이지 이름 부분 일치 검색을 수행합니다.
                  항상 deletedAt IS NULL 조건이 적용됩니다.
              """)
  @GetMapping("/{artHallId}/stages")
  public PageResponse<StageListResponse> getList(
      @PathVariable Long artHallId,
      @RequestParam(required = false) String keyword,
      Pageable pageable) {

    Page<Stage> page = stageQueryService.getStageList(artHallId, keyword, pageable);

    return PageResponse.from(page, StageListResponse::from);
  }
}
