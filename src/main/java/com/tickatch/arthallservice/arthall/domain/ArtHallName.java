package com.tickatch.arthallservice.arthall.domain;

import com.tickatch.arthallservice.arthall.domain.exception.ArtHallErrorCode;
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
public class ArtHallName {

  @Column(name = "name", nullable = false, length = 50)
  private String value;

  private ArtHallName(String value) {
    this.value = value;
  }

  public static ArtHallName of(String value) {
    if (value == null || value.isBlank()) {
      throw new BusinessException(ArtHallErrorCode.INVALID_ARTHALL_NAME, value);
    }
    return new ArtHallName(value);
  }
}
