package com.tickatch.arthallservice.stage.application.port;

import java.time.LocalDateTime;
import java.util.UUID;

public interface StageLogPort {

  void publishAction(
      Long stageId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt);
}
