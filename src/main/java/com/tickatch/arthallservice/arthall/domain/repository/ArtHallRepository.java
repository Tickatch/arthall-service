package com.tickatch.arthallservice.arthall.domain.repository;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import java.util.Optional;

public interface ArtHallRepository {

  ArtHall save(ArtHall artHall);

  Optional<ArtHall> findByIdAndDeletedAtIsNull(Long id);

  void delete(ArtHall artHall);
}
