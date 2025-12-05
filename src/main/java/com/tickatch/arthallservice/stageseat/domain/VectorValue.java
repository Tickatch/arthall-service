package com.tickatch.arthallservice.stageseat.domain;

import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import io.github.tickatch.common.error.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode
public class VectorValue {

  @Column(name = "vector", columnDefinition = "vector(2)", nullable = false)
  @JdbcType(PostgresVectorType.class)
  @JdbcTypeCode(SqlTypes.OTHER)
  private float[] values;

  private VectorValue(float[] values) {
    this.values = values;
  }

  public static VectorValue of(float x, float y) {
    float[] values = new float[] {x, y};
    validate(values);
    return new VectorValue(values);
  }

  private static void validate(float[] values) {
    validateNotNull(values);
    validateLength(values);
  }

  private static void validateNotNull(float[] values) {
    if (values == null) {
      throw new BusinessException(StageSeatErrorCode.INVALID_VECTOR, (Object) null);
    }
  }

  private static void validateLength(float[] values) {
    if (values.length != 2) {
      throw new BusinessException(StageSeatErrorCode.INVALID_VECTOR_LENGTH, (Object) null);
    }
  }
}
