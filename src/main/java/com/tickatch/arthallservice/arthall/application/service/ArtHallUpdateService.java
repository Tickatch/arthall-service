package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.ArtHallStatus;
import com.tickatch.arthallservice.arthall.domain.exception.ArtHallErrorCode;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtHallUpdateService {

  private final ArtHallRepository artHallRepository;

  @Transactional
  public ArtHallResult update(Long id, String name, String address, String status) {

    ArtHall artHall = artHallRepository.findActiveById(id)
        .orElseThrow(() ->
            new BusinessException(ArtHallErrorCode.ARTHALL_NOT_FOUND, id)
        );

    artHall.updateInfo(name, address, ArtHallStatus.valueOf(status));

    return new ArtHallResult(
        artHall.getId(),
        artHall.getName().getValue(),
        artHall.getAddress().getValue(),
        artHall.getStatus().name());
  }
}
