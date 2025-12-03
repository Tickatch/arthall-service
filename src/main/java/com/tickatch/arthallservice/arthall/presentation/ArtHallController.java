package com.tickatch.arthallservice.arthall.presentation;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.application.service.ArtHallRegisterService;
import com.tickatch.arthallservice.arthall.presentation.dto.request.ArtHallRegisterRequest;
import com.tickatch.arthallservice.arthall.presentation.dto.response.ArtHallRegisterResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/arthalls")
public class ArtHallController {

  private final ArtHallRegisterService artHallRegisterService;

  @Operation(summary = "아트홀 등록", description = "새로운 아트홀을 등록합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<ArtHallRegisterResponse>> register(
      @Valid @RequestBody ArtHallRegisterRequest request) {
    ArtHallResult result = artHallRegisterService.register(request);
    return ResponseEntity.ok(
        ApiResponse.success(ArtHallRegisterResponse.from(result), "아트홀이 등록되었습니다."));
  }
}
