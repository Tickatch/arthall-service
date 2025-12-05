package com.tickatch.arthallservice.stageseat.application.service;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatUpdateCommand;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.StageSeatLocation;
import com.tickatch.arthallservice.stageseat.domain.VectorValue;
import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatRepository;
import io.github.tickatch.common.error.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageSeatUpdateService {

  private final StageSeatRepository stageSeatRepository;
  private final StageRepository stageRepository;

  @Transactional
  public StageSeatResult update(StageSeatUpdateCommand command) {

    StageSeat seat = getSeat(command.stageSeatId());

    getAndValidateStage(seat.getStageId());

    validateDuplicateLocation(
        seat.getStageId(), command.row(), command.col(), seat.getStageSeatId());

    StageSeatLocation newLocation = createLocation(command);

    seat.updateLocation(newLocation);

    return toResult(seat, command);
  }

  // 1. 좌석 조회
  private StageSeat getSeat(Long seatId) {
    return stageSeatRepository
        .findByStageSeatIdAndDeletedAtIsNull(seatId)
        .orElseThrow(() -> new BusinessException(StageSeatErrorCode.SEAT_NOT_FOUND));
  }

  // 2. 스테이지 조회 및 상태 검증
  private Stage getAndValidateStage(Long stageId) {

    Stage stage =
        stageRepository
            .findByStageIdAndDeletedAtIsNull(stageId)
            .orElseThrow(
                () -> new BusinessException(StageSeatErrorCode.STAGE_NOT_FOUND_FOR_SEAT_OPERATION));

    if (stage.isInactive()) {
      throw new BusinessException(StageSeatErrorCode.STAGE_INACTIVE_FOR_SEAT_OPERATION);
    }

    return stage;
  }

  // 3. row/col 중복 검사
  private void validateDuplicateLocation(Long stageId, int row, int col, Long currentSeatId) {

    stageSeatRepository
        .findByStageIdAndLocationRowAndLocationColAndDeletedAtIsNull(stageId, row, col)
        .ifPresent(
            existing -> {
              if (!existing.getStageSeatId().equals(currentSeatId)) {
                throw new BusinessException(StageSeatErrorCode.ROW_COL_ALREADY_EXISTS);
              }
            });
  }

  // VO 생성
  private StageSeatLocation createLocation(StageSeatUpdateCommand command) {

    VectorValue vector = VectorValue.of(command.vectorX(), command.vectorY());

    return StageSeatLocation.of(command.row(), command.col(), vector);
  }

  // 5. 결과 변환
  private StageSeatResult toResult(StageSeat seat, StageSeatUpdateCommand command) {

    return StageSeatResult.of(
        seat.getStageSeatId(),
        seat.getSeatNumber().getValue(),
        seat.getStatus(),
        command.row(),
        command.col(),
        List.of(command.vectorX(), command.vectorY()));
  }
}
