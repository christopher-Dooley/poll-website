package com.example.pollwebsite;

import com.example.pollwebsite.dtos.PollDTO;
import com.example.pollwebsite.dtos.VoteDTO;
import com.example.pollwebsite.entities.PollEntity;
import com.example.pollwebsite.entities.VoteEntity;

public class DataTransformer {

    public static PollEntity pollDTOToEntity(PollDTO dto) {
        return new PollEntity(
                dto.getUuid(), 
                dto.getName(), 
                dto.getQuestion(),
                dto.getOptions(), 
                dto.getNumberOfVotes(),
                dto.isCurrent());
    }

    public static PollDTO pollEntityToDTO(PollEntity entity) {
        return new PollDTO(
                entity.getUuid(), 
                entity.getName(), 
                entity.getQuestion(), 
                entity.getOptions(), 
                entity.getNumberOfVotes(),
                entity.isCurrent());
    }

    public static VoteEntity voteDTOToEntity(VoteDTO dto) {
        return new VoteEntity(
                dto.getUuid(),
                dto.getPollName(),
                dto.getOption(),
                dto.getVoteNumber(),
                dto.getTimestamp()
        );
    }

    public static VoteDTO voteEntityToDTO(VoteEntity entity) {
        return new VoteDTO(
                entity.getUuid(),
                entity.getPollName(),
                entity.getOption(),
                entity.getVoteNumber(),
                entity.getTimestamp()
        );
    }
}
