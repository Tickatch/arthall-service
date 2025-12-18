package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.application.dto.ArtHallStatusUpdateCommand;
import com.tickatch.arthallservice.arthall.application.port.ArtHallLogPort;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.ArtHallStatus;
import com.tickatch.arthallservice.arthall.domain.exception.ArtHallErrorCode;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import com.tickatch.arthallservice.global.config.security.ActorExtractor;
import com.tickatch.arthallservice.global.config.security.ActorExtractor.ActorInfo;
import io.github.tickatch.common.error.BusinessException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtHallStatusUpdateService {

  private final ArtHallRepository artHallRepository;
  private final ArtHallLogPort artHallLogPort;

  @Transactional
  public ArtHallResult updateStatus(ArtHallStatusUpdateCommand command) {

    ArtHall artHall =
        artHallRepository
            .findByArtHallIdAndDeletedAtIsNull(command.id())
            .orElseThrow(
                () -> new BusinessException(ArtHallErrorCode.ARTHALL_NOT_FOUND, command.id()));

    ArtHallStatus newStatus = ArtHallStatus.valueOf(command.status());

    artHall.changeStatus(newStatus);

    // 로그 이벤트 발행
    try {
      ActorInfo actor = ActorExtractor.extract();

      String actionType = newStatus == ArtHallStatus.ACTIVE ? "ACTIVATED" : "INACTIVATED";

      artHallLogPort.publishAction(
          artHall.getArtHallId(),
          actionType,
          actor.actorType(),
          actor.actorUserId(),
          LocalDateTime.now());

    } catch (Exception e) {
      log.warn("아트홀 로그 저장 실패. artHallId={}", artHall.getArtHallId(), e);
    }

    return new ArtHallResult(
        artHall.getArtHallId(),
        artHall.getName().getValue(),
        artHall.getAddress().getValue(),
        artHall.getStatus().name());
  }
}
