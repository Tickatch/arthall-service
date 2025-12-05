package com.tickatch.arthallservice.stageseat.application.service;

import com.tickatch.arthallservice.stage.application.service.StageQueryService;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatResult;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatStatusUpdateCommand;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;
import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatRepository;
import io.github.tickatch.common.error.BusinessException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageSeatStatusUpdateService {

  private final StageSeatRepository stageSeatRepository;
  private final StageQueryService stageQueryService;

  @Transactional
  public List<StageSeatResult> updateStatus(StageSeatStatusUpdateCommand command) {

    List<StageSeat> seats = getSeats(command.seatIds());

    validateSeatCount(seats, command.seatIds());

    validateStageIsActive(seats.getFirst().getStageId());

    return changeAllStatuses(seats, command.status());
  }

  // 1. 좌석 목록 조회
  private List<StageSeat> getSeats(List<Long> seatIds) {
    return stageSeatRepository.findAllByStageSeatIdInAndDeletedAtIsNull(seatIds);
  }

  // 2. 좌석 개수 검증
  private void validateSeatCount(List<StageSeat> seats, List<Long> requestedIds) {
    if (seats.size() != requestedIds.size()) {
      throw new BusinessException(StageSeatErrorCode.SEAT_NOT_FOUND);
    }
  }

  // 3. 스테이지 검증
  private void validateStageIsActive(Long stageId) {
    stageQueryService.getActiveStage(stageId);
  }

  // 4. 모든 좌석 상태 변경
  private List<StageSeatResult> changeAllStatuses(List<StageSeat> seats, StageSeatStatus status) {

    List<StageSeatResult> results = new ArrayList<>();

    for (StageSeat seat : seats) {
      seat.changeStatus(status);
      results.add(toResult(seat));
    }

    return results;
  }

  // 5. 결과 변환
  private StageSeatResult toResult(StageSeat seat) {

    float[] vector = seat.getLocation().getVector().getValues();

    return StageSeatResult.of(
        seat.getStageSeatId(),
        seat.getSeatNumber().getValue(),
        seat.getStatus(),
        seat.getLocation().getRow(),
        seat.getLocation().getCol(),
        List.of(vector[0], vector[1]));
  }
}
