package com.tickatch.arthallservice.arthall.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ArtHallStatusUpdateRequest(@NotBlank String status) {}
