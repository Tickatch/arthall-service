package com.tickatch.arthallservice.arthall.domain;

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
@Table(name = "p_arthall")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtHall extends AbstractAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long artHallId;

  @Embedded private ArtHallName name;

  @Embedded private ArtHallAddress address;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private ArtHallStatus status;

  public ArtHall(ArtHallName name, ArtHallAddress address, ArtHallStatus status) {
    this.name = name;
    this.address = address;
    this.status = status;
  }

  public static ArtHall register(String name, String address, ArtHallStatus status) {
    return new ArtHall(ArtHallName.of(name), ArtHallAddress.of(address), status);
  }

  public void updateInfo(String name, String address, ArtHallStatus status) {
    this.name = ArtHallName.of(name);
    this.address = ArtHallAddress.of(address);
    this.status = status;
  }

  public void changeStatus(ArtHallStatus newStatus) {
    this.status = newStatus;
  }

  public void softDelete(String deletedBy) {
    delete(deletedBy);
  }

  public boolean isInactive() {
    return this.status == ArtHallStatus.INACTIVE;
  }
}
