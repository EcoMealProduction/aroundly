package com.backend.adapter.outbound.repo;

import com.backend.adapter.outbound.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientPersistenceRepository extends JpaRepository<ClientEntity, Long> {
}
