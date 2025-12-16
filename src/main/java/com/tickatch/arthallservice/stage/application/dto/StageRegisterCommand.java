package com.tickatch.arthallservice.stage.application.dto;

import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageRegisterCommand(Long artHallId, String name, StageStatus status) {}
