package com.tickatch.arthallservice.stageseat.application.service;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatListResult;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageSeatListService {

  private final StageSeatQueryRepository stageSeatQueryRepository;

  public Page<StageSeatListResult> getList(Long stageId, String keyword, Pageable pageable) {

    Page<StageSeat> page =
        stageSeatQueryRepository.findByStageIdAndKeyword(stageId, keyword, pageable);

    return page.map(StageSeatListResult::from);
  }
}
