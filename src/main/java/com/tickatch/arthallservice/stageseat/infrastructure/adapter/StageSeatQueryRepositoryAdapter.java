package com.tickatch.arthallservice.stageseat.infrastructure.adapter;

import static com.tickatch.arthallservice.stageseat.domain.QStageSeat.stageSeat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickatch.arthallservice.stageseat.domain.StageSeat;
import com.tickatch.arthallservice.stageseat.domain.repository.StageSeatQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
  public Page<StageSeat> findByStageIdAndKeyword(Long stageId, String keyword, Pageable pageable) {

    List<StageSeat> content =
        queryFactory
            .selectFrom(stageSeat)
            .where(
                stageSeat.stageId.eq(stageId),
                stageSeat.deletedAt.isNull(),
                keyword == null ? null : stageSeat.seatNumber.value.containsIgnoreCase(keyword))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(stageSeat.createdAt.desc())
            .fetch();

    Long count =
        queryFactory
            .select(stageSeat.count())
            .from(stageSeat)
            .where(
                stageSeat.stageId.eq(stageId),
                stageSeat.deletedAt.isNull(),
                keyword == null ? null : stageSeat.seatNumber.value.containsIgnoreCase(keyword))
            .fetchOne();

    long total = (count == null) ? 0L : count;

    return new PageImpl<>(content, pageable, total);
  }
}
