package com.tickatch.arthallservice.arthall.infrastructure;

import com.tickatch.arthallservice.arthall.domain.service.ArtHallCascadeDeleter;
import com.tickatch.arthallservice.stage.application.service.StageDeleteService;
import com.tickatch.arthallservice.stage.application.service.StageQueryService;
import com.tickatch.arthallservice.stage.domain.Stage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArtHallRelatedEntitiesDeleter implements ArtHallCascadeDeleter {

  private final StageQueryService stageQueryService;
  private final StageDeleteService stageDeleteService;

  @Override
  @Transactional
  public void deleteRelatedEntities(Long artHallId, String deletedBy) {
    List<Stage> stages = stageQueryService.getStageListBy(artHallId);

    stages.forEach(stage -> stageDeleteService.delete(stage.getStageId(), deletedBy));
  }
}
