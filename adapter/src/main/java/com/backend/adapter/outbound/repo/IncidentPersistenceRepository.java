package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentPersistenceRepository extends JpaRepository<IncidentEntity, Long> {
}
