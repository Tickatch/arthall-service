package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.global.config.security.ActorExtractor;
import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.application.dto.StageStatusUpdateCommand;
import com.tickatch.arthallservice.stage.application.port.StageLogPort;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.StageStatus;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import io.github.tickatch.common.error.BusinessException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StageStatusUpdateService {

  private final StageRepository stageRepository;
  private final StageLogPort stageLogPort;

  @Transactional
  public StageResult updateStatus(StageStatusUpdateCommand command) {

    Stage stage =
        stageRepository
            .findByStageIdAndDeletedAtIsNull(command.stageId())
            .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));

    StageStatus newStatus = command.status();
    stage.changeStatus(newStatus);

    // 로그 이벤트 발행
    try {
      ActorExtractor.ActorInfo actor = ActorExtractor.extract();

      String actionType = newStatus == StageStatus.ACTIVE ? "ACTIVATED" : "INACTIVATED";

      stageLogPort.publishStatusChanged(
          stage.getStageId(),
          actionType,
          actor.actorType(),
          actor.actorUserId(),
          LocalDateTime.now());

    } catch (Exception e) {
      log.warn("스테이지 로그 저장 실패. stageId={}", stage.getStageId(), e);
    }

    return StageResult.from(stage);
  }
}
