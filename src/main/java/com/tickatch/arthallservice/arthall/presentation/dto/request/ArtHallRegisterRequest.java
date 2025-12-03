package com.tickatch.arthallservice.arthall.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ArtHallRegisterRequest(
    @NotBlank String name, @NotBlank String address, @NotBlank String status) {}
