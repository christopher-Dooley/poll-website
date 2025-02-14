package com.example.pollwebsite;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollRepository extends CrudRepository<PollEntity, UUID> {
    PollEntity save(PollEntity pollEntity);

    PollEntity findByName(String name);

    PollEntity findByUUID(UUID uuid);

    PollEntity findByCurrent(boolean current);
}
