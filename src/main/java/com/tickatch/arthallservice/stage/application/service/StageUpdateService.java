package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.application.dto.StageUpdateCommand;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageUpdateService {

  private final StageRepository stageRepository;

  @Transactional
  public StageResult update(StageUpdateCommand command) {

    Stage stage =
        stageRepository
            .findByStageIdAndDeletedAtIsNull(command.stageId())
            .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));

    if (stage.isInactive()) {
      throw new BusinessException(StageErrorCode.STAGE_INACTIVE);
    }

    stage.updateInfo(command.name(), command.status());

    return StageResult.from(stage);
  }
}
