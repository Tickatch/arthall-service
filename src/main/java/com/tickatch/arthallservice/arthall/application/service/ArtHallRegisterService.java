package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallRegisterCommand;
import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.ArtHallStatus;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtHallRegisterService {

  private final ArtHallRepository artHallRepository;

  @Transactional
  public ArtHallResult register(ArtHallRegisterCommand command) {

    ArtHallStatus status = ArtHallStatus.valueOf(command.status());

    ArtHall artHall = ArtHall.register(command.name(), command.address(), status);

    ArtHall saved = artHallRepository.save(artHall);

    return new ArtHallResult(
        saved.getArtHallId(),
        saved.getName().getValue(),
        saved.getAddress().getValue(),
        saved.getStatus().name());
  }
}
