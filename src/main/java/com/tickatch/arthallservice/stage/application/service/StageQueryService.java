package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.infrastructure.repository.StageQueryRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageQueryService {

  private final StageQueryRepository stageQueryRepository;

  @Transactional(readOnly = true)
  public Stage getStageDetail(Long stageId) {
    return stageQueryRepository.findDetail(stageId)
        .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));
  }
}
