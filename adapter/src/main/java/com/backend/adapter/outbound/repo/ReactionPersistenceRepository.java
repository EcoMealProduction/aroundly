package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionPersistenceRepository extends JpaRepository<ReactionEntity, Long> {
}
