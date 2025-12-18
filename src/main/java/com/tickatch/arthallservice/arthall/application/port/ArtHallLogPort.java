package com.tickatch.arthallservice.arthall.application.port;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ArtHallLogPort {

  void publishStatusChanged(
      Long artHallId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt);
}
