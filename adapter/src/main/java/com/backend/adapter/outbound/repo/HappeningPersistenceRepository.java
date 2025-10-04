package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.HappeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HappeningPersistenceRepository extends JpaRepository<HappeningEntity, Long> {
}
