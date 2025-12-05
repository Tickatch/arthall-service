package com.tickatch.arthallservice.stageseat.domain.repository;

import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StageSeatQueryRepository {

  Optional<StageSeat> findDetailByStageSeatId(Long seatId);

  Page<StageSeat> findByStageIdAndKeyword(Long stageId, String keyword, Pageable pageable);
}
