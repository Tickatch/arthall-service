package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.arthall.application.service.ArtHallQueryService;
import com.tickatch.arthallservice.stage.application.dto.StageRegisterCommand;
import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.StageName;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageRegisterService {

  private final StageRepository stageRepository;
  private final ArtHallQueryService artHallQueryService;

  @Transactional
  public StageResult register(StageRegisterCommand command) {

    validateArtHallIsActive(command.artHallId());

    StageName name = StageName.of(command.name());

    Stage stage = Stage.register(command.artHallId(), name, command.status());

    Stage saved = stageRepository.save(stage);

    return StageResult.from(saved);
  }

  private void validateArtHallIsActive(Long artHallId) {
    artHallQueryService.getActiveArtHall(artHallId);
  }
}
