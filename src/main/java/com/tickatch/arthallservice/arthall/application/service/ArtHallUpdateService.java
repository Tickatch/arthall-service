package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.application.dto.ArtHallResult;
import com.tickatch.arthallservice.arthall.application.dto.ArtHallUpdateCommand;
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
  public ArtHallResult update(ArtHallUpdateCommand command) {

    ArtHall artHall =
        artHallRepository
            .findByIdAndDeletedAtIsNull(command.id())
            .orElseThrow(
                () -> new BusinessException(ArtHallErrorCode.ARTHALL_NOT_FOUND, command.id()));

    artHall.updateInfo(command.name(), command.address(), ArtHallStatus.valueOf(command.status()));

    return new ArtHallResult(
        artHall.getId(),
        artHall.getName().getValue(),
        artHall.getAddress().getValue(),
        artHall.getStatus().name());
  }
}
