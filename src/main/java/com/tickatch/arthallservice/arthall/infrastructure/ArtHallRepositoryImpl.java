package com.tickatch.arthallservice.arthall.infrastructure;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.repository.ArtHallRepository;
import com.tickatch.arthallservice.arthall.infrastructure.repository.ArtHallJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtHallRepositoryImpl implements ArtHallRepository {

  private final ArtHallJpaRepository jpaRepository;

  @Override
  public ArtHall save(ArtHall artHall) {
    return jpaRepository.save(artHall);
  }

  @Override
  public Optional<ArtHall> findByIdAndDeletedAtIsNull(Long id) {
    return jpaRepository.findByIdAndDeletedAtIsNull(id);
  }

  @Override
  public void delete(ArtHall artHall) {
    jpaRepository.save(artHall);
  }
}
