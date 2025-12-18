package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.global.config.security.ActorExtractor;
import com.tickatch.arthallservice.stage.application.port.StageLogPort;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import com.tickatch.arthallservice.stage.domain.service.StageCascadeDeleter;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StageDeleteService {

  private final StageRepository stageRepository;
  private final StageCascadeDeleter cascadeDeleter;
  private final StageLogPort stageLogPort;

  @Transactional
  public void delete(Long stageId, String deletedBy) {

    Stage stage =
        stageRepository
            .findByStageIdAndDeletedAtIsNull(stageId)
            .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));

    cascadeDeleter.deleteRelatedEntities(stageId, deletedBy);

    stage.softDelete(deletedBy);

    // 로그 이벤트 발행
    try {
      ActorExtractor.ActorInfo actor = ActorExtractor.extract();

      stageLogPort.publishAction(
          stage.getStageId(),
          "DELETED",
          actor.actorType(),
          actor.actorUserId(),
          java.time.LocalDateTime.now()
      );
    } catch (Exception e) {
      log.warn("스테이지 로그 저장 실패. stageId={}", stage.getStageId(), e);
    }
  }
}
