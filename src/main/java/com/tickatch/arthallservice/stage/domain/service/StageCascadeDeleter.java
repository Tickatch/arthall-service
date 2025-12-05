package com.tickatch.arthallservice.stage.domain.service;

public interface StageCascadeDeleter {
  void deleteRelatedEntities(Long stageId, String deletedBy);
}
