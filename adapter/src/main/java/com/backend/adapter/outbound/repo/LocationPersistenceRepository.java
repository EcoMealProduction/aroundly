package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LocationPersistenceRepository extends JpaRepository<LocationEntity, Long> {
}
