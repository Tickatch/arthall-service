package com.tickatch.arthallservice.stage.presentation;

import com.tickatch.arthallservice.stage.application.dto.StageRegisterCommand;
import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.application.dto.StageUpdateCommand;
import com.tickatch.arthallservice.stage.application.service.StageRegisterService;
import com.tickatch.arthallservice.stage.application.service.StageUpdateService;
import com.tickatch.arthallservice.stage.presentation.dto.request.StageRegisterRequest;
import com.tickatch.arthallservice.stage.presentation.dto.request.StageUpdateRequest;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageRegisterResponse;
import com.tickatch.arthallservice.stage.presentation.dto.response.StageUpdateResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

  @Operation(summary = "스테이지 등록", description = "특정 아트홀에 새로운 스테이지를 생성합니다.")
  @PostMapping("/{artHallId}/stages")
  public ApiResponse<StageRegisterResponse> register(
      @PathVariable Long artHallId, @Valid @RequestBody StageRegisterRequest request) {
    StageRegisterCommand command = request.toCommand(artHallId);

    StageResult result = stageRegisterService.register(command);

    return ApiResponse.success(StageRegisterResponse.from(result), "스테이지가 등록되었습니다.");
  }

  @Operation(summary = "스테이지 수정", description = "스테이지 이름과 상태를 수정합니다.")
  @PutMapping("/stages/{stageId}")
  public ApiResponse<StageUpdateResponse> update(
      @PathVariable Long stageId, @Valid @RequestBody StageUpdateRequest request) {
    StageUpdateCommand command = request.toCommand(stageId);

    StageResult result = stageUpdateService.update(command);

    return ApiResponse.success(StageUpdateResponse.from(result), "스테이지 정보가 수정되었습니다.");
  }
}
