package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPersistenceRepository extends JpaRepository<EventEntity, Long> {
}
