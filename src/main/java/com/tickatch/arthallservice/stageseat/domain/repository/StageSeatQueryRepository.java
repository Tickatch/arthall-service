package com.tickatch.arthallservice.stageseat.domain.repository;

import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import java.util.Optional;

public interface StageSeatQueryRepository {

  Optional<StageSeat> findDetailByStageSeatId(Long seatId);
}
