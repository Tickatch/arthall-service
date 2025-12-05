package com.tickatch.arthallservice.stageseat.domain.exception;

import io.github.tickatch.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StageSeatErrorCode implements ErrorCode {
  SEAT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "SEAT_NOT_FOUND"),
  INVALID_ROW(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_ROW"),
  INVALID_COL(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_COL"),

  INVALID_SEAT_NUMBER(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_NUMBER"),
  INVALID_SEAT_NUMBER_FORMAT(
      HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_NUMBER_FORMAT"),

  INVALID_VECTOR(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_VECTOR"),
  INVALID_VECTOR_LENGTH(
      HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_SEAT_VECTOR_LENGTH"),
  SEATNUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "SEATNUMBER_ALREADY_EXISTS"),
  ROW_COL_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "ROW_COL_ALREADY_EXISTS"),

  STAGE_NOT_FOUND_FOR_SEAT_OPERATION(HttpStatus.NOT_FOUND.value(),
      "STAGE_NOT_FOUND_FOR_SEAT_OPERATION"),
  STAGE_INACTIVE_FOR_SEAT_OPERATION(
      HttpStatus.CONFLICT.value(), "STAGE_INACTIVE_FOR_SEAT_OPERATION");

  private final int status;
  private final String code;
}
