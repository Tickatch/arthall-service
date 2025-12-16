package com.tickatch.arthallservice.stage.infrastructure;

import com.tickatch.arthallservice.stage.domain.service.StageCascadeDeleter;
import com.tickatch.arthallservice.stageseat.application.dto.StageSeatListResult;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatDeleteService;
import com.tickatch.arthallservice.stageseat.application.service.StageSeatListService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StageRelatedEntitiesDeleter implements StageCascadeDeleter {

  private final StageSeatListService stageSeatListService;
  private final StageSeatDeleteService stageSeatDeleteService;

  @Override
  @Transactional
  public void deleteRelatedEntities(Long stageId, String deletedBy) {

    List<Long> stageSeatIds =
        stageSeatListService.getAllByStageId(stageId).stream()
            .map(StageSeatListResult::stageSeatId)
            .toList();

    stageSeatDeleteService.deleteAll(stageSeatIds, deletedBy);
  }
}
