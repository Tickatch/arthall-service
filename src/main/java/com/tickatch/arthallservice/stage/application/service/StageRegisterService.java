package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import com.tickatch.arthallservice.stage.application.dto.StageRegisterCommand;
import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.StageName;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageRegisterService {

  private final StageRepository stageRepository;
  private final ArtHallRepository artHallRepository;

  @Transactional
  public StageResult register(StageRegisterCommand command) {

    validateArtHallIsActive(command.artHallId());

    StageName name = StageName.of(command.name());

    Stage stage = Stage.register(command.artHallId(), name, command.status());

    Stage saved = stageRepository.save(stage);

    return StageResult.from(saved);
  }

  private void validateArtHallIsActive(Long artHallId) {

    ArtHall artHall =
        artHallRepository
            .findByArtHallIdAndDeletedAtIsNull(artHallId)
            .orElseThrow(() -> new BusinessException(StageErrorCode.ARTHALL_NOT_FOUND_FOR_STAGE));

    if (artHall.isInactive()) {
      throw new BusinessException(StageErrorCode.ARTHALL_INACTIVE_FOR_STAGE);
    }
  }
}
