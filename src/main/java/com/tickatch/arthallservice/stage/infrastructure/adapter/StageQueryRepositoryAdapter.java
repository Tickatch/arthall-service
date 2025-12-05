package com.tickatch.arthallservice.stage.infrastructure.adapter;

import static com.tickatch.arthallservice.stage.domain.QStage.stage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickatch.arthallservice.stage.domain.Stage;
import com.tickatch.arthallservice.stage.domain.repository.StageQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StageQueryRepositoryAdapter implements StageQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<Stage> findDetail(Long stageId) {
    Stage result =
        queryFactory
            .selectFrom(stage)
            .where(stage.stageId.eq(stageId), stage.deletedAt.isNull())
            .fetchOne();

    return Optional.ofNullable(result);
  }

  @Override
  public List<Stage> findByArtHallId(Long artHallId) {
    return queryFactory
        .selectFrom(stage)
        .where(stage.artHallId.eq(artHallId), stage.deletedAt.isNull())
        .fetch();
  }

  @Override
  public Page<Stage> findByArtHallIdAndKeyword(Long artHallId, String keyword, Pageable pageable) {

    List<Stage> content =
        queryFactory
            .selectFrom(stage)
            .where(
                stage.artHallId.eq(artHallId),
                stage.deletedAt.isNull(),
                keyword == null ? null : stage.name.value.containsIgnoreCase(keyword))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(stage.createdAt.desc())
            .fetch();

    Long count =
        queryFactory
            .select(stage.count())
            .from(stage)
            .where(
                stage.artHallId.eq(artHallId),
                stage.deletedAt.isNull(),
                keyword == null ? null : stage.name.value.containsIgnoreCase(keyword))
            .fetchOne();

    return new PageImpl<>(content, pageable, count == null ? 0 : count);
  }
}
