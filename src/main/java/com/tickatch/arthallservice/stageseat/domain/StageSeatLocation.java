package com.tickatch.arthallservice.stageseat.domain;

import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import io.github.tickatch.common.error.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class StageSeatLocation {

  @Column(name = "row", nullable = false)
  private int row;

  @Column(name = "col", nullable = false)
  private int col;

  @Embedded private VectorValue vector;

  private StageSeatLocation(int row, int col, VectorValue vector) {
    this.row = row;
    this.col = col;
    this.vector = vector;
  }

  public static StageSeatLocation of(int row, int col, VectorValue vector) {
    if (row < 0) {
      throw new BusinessException(StageSeatErrorCode.INVALID_ROW, row);
    }
    if (col < 0) {
      throw new BusinessException(StageSeatErrorCode.INVALID_COL, col);
    }
    if (vector == null) {
      throw new BusinessException(StageSeatErrorCode.INVALID_VECTOR, (Object) null);
    }
    return new StageSeatLocation(row, col, vector);
  }
}
