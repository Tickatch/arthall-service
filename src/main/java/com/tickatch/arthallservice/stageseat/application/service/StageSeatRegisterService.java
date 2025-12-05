package com.tickatch.arthallservice.stageseat.application.service;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatRegisterCommand;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.StageSeatLocation;
import com.tickatch.arthallservice.stageseat.domain.StageSeatNumber;
import com.tickatch.arthallservice.stageseat.domain.VectorValue;
import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatRepository;
import io.github.tickatch.common.error.BusinessException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageSeatRegisterService {

  private final StageSeatRepository stageSeatRepository;

  @Transactional
  public List<StageSeatResult> registerAll(List<StageSeatRegisterCommand> commands) {

    // 1) 요청 내부 seatNumber 중복 검사
    validateDuplicateSeatInRequest(commands);

    // 2) 요청 내부 row/col 중복 검사
    validateDuplicateLocationInRequest(commands);

    // 3) DB 중복 검증 + 저장
    List<StageSeatResult> results = new ArrayList<>();

    for (StageSeatRegisterCommand command : commands) {
      results.add(register(command));
    }

    return results;
  }

  private StageSeatResult register(StageSeatRegisterCommand command) {

    validateDuplicateSeat(command);
    validateDuplicateLocation(command);

    VectorValue vector = VectorValue.of(command.vectorX(), command.vectorY());
    StageSeatLocation location = StageSeatLocation.of(command.row(), command.col(), vector);

    StageSeat seat =
        StageSeat.register(command.stageId(), command.seatNumber(), command.status(), location);

    StageSeat saved = stageSeatRepository.save(seat);

    return StageSeatResult.of(
        saved.getStageSeatId(),
        saved.getSeatNumber().getValue(),
        saved.getStatus(),
        saved.getLocation().getRow(),
        saved.getLocation().getCol(),
        toList(saved.getLocation().getVector().getValues()));
  }

  private List<Float> toList(float[] values) {
    return List.of(values[0], values[1]);
  }

  /** 요청 내부 seatNumber 중복 검사 */
  private void validateDuplicateSeatInRequest(List<StageSeatRegisterCommand> commands) {

    Set<String> seen = new HashSet<>();

    for (StageSeatRegisterCommand cmd : commands) {
      if (!seen.add(cmd.seatNumber())) {
        throw new BusinessException(StageSeatErrorCode.SEATNUMBER_ALREADY_EXISTS);
      }
    }
  }

  /** 요청 내부 위치(row/col) 중복 검사 */
  private void validateDuplicateLocationInRequest(List<StageSeatRegisterCommand> commands) {

    Set<String> seen = new HashSet<>();

    for (StageSeatRegisterCommand cmd : commands) {
      String key = cmd.row() + "_" + cmd.col();

      if (!seen.add(key)) {
        throw new BusinessException(StageSeatErrorCode.ROW_COL_ALREADY_EXISTS);
      }
    }
  }

  /** DB seatNumber 중복 검사 */
  private void validateDuplicateSeat(StageSeatRegisterCommand command) {

    StageSeatNumber seatNumber = StageSeatNumber.of(command.seatNumber());

    stageSeatRepository
        .findByStageIdAndSeatNumberAndDeletedAtIsNull(command.stageId(), seatNumber)
        .ifPresent(
            existing -> {
              throw new BusinessException(StageSeatErrorCode.SEATNUMBER_ALREADY_EXISTS);
            });
  }

  /** DB 위치(row/col) 중복 검사 */
  private void validateDuplicateLocation(StageSeatRegisterCommand command) {

    stageSeatRepository
        .findByStageIdAndLocationRowAndLocationColAndDeletedAtIsNull(
            command.stageId(), command.row(), command.col())
        .ifPresent(
            existing -> {
              throw new BusinessException(StageSeatErrorCode.ROW_COL_ALREADY_EXISTS);
            });
  }
}
