package com.tickatch.arthallservice.stageseat.presentation;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatRegisterCommand;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatDeleteService;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatRegisterService;
import com.tickatch.arthallservice.stageseat.presentation.dto.request.StageSeatDeleteRequest;
import com.tickatch.arthallservice.stageseat.presentation.dto.request.StageSeatRegisterRequest;
import com.tickatch.arthallservice.stageseat.presentation.dto.response.StageSeatRegisterResponse;
import io.github.tickatch.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/arthalls")
public class StageSeatApi {

  private final StageSeatRegisterService stageSeatRegisterService;
  private final StageSeatDeleteService stageSeatDeleteService;

  @Operation(summary = "좌석 등록", description = "해당 스테이지에 여러 좌석을 등록합니다.")
  @PostMapping("/stages/{stageId}/stage-seats")
  public ApiResponse<?> register(
      @PathVariable Long stageId, @Valid @RequestBody List<StageSeatRegisterRequest> requests) {

    List<StageSeatRegisterCommand> commands =
        requests.stream().map(req -> req.toCommand(stageId)).toList();

    List<StageSeatResult> results = stageSeatRegisterService.registerAll(commands);

    return ApiResponse.success(
        results.stream().map(StageSeatRegisterResponse::from).toList(),
        results.size() + "개의 좌석이 등록되었습니다.");
  }

  @Operation(summary = "좌석 삭제", description = "여러 좌석을 한 번에 삭제합니다.")
  @DeleteMapping("/stages/stage-seats")
  public ApiResponse<?> deleteStageSeats(@Valid @RequestBody StageSeatDeleteRequest request) {

    stageSeatDeleteService.deleteAll(request.seatIds(), "SYSTEM");

    return ApiResponse.success(null, request.seatIds().size() + "개의 좌석이 삭제되었습니다.");
  }
}
