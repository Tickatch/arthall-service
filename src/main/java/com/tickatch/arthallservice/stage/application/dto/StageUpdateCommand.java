package com.tickatch.arthallservice.stage.application.dto;

import com.tickatch.arthallservice.stage.domain.StageStatus;

public record StageUpdateCommand(Long stageId, String name, StageStatus status) {}
