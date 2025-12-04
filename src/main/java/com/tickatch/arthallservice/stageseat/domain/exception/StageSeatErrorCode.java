package com.tickatch.arthallservice.stageseat.domain.exception;

import io.github.tickatch.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StageSeatErrorCode implements ErrorCode {
  INVALID_ROW(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_ROW"),
  INVALID_COL(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_COL"),

  INVALID_SEAT_NUMBER(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_NUMBER"),
  INVALID_SEAT_NUMBER_FORMAT(
      HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_NUMBER_FORMAT"),

  INVALID_VECTOR(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_VECTOR"),
  INVALID_VECTOR_LENGTH(
      HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_VECTOR_LENGTH");

  private final int status;
  private final String code;
}
