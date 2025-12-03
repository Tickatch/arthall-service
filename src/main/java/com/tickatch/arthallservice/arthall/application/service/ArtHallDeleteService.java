package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.exception.ArtHallErrorCode;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtHallDeleteService {

  private final ArtHallRepository artHallRepository;

  @Transactional
  public void delete(Long id, String deletedBy) {

    ArtHall artHall =
        artHallRepository
            .findByArtHallIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new BusinessException(ArtHallErrorCode.ARTHALL_NOT_FOUND, id));

    artHall.softDelete(deletedBy);

    artHallRepository.delete(artHall);
  }
}
