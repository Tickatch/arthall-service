package com.tickatch.arthallservice.stageseat.domain;

import com.tickatch.arthallservice.global.domain.AbstractAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_stageseat", schema = "arthall_service")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StageSeat extends AbstractAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long stageSeatId;

  @Column(name = "stage_id", nullable = false)
  private Long stageId;

  @Embedded private StageSeatNumber seatNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private StageSeatStatus status;

  @Embedded private StageSeatLocation location;

  private StageSeat(
      Long stageId,
      StageSeatNumber seatNumber,
      StageSeatStatus status,
      StageSeatLocation location) {
    this.stageId = stageId;
    this.seatNumber = seatNumber;
    this.status = status;
    this.location = location;
  }

  public static StageSeat register(
      Long stageId, String seatNumber, StageSeatStatus status, StageSeatLocation location) {

    return new StageSeat(stageId, StageSeatNumber.of(seatNumber), status, location);
  }

  public void changeStatus(StageSeatStatus newStatus) {
    this.status = newStatus;
  }

  public void updateLocation(StageSeatLocation newLocation) {
    this.location = newLocation;
  }

  public void softDelete(String deletedBy) {
    delete(deletedBy);
  }

  public boolean isInactive() {
    return this.status == StageSeatStatus.INACTIVE;
  }
}
