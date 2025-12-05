package com.tickatch.arthallservice.stageseat.infrastructure.adapter;

import static com.tickatch.arthallservice.stageseat.domain.QStageSeat.stageSeat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickatch.arthallservice.stageseat.domain.QStageSeat;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageSeatQueryRepositoryAdapter implements StageSeatQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<StageSeat> findDetailByStageSeatId(Long stageSeatId) {
    StageSeat result =
        queryFactory
            .selectFrom(stageSeat)
            .where(stageSeat.stageSeatId.eq(stageSeatId), stageSeat.deletedAt.isNull())
            .fetchOne();

    return Optional.ofNullable(result);
  }

  @Override
  public List<StageSeat> findByStageIdAndSeatNumberLike(Long stageId, String seatNumber) {

    QStageSeat stageSeat = QStageSeat.stageSeat;

    return queryFactory
        .selectFrom(stageSeat)
        .where(
            stageSeat.stageId.eq(stageId),
            stageSeat.deletedAt.isNull(),
            seatNumber != null && !seatNumber.isBlank()
                ? stageSeat.seatNumber.value.like("%" + seatNumber + "%")
                : null)
        .fetch();
  }

  // StageId로 삭제되지 않은 StageSeatId 전체 조회
  @Override
  public List<StageSeat> findAllByStageId(Long stageId) {

    QStageSeat stageSeat = QStageSeat.stageSeat;

    return queryFactory
        .selectFrom(stageSeat)
        .where(stageSeat.stageId.eq(stageId), stageSeat.deletedAt.isNull())
        .fetch();
  }
}
