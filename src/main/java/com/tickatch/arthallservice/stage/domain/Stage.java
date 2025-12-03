package com.tickatch.arthallservice.stage.domain;

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
@Table(name = "p_stage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stage extends AbstractAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long stageId;

  @Column(name = "arthall_id", nullable = false)
  private Long artHallId;

  @Embedded private StageName name;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private StageStatus status;

  public Stage(Long artHallId, StageName name, StageStatus status) {
    this.artHallId = artHallId;
    this.name = name;
    this.status = status;
  }

  public static Stage register(Long artHallId, String name, StageStatus status) {
    return new Stage(artHallId, StageName.of(name), status);
  }

  public void updateInfo(String name, StageStatus status) {
    this.name = StageName.of(name);
    this.status = status;
  }

  public void changeStatus(StageStatus newStatus) {
    this.status = newStatus;
  }

  public void softDelete(String deletedBy) {
    delete(deletedBy);
  }

  public boolean isInactive() {
    return this.status == StageStatus.INACTIVE;
  }
}
