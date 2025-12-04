package com.tickatch.arthallservice.stage.domain.exception;

import io.github.tickatch.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StageErrorCode implements ErrorCode {
  STAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "STAGE_NOT_FOUND"),
  STAGE_INACTIVE(HttpStatus.CONFLICT.value(), "STAGE_INACTIVE"),
  INVALID_STAGE_NAME(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_STAGE_NAME"),
  ARTHALL_NOT_FOUND_FOR_STAGE(HttpStatus.NOT_FOUND.value(), "ARTHALL_NOT_FOUND_FOR_STAGE"),
  ARTHALL_INACTIVE_FOR_STAGE(HttpStatus.CONFLICT.value(), "ARTHALL_INACTIVE_FOR_STAGE");

  private final int status;
  private final String code;
}
