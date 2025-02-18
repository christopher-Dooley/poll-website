package com.example.pollwebsite.repository;

import com.example.pollwebsite.entities.PollEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface PollRepository extends Repository<PollEntity, UUID> {
    PollEntity save(PollEntity pollEntity);

    Optional<PollEntity> findByName(String name);

    Optional<PollEntity> findById(UUID uuid);

    Optional<PollEntity> findByCurrent(boolean current);
}
