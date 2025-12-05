package com.tickatch.arthallservice.stageseat.domain;

import com.tickatch.arthallservice.stageseat.domain.exception.StageSeatErrorCode;
import io.github.tickatch.common.error.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode
public class StageSeatNumber {

  private static final String PATTERN = "^[A-Z](?:[1-9]?[0-9]{1,2}|0)$";

  @Column(name = "seatnumber", nullable = false, length = 10)
  private String value;

  private StageSeatNumber(String value) {
    this.value = value;
  }

  public static StageSeatNumber of(String value) {
    validate(value);
    return new StageSeatNumber(value);
  }

  private static void validate(String value) {
    validateNotBlank(value);
    validatePattern(value);
  }

  private static void validateNotBlank(String value) {
    if (value == null || value.isBlank()) {
      throw new BusinessException(StageSeatErrorCode.INVALID_SEAT_NUMBER, value);
    }
  }

  private static void validatePattern(String value) {
    if (!value.matches(PATTERN)) {
      throw new BusinessException(StageSeatErrorCode.INVALID_SEAT_NUMBER_FORMAT, value);
    }
  }
}
