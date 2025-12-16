package com.tickatch.arthallservice.arthall.domain.exception;

import io.github.tickatch.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ArtHallErrorCode implements ErrorCode {
  ARTHALL_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ARTHALL_NOT_FOUND"),
  ARTHALL_INACTIVE(HttpStatus.CONFLICT.value(), "ARTHALL_INACTIVE"),
  INVALID_ARTHALL_NAME(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_ARTHALL_NAME"),
  INVALID_ARTHALL_ADDRESS(HttpStatus.UNPROCESSABLE_ENTITY.value(), "INVALID_ARTHALL_ADDRESS");

  private final int status;
  private final String code;
}
