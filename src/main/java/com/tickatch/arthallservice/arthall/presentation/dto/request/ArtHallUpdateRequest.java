package com.tickatch.arthallservice.arthall.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ArtHallUpdateRequest(
    @NotBlank String name, @NotBlank String address, @NotBlank String status) {}
