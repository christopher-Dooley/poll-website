package com.example.pollwebsite;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends ListCrudRepository<VoteEntity, UUID> {
    VoteEntity save(VoteEntity voteEntity);

    VoteEntity findByUUID(UUID uuid);

    VoteEntity findByPollNameAndVoteNumber(String pollName, int voteNumber);

    List<VoteEntity> findByPollName(String pollName);
}
