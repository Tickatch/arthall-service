package com.tickatch.arthallservice.stage.infrastructure.repository;

import static com.tickatch.arthallservice.stage.domain.QStage.stage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickatch.arthallservice.stage.domain.Stage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StageQueryRepository {

  private final JPAQueryFactory queryFactory;

  public Optional<Stage> findDetail(Long stageId) {
    Stage result = queryFactory
        .selectFrom(stage)
        .where(
            stage.stageId.eq(stageId),
            stage.deletedAt.isNull()
        )
        .fetchOne();

    return Optional.ofNullable(result);
  }
}
