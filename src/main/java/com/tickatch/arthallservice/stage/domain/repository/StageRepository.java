package com.tickatch.arthallservice.stage.domain.repository;

import com.tickatch.arthallservice.stage.domain.Stage;
import java.util.Optional;

public interface StageRepository {

  Stage save(Stage stage);

  Optional<Stage> findByStageIdAndDeletedAtIsNull(Long id);
}
