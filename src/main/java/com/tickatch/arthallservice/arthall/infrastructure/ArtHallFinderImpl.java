package com.tickatch.arthallservice.arthall.infrastructure;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.exception.ArtHallErrorCode;
import com.tickatch.arthallservice.arthall.domain.service.ArtHallFinder;
import com.tickatch.arthallservice.arthall.infrastructure.query.ArtHallQuery;
import io.github.tickatch.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtHallFinderImpl implements ArtHallFinder {

  private final ArtHallQuery artHallQuery;

  @Override
  public ArtHall findDetailByArtHallId(Long id) {
    return artHallQuery
        .findDetailByArtHallId(id)
        .orElseThrow(() -> new BusinessException(ArtHallErrorCode.ARTHALL_NOT_FOUND, id));
  }

  @Override
  public Page<ArtHall> findAll(String keyword, Pageable pageable) {
    return artHallQuery.findAll(keyword, pageable);
  }
}
