package com.tickatch.arthallservice.arthall.infrastructure.query;

import static com.tickatch.arthallservice.arthall.domain.QArtHall.artHall;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickatch.arthallservice.arthall.domain.ArtHall;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtHallQuery {

  private final JPAQueryFactory query;

  // 단건조회
  public Optional<ArtHall> findDetailByArtHallId(Long id) {

    ArtHall entity =
        query
            .selectFrom(artHall)
            .where(artHall.artHallId.eq(id), artHall.deletedAt.isNull())
            .fetchOne();

    return Optional.ofNullable(entity);
  }

  // 목록 조회
  public Page<ArtHall> findAll(String keyword, Pageable pageable) {

    BooleanBuilder builder = new BooleanBuilder().and(artHall.deletedAt.isNull());

    if (keyword != null && !keyword.isBlank()) {
      builder.and(
          artHall
              .name
              .value
              .containsIgnoreCase(keyword)
              .or(artHall.address.value.containsIgnoreCase(keyword)));
    }

    List<ArtHall> contents =
        query
            .selectFrom(artHall)
            .where(builder)
            .orderBy(artHall.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    Long total = query.select(artHall.count()).from(artHall).where(builder).fetchOne();

    if (total == null) {
      total = 0L;
    }

    return new PageImpl<>(contents, pageable, total);
  }
}
