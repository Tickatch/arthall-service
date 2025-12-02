package com.tickatch.arthallservice.arthall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ArtHallId {

  @Column(name = "id")
  private Long id;

  private ArtHallId(Long id) {
    this.id = id;
  }

  public static ArtHallId of(Long id) {
    return new ArtHallId(id);
  }
}
