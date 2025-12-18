package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.arthall.application.service.ArtHallQueryService;
import com.tickatch.arthallservice.global.config.security.ActorExtractor;
import com.tickatch.arthallservice.stage.application.dto.StageRegisterCommand;
import com.tickatch.arthallservice.stage.application.dto.StageResult;
import com.tickatch.arthallservice.stage.application.port.StageLogPort;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.StageName;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StageRegisterService {

  private final StageRepository stageRepository;
  private final ArtHallQueryService artHallQueryService;
  private final StageLogPort stageLogPort;

  @Transactional
  public StageResult register(StageRegisterCommand command) {

    validateArtHallIsActive(command.artHallId());

    StageName name = StageName.of(command.name());

    Stage stage = Stage.register(command.artHallId(), name, command.status());

    Stage saved = stageRepository.save(stage);

    // ===== 로그 이벤트 발행 =====
    try {
      ActorExtractor.ActorInfo actor = ActorExtractor.extract();

      stageLogPort.publishAction(
          saved.getStageId(),
          "CREATED",
          actor.actorType(),
          actor.actorUserId(),
          java.time.LocalDateTime.now()
      );
    } catch (Exception e) {
      log.warn("아트홀 삭제 로그 저장 실패. stageId={}", stage.getStageId(), e);
    }

    return StageResult.from(saved);
  }

  private void validateArtHallIsActive(Long artHallId) {
    artHallQueryService.getActiveArtHall(artHallId);
  }
}
