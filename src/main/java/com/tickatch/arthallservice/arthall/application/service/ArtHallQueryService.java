package com.tickatch.arthallservice.arthall.application.service;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import com.tickatch.arthallservice.arthall.domain.service.ArtHallFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtHallQueryService {

  private final ArtHallFinder artHallFinder;

  public ArtHall getArtHallDetail(Long id) {
    return artHallFinder.findDetailById(id);
  }

  public Page<ArtHall> getArtHallList(String keyword, Pageable pageable) {
    return artHallFinder.findAll(keyword, pageable);
  }
}
