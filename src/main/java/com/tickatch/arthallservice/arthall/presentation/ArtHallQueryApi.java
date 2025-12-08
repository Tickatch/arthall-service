package com.tickatch.arthallservice.arthall.presentation;

import com.tickatch.arthallservice.arthall.application.service.ArtHallQueryService;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.presentation.dto.response.ArtHallDetailResponse;
import com.tickatch.arthallservice.arthall.presentation.dto.response.ArtHallListResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.github.tickatch.common.api.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/arthalls")
@Tag(name = "ArtHall Query", description = "아트홀 조회 API")
public class ArtHallQueryApi {

  private final ArtHallQueryService artHallQueryService;

  @Operation(summary = "아트홀 상세 조회", description = "아트홀을 상세 조회합니다.")
  @GetMapping("/{id}")
  public ApiResponse<ArtHallDetailResponse> getDetail(@PathVariable Long id) {
    ArtHall artHall = artHallQueryService.getArtHallDetail(id);
    return ApiResponse.success(ArtHallDetailResponse.from(artHall));
  }

  @Operation(
      summary = "아트홀 목록 조회",
      description =
          """
              아트홀 정보를 페이징 형태로 조회합니다.
              검색어(keyword)가 있을 경우 아트홀 이름 또는 주소에 대해 부분 일치 검색을 수행하며,
              최신 생성순(createdAt DESC)으로 정렬하여 반환합니다.
              """)
  @GetMapping
  public ApiResponse<PageResponse<ArtHallListResponse>> getArtHalls(
      @RequestParam(required = false) String keyword, Pageable pageable) {

    Page<ArtHall> page = artHallQueryService.getArtHallList(keyword, pageable);

    return ApiResponse.success(PageResponse.from(page, ArtHallListResponse::from));
  }
}
