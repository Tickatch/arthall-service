package com.tickatch.arthallservice.arthall.infrastructure.log.publisher;

import com.tickatch.arthallservice.arthall.application.port.ArtHallLogPort;
import com.tickatch.arthallservice.arthall.infrastructure.messaging.event.ArtHallLogEvent;
import com.tickatch.arthallservice.global.config.RabbitMQ.RabbitMQConfig;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArtHallLogPublisher implements ArtHallLogPort {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void publishAction(
      Long artHallId,
      String actionType,
      String actorType,
      UUID actorUserId,
      LocalDateTime occurredAt) {
    ArtHallLogEvent event =
        new ArtHallLogEvent(
            UUID.randomUUID(),
            "ARTHALL",
            artHallId,
            actionType,
            actorType,
            actorUserId,
            occurredAt);

    rabbitTemplate.convertAndSend(
        RabbitMQConfig.LOG_EXCHANGE, RabbitMQConfig.ROUTING_KEY_ARTHALL_LOG, event);
  }
}
