package com.tickatch.arthallservice.arthall.domain.service;

public interface ArtHallCascadeDeleter {
  void deleteRelatedEntities(Long artHallId, String deletedBy);
}
