package com.tickatch.arthallservice.stageseat.infrastructure.adapter;

import static com.tickatch.arthallservice.stageseat.domain.QStageSeat.stageSeat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatQueryRepository;
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
}
