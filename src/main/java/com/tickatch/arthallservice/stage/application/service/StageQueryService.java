package com.tickatch.arthallservice.stage.application.service;

import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import com.tickatch.arthallservice.stage.domain.repository.StageQueryRepository;
import io.github.tickatch.common.error.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageQueryService {

  private final StageQueryRepository stageQueryRepository;

  @Transactional(readOnly = true)
  public Stage getStageDetail(Long stageId) {
    return stageQueryRepository
        .findDetail(stageId)
        .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<Stage> getStageListBy(Long artHallId) {
    return stageQueryRepository.findByArtHallId(artHallId);
  }

  @Transactional(readOnly = true)
  public Page<Stage> getStageList(Long artHallId, String keyword, Pageable pageable) {
    return stageQueryRepository.findByArtHallIdAndKeyword(artHallId, keyword, pageable);
  }

  @Transactional(readOnly = true)
  public void getActiveStage(Long stageId) {

    Stage stage =
        stageQueryRepository
            .findDetail(stageId)
            .orElseThrow(() -> new BusinessException(StageErrorCode.STAGE_NOT_FOUND));

    if (stage.isInactive()) {
      throw new BusinessException(StageErrorCode.STAGE_INACTIVE);
    }
  }
}
