package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.application.dto.StageStatusUpdateCommand;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageStatusUpdateService {

  private final StageRepository stageRepository;

  @Transactional
  public StageResult updateStatus(StageStatusUpdateCommand command) {

    Stage stage =
        stageRepository
            .findByStageIdAndDeletedAtIsNull(command.stageId())
            .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));

    stage.changeStatus(command.status());

    return StageResult.from(stage);
  }
}
