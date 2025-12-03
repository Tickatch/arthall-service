package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.ArtHallStatus;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import com.tickatch.arthallservice.arthall.presentation.dto.request.ArtHallRegisterRequest;
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
  public ArtHallResult register(ArtHallRegisterRequest request) {

    ArtHallStatus status = ArtHallStatus.valueOf(request.status());

    ArtHall artHall = ArtHall.register(request.name(), request.address(), status);

    ArtHall saved = artHallRepository.save(artHall);

    return new ArtHallResult(
        saved.getId(),
        saved.getName().getValue(),
        saved.getAddress().getValue(),
        saved.getStatus().name());
  }
}
