package com.tickatch.arthallservice.stageseat.domain.repository;

import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.StageSeatNumber;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageSeatRepository extends JpaRepository<StageSeat, Long> {

  Optional<StageSeat> findByStageIdAndSeatNumberAndDeletedAtIsNull(
      Long stageId, StageSeatNumber seatNumber);

  Optional<StageSeat> findByStageIdAndLocationRowAndLocationColAndDeletedAtIsNull(
      Long stageId, int row, int col);

  List<StageSeat> findAllByStageSeatIdInAndDeletedAtIsNull(List<Long> stageSeatIds);
}
