package com.tickatch.arthallservice.stage.domain.repository;

import com.tickatch.arthallservice.stage.domain.Stage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StageQueryRepository {

  Optional<Stage> findDetail(Long stageId);

  List<Stage> findByArtHallId(Long artHallId);

  Page<Stage> findByArtHallIdAndKeyword(Long artHallId, String keyword, Pageable pageable);
}
