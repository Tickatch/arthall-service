package com.tickatch.arthallservice.stage.infrastructure.log.publisher;

import com.tickatch.arthallservice.global.config.RabbitMQ.RabbitMQConfig;
import com.tickatch.arthallservice.stage.application.port.StageLogPort;
import com.tickatch.arthallservice.stage.infrastructure.messaging.event.StageLogEvent;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageLogPublisher implements StageLogPort {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void publishStatusChanged(
      Long stageId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {
    StageLogEvent event =
        new StageLogEvent(
            UUID.randomUUID(), "STAGE", stageId, actionType, actorType, actorUserId, occurredAt);

    rabbitTemplate.convertAndSend(
        RabbitMQConfig.LOG_EXCHANGE, RabbitMQConfig.ROUTING_KEY_ARTHALL_LOG, event);
  }
}
