package com.tickatch.arthallservice.stageseat.application.service;

import com.tickatch.arthallservice.stageseat.application.dto.StageSeatListResult;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageSeatListService {

  private final StageSeatQueryRepository stageSeatQueryRepository;

  public List<StageSeatListResult> getList(Long stageId, String seatNumber) {

    List<StageSeat> seats =
        stageSeatQueryRepository.findByStageIdAndSeatNumberLike(stageId, seatNumber);

    return seats.stream().map(StageSeatListResult::from).toList();
  }

  // StageId로 해당하는 StageSeat 전체 조회
  public List<StageSeatListResult> getAllByStageId(Long stageId) {
    List<StageSeat> seats = stageSeatQueryRepository.findAllByStageId(stageId);

    return seats.stream().map(StageSeatListResult::from).toList();
  }
}
