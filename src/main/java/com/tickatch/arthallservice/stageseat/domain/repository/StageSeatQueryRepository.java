package com.tickatch.arthallservice.stageseat.domain.repository;

import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import java.util.List;
import java.util.Optional;

public interface StageSeatQueryRepository {

  Optional<StageSeat> findDetailByStageSeatId(Long seatId);

  List<StageSeat> findByStageIdAndSeatNumberLike(Long stageId, String seatNumber);

  List<StageSeat> findAllByStageId(Long stageId);
}
