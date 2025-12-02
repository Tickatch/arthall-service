package com.tickatch.arthallservice.arthall.domain.exception;

import io.github.tickatch.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArtHallErrorCode implements ErrorCode {
  ARTHALL_NOT_FOUND(404, "ARTHALL_NOT_FOUND"),
  ARTHALL_INACTIVE(409, "ARTHALL_INACTIVE"),
  INVALID_ARTHALL_NAME(422, "INVALID_ARTHALL_NAME"),
  INVALID_ARTHALL_ADDRESS(422, "INVALID_ARTHALL_ADDRESS");

  private final int status;
  private final String code;
}
