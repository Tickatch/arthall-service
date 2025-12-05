package com.tickatch.arthallservice.stageseat.application.dto;

public record StageSeatUpdateCommand(
    Long stageSeatId, int row, int col, float vectorX, float vectorY) {}
