package com.tickatch.arthallservice.arthall.presentation;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.application.dto.ArtHallStatusUpdateCommand;
import com.tickatch.arthallservice.arthall.application.dto.ArtHallUpdateCommand;
import com.tickatch.arthallservice.arthall.application.service.ArtHallDeleteService;
import com.tickatch.arthallservice.arthall.application.service.ArtHallRegisterService;
import com.tickatch.arthallservice.arthall.application.service.ArtHallStatusUpdateService;
import com.tickatch.arthallservice.arthall.application.service.ArtHallUpdateService;
import com.tickatch.arthallservice.arthall.presentation.dto.request.ArtHallRegisterRequest;
import com.tickatch.arthallservice.arthall.presentation.dto.request.ArtHallStatusUpdateRequest;
import com.tickatch.arthallservice.arthall.presentation.dto.request.ArtHallUpdateRequest;
import com.tickatch.arthallservice.arthall.presentation.dto.response.ArtHallRegisterResponse;
import com.tickatch.arthallservice.arthall.presentation.dto.response.ArtHallStatusUpdateResponse;
import com.tickatch.arthallservice.arthall.presentation.dto.response.ArtHallUpdateResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/arthalls")
public class ArtHallController {

  private final ArtHallRegisterService artHallRegisterService;
  private final ArtHallUpdateService artHallUpdateService;
  private final ArtHallDeleteService artHallDeleteService;
  private final ArtHallStatusUpdateService artHallStatusUpdateService;

  @Operation(summary = "아트홀 등록", description = "새로운 아트홀을 등록합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<ArtHallRegisterResponse>> register(
      @Valid @RequestBody ArtHallRegisterRequest request) {
    ArtHallResult result = artHallRegisterService.register(request.toCommand());
    return ResponseEntity.ok(
        ApiResponse.success(ArtHallRegisterResponse.from(result), "아트홀이 등록되었습니다."));
  }

  @Operation(summary = "아트홀 수정", description = "아트홀 정보를 수정합니다.")
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<ArtHallUpdateResponse>> update(
      @PathVariable Long id, @Valid @RequestBody ArtHallUpdateRequest request) {
    ArtHallUpdateCommand command = request.toCommand(id);
    ArtHallResult result = artHallUpdateService.update(command);

    return ResponseEntity.ok(
        ApiResponse.success(ArtHallUpdateResponse.from(result), "아트홀 정보가 수정되었습니다."));
  }

  @Operation(summary = "아트홀 삭제", description = "아트홀을 소프트 삭제합니다.")
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    artHallDeleteService.delete(id, "SYSTEM");
    return ResponseEntity.ok(ApiResponse.successWithMessage("아트홀이 삭제되었습니다."));
  }

  @Operation(summary = "아트홀 상태 변경", description = "아트홀 상태를 ACTIVE 또는 INACTIVE 로 변경합니다.")
  @PostMapping("/{id}/status")
  public ResponseEntity<ApiResponse<ArtHallStatusUpdateResponse>> updateStatus(
      @PathVariable Long id, @Valid @RequestBody ArtHallStatusUpdateRequest request) {
    ArtHallStatusUpdateCommand command = request.toCommand(id);
    ArtHallResult result = artHallStatusUpdateService.updateStatus(command);

    return ResponseEntity.ok(
        ApiResponse.success(ArtHallStatusUpdateResponse.from(result), "아트홀 상태가 변경되었습니다."));
  }
}
