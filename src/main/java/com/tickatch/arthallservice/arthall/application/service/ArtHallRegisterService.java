package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallRegisterCommand;
import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.application.port.ArtHallLogPort;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.ArtHallStatus;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import com.tickatch.arthallservice.global.config.security.ActorExtractor;
import com.tickatch.arthallservice.global.config.security.ActorExtractor.ActorInfo;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtHallRegisterService {

  private final ArtHallRepository artHallRepository;
  private final ArtHallLogPort artHallLogPort;

  @Transactional
  public ArtHallResult register(ArtHallRegisterCommand command) {

    ArtHallStatus status = ArtHallStatus.valueOf(command.status());

    ArtHall artHall = ArtHall.register(command.name(), command.address(), status);

    ArtHall saved = artHallRepository.save(artHall);

    // ===== 로그 이벤트 발행 =====
    try {
      ActorInfo actor = ActorExtractor.extract();

      artHallLogPort.publishStatusChanged(
          saved.getArtHallId(),
          "CREATED",
          actor.actorType(),
          actor.actorUserId(),
          LocalDateTime.now());

    } catch (Exception e) {
      log.warn("아트홀 생성 로그 저장 실패. artHallId={}", saved.getArtHallId(), e);
    }

    return new ArtHallResult(
        saved.getArtHallId(),
        saved.getName().getValue(),
        saved.getAddress().getValue(),
        saved.getStatus().name());
  }
}
