package com.tickatch.arthallservice.stage.infrastructure.adapter;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.repository.StageRepository;
import com.tickatch.arthallservice.stage.infrastructure.repository.StageJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StageJpaRepositoryAdapter implements StageRepository {

  private final StageJpaRepository jpaRepository;

  @Override
  public Stage save(Stage stage) {
    return jpaRepository.save(stage);
  }

  @Override
  public Optional<Stage> findByStageIdAndDeletedAtIsNull(Long id) {
    return jpaRepository.findByStageIdAndDeletedAtIsNull(id);
  }
}
