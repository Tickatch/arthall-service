package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageDeleteService {

  private final StageRepository stageRepository;

  @Transactional
  public void delete(Long stageId, String deletedBy) {

    Stage stage =
        stageRepository
            .findByStageIdAndDeletedAtIsNull(stageId)
            .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));

    stage.softDelete(deletedBy);
  }
}
