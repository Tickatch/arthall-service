package com.tickatch.arthallservice.arthall.domain.service;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtHallFinder {

  ArtHall findDetailByArtHallId(Long id);

  Page<ArtHall> findAll(String keyword, Pageable pageable);
}
