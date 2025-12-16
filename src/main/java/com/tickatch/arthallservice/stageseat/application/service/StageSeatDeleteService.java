package com.tickatch.arthallservice.stageseat.application.service;

import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatRepository;
import io.github.tickatch.common.error.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageSeatDeleteService {

  private final StageSeatRepository stageSeatRepository;

  // 다건 삭제
  @Transactional
  public void deleteAll(List<Long> stageSeatIds, String deletedBy) {

    // 전체 좌석 조회
    List<StageSeat> seats =
        stageSeatRepository.findAllByStageSeatIdInAndDeletedAtIsNull(stageSeatIds);

    // 조회된 개수가 입력된 개수보다 적다면 → 일부 존재하지 않음
    if (seats.size() != stageSeatIds.size()) {
      throw new BusinessException(StageSeatErrorCode.SEAT_NOT_FOUND);
    }

    // soft delete 수행
    seats.forEach(seat -> seat.softDelete(deletedBy));
  }
}
