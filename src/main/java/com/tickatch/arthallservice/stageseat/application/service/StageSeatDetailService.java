package com.tickatch.arthallservice.stageseat.application.service;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatDetailResult;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatQueryRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageSeatDetailService {

  private final StageSeatQueryRepository stageSeatQueryRepository;

  public StageSeatDetailResult getDetail(Long stageSeatId) {

    StageSeat seat =
        stageSeatQueryRepository
            .findDetailByStageSeatId(stageSeatId)
            .orElseThrow(() -> new BusinessException(StageSeatErrorCode.SEAT_NOT_FOUND));

    return StageSeatDetailResult.from(seat);
  }
}
