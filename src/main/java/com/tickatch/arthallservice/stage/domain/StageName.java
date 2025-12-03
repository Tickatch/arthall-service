package com.tickatch.arthallservice.stage.domain;

import com.tickatch.arthallservice.stage.domain.exception.StageErrorCode;
import io.github.tickatch.common.error.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class StageName {

  @Column(name = "name", nullable = false, length = 50)
  private String value;

  private StageName(String value) {
    this.value = value;
  }

  public static StageName of(String value) {
    if (value == null || value.isBlank()) {
      throw new BusinessException(StageErrorCode.INVALID_STAGE_NAME, value);
    }
    return new StageName(value);
  }
}
