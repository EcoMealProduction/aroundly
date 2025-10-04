package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentPersistenceRepository extends JpaRepository<CommentEntity, Long> {
}
