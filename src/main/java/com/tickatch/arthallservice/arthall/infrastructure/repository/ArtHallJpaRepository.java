package com.tickatch.arthallservice.arthall.infrastructure.repository;

import com.tickatch.arthallservice.arthall.domain.ArtHall;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtHallJpaRepository extends JpaRepository<ArtHall, Long> {

  Optional<ArtHall> findByIdAndDeletedAtIsNull(Long id);
}
