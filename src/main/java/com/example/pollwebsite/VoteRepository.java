package com.example.pollwebsite;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoteRepository extends Repository<VoteEntity, UUID> {
    VoteEntity save(VoteEntity voteEntity);

    Optional<VoteEntity> findById(UUID uuid);

    Optional<VoteEntity> findByPollNameAndVoteNumber(String pollName, int voteNumber);

    List<VoteEntity> findByPollName(String pollName);
}
