package com.tickatch.arthallservice.stage.infrastructure.repository;

import com.tickatch.arthallservice.stage.domain.Stage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageJpaRepository extends JpaRepository<Stage, Long> {

  Optional<Stage> findByStageIdAndDeletedAtIsNull(Long id);
}
