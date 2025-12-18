package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.application.port.ArtHallLogPort;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.exception.ArtHallErrorCode;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import com.tickatch.arthallservice.arthall.domain.service.ArtHallCascadeDeleter;
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
public class ArtHallDeleteService {

  private final ArtHallRepository artHallRepository;
  private final ArtHallCascadeDeleter cascadeDeleter;
  private final ArtHallLogPort artHallLogPort;

  @Transactional
  public void delete(Long id, String deletedBy) {
    ArtHall artHall =
        artHallRepository
            .findByArtHallIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new BusinessException(ArtHallErrorCode.ARTHALL_NOT_FOUND, id));

    cascadeDeleter.deleteRelatedEntities(id, deletedBy);

    artHall.softDelete(deletedBy);

    artHallRepository.delete(artHall);

    try {
      ActorInfo actor = ActorExtractor.extract();

      artHallLogPort.publishStatusChanged(
          artHall.getArtHallId(),
          "DELETED",
          actor.actorType(),
          actor.actorUserId(),
          LocalDateTime.now()
      );
    } catch (Exception e) {
      log.warn("아트홀 삭제 로그 저장 실패. artHallId={}", artHall.getArtHallId(), e);
    }
  }
}
