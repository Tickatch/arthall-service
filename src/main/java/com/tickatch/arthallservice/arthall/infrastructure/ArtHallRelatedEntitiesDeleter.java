package com.tickatch.arthallservice.arthall.infrastructure;

import com.tickatch.arthallservice.arthall.domain.service.ArtHallCascadeDeleter;
import com.tickatch.arthallservice.global.config.security.ActorExtractor;
import com.tickatch.arthallservice.stage.application.port.StageLogPort;
import com.tickatch.arthallservice.stage.application.service.StageDeleteService;
import com.tickatch.arthallservice.stage.application.service.StageQueryService;
import com.tickatch.arthallservice.stage.domain.Stage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtHallRelatedEntitiesDeleter implements ArtHallCascadeDeleter {

  private final StageQueryService stageQueryService;
  private final StageDeleteService stageDeleteService;
  private final StageLogPort stageLogPort;

  @Override
  @Transactional
  public void deleteRelatedEntities(Long artHallId, String deletedBy) {
    List<Stage> stages = stageQueryService.getStageListBy(artHallId);

    for (Stage stage : stages) {
      // 1. 스테이지 삭제
      stageDeleteService.delete(stage.getStageId(), deletedBy);

      // 2. 삭제 완료 로그 발행
      try {
        ActorExtractor.ActorInfo actor = ActorExtractor.extract();

        stageLogPort.publishAction(
            stage.getStageId(),
            "CASCADE_DELETED",
            actor.actorType(),
            actor.actorUserId(),
            LocalDateTime.now());
      } catch (Exception e) {
        log.warn(
            "아트홀 연관 스테이지 삭제 로그 발행 실패. artHallId={}, stageId={}", artHallId, stage.getStageId(), e);
      }
    }
  }
}
