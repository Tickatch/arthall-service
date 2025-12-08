package com.tickatch.arthallservice.stageseat.presentation;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatRegisterCommand;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatUpdateCommand;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatDeleteService;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatRegisterService;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatStatusUpdateService;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatUpdateService;
import com.tickatch.arthallservice.stageseat.presentation.dto.request.StageSeatDeleteRequest;
import com.tickatch.arthallservice.stageseat.presentation.dto.request.StageSeatRegisterRequest;
import com.tickatch.arthallservice.stageseat.presentation.dto.request.StageSeatStatusUpdateRequest;
import com.tickatch.arthallservice.stageseat.presentation.dto.request.StageSeatUpdateRequest;
import com.tickatch.arthallservice.stageseat.presentation.dto.response.StageSeatRegisterResponse;
import com.tickatch.arthallservice.stageseat.presentation.dto.response.StageSeatStatusUpdateResponse;
import com.tickatch.arthallservice.stageseat.presentation.dto.response.StageSeatUpdateResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/arthalls")
public class StageSeatApi {

  private final StageSeatRegisterService stageSeatRegisterService;
  private final StageSeatDeleteService stageSeatDeleteService;
  private final StageSeatUpdateService stageSeatUpdateService;
  private final StageSeatStatusUpdateService stageSeatStatusUpdateService;

  @Operation(summary = "좌석 등록", description = "해당 스테이지에 여러 좌석을 등록합니다.")
  @PostMapping("/stages/{stageId}/stage-seats")
  public ApiResponse<?> registerAll(
      @PathVariable Long stageId, @Valid @RequestBody List<StageSeatRegisterRequest> requests) {

    List<StageSeatRegisterCommand> commands =
        requests.stream().map(StageSeatRegisterRequest::toCommand).toList();

    List<StageSeatResult> results = stageSeatRegisterService.registerAll(stageId, commands);

    return ApiResponse.success(
        results.stream().map(StageSeatRegisterResponse::from).toList(), "좌석이 등록되었습니다.");
  }

  @Operation(summary = "좌석 위치 수정", description = "좌석의 row, col, vector 값을 수정합니다.")
  @PutMapping("/stages/stage-seats/{seatId}")
  public ApiResponse<StageSeatUpdateResponse> updateSeatLocation(
      @PathVariable Long seatId, @Valid @RequestBody StageSeatUpdateRequest request) {

    StageSeatUpdateCommand command = request.toCommand(seatId);

    return ApiResponse.success(
        StageSeatUpdateResponse.from(stageSeatUpdateService.update(command)), "좌석 위치가 수정되었습니다.");
  }

  @Operation(summary = "좌석 상태 변경", description = "여러 좌석의 상태를 한 번에 변경합니다.")
  @PostMapping("/stages/stage-seats/status")
  public ApiResponse<List<StageSeatStatusUpdateResponse>> updateSeatStatus(
      @Valid @RequestBody StageSeatStatusUpdateRequest request) {

    List<StageSeatResult> results = stageSeatStatusUpdateService.updateStatus(request.toCommand());

    return ApiResponse.success(
        results.stream().map(StageSeatStatusUpdateResponse::from).toList(), "좌석 상태가 수정되었습니다.");
  }

  @Operation(summary = "좌석 삭제", description = "여러 좌석을 한 번에 삭제합니다.")
  @DeleteMapping("/stages/stage-seats")
  public ApiResponse<?> deleteStageSeats(@Valid @RequestBody StageSeatDeleteRequest request) {

    stageSeatDeleteService.deleteAll(request.seatIds(), "SYSTEM");

    return ApiResponse.success(null, request.seatIds().size() + "개의 좌석이 삭제되었습니다.");
  }
}
