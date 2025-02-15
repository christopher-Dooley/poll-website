package com.example.pollwebsite;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PollRepository extends CrudRepository<PollEntity, UUID> {
    PollEntity save(PollEntity pollEntity);

    Optional<PollEntity> findByName(String name);

    Optional<PollEntity> findByID(UUID uuid);

    Optional<PollEntity> findByCurrent(boolean current);
}
