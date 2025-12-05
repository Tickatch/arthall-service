package com.tickatch.arthallservice.stageseat.application.dto;

import com.tickatch.arthallservice.stageseat.domain.StageSeatStatus;

public record StageSeatRegisterCommand(
    String seatNumber, StageSeatStatus status, int row, int col, float vectorX, float vectorY) {}
