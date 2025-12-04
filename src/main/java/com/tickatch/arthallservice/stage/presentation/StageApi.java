package com.tickatch.arthallservice.stage.presentation;

import com.tickatch.arthallservice.stage.application.dto.StageRegisterCommand;
import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.application.dto.StageStatusUpdateCommand;
import com.tickatch.arthallservice.stage.application.dto.StageUpdateCommand;
import com.tickatch.arthallservice.stage.application.service.StageDeleteService;
import com.tickatch.arthallservice.stage.application.service.StageRegisterService;
import com.tickatch.arthallservice.stage.application.service.StageStatusUpdateService;
import com.tickatch.arthallservice.stage.application.service.StageUpdateService;
import com.tickatch.arthallservice.stage.presentation.dto.request.StageRegisterRequest;
import com.tickatch.arthallservice.stage.presentation.dto.request.StageStatusUpdateRequest;
import com.tickatch.arthallservice.stage.presentation.dto.request.StageUpdateRequest;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageRegisterResponse;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageStatusUpdateResponse;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageUpdateResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/arthalls")
@RequiredArgsConstructor
public class StageApi {

  private final StageRegisterService stageRegisterService;
  private final StageUpdateService stageUpdateService;
  private final StageDeleteService stageDeleteService;
  private final StageStatusUpdateService stageStatusUpdateService;

  @Operation(summary = "스테이지 등록", description = "특정 아트홀에 새로운 스테이지를 생성합니다.")
  @PostMapping("/{artHallId}/stages")
  public ApiResponse<StageRegisterResponse> register(
      @PathVariable Long artHallId,
      @Valid @RequestBody StageRegisterRequest request
  ) {
    StageRegisterCommand command = request.toCommand(artHallId);
    StageResult result = stageRegisterService.register(command);

    return ApiResponse.success(
        StageRegisterResponse.from(result),
        "스테이지가 등록되었습니다."
    );
  }

  @Operation(summary = "스테이지 수정", description = "스테이지 이름과 상태를 수정합니다.")
  @PutMapping("/stages/{stageId}")
  public ApiResponse<StageUpdateResponse> update(
      @PathVariable Long stageId,
      @Valid @RequestBody StageUpdateRequest request
  ) {
    StageUpdateCommand command = request.toCommand(stageId);
    StageResult result = stageUpdateService.update(command);

    return ApiResponse.success(
        StageUpdateResponse.from(result),
        "스테이지 정보가 수정되었습니다."
    );
  }

  @Operation(summary = "스테이지 삭제", description = "스테이지를 소프트 삭제합니다.")
  @DeleteMapping("/stages/{stageId}")
  public ApiResponse<Void> delete(@PathVariable Long stageId) {

    stageDeleteService.delete(stageId, "SYSTEM");

    return ApiResponse.successWithMessage("스테이지가 삭제되었습니다.");
  }

  @Operation(summary = "스테이지 상태 변경", description = "스테이지 상태를 ACTIVE 또는 INACTIVE 로 변경합니다.")
  @PostMapping("/stages/{stageId}/status")
  public ApiResponse<StageStatusUpdateResponse> updateStatus(
      @PathVariable Long stageId,
      @Valid @RequestBody StageStatusUpdateRequest request
  ) {
    StageStatusUpdateCommand command = request.toCommand(stageId);
    StageResult result = stageStatusUpdateService.updateStatus(command);

    return ApiResponse.success(
        StageStatusUpdateResponse.from(result),
        "스테이지 상태가 변경되었습니다."
    );
  }
}
