package com.tickatch.arthallservice.stage.infrastructure.messaging.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record StageLogEvent(
    UUID eventId,
    String domainType, // "STAGE"
    Long domainId,
    String actionType,
    String actorType,
    UUID actorUserId,
    LocalDateTime occurredAt) {}
