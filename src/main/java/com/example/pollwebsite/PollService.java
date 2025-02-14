package com.example.pollwebsite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.pollwebsite.DataTransformer.*;

public class PollService {

    private static final Logger log = LoggerFactory.getLogger(PollService.class);
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public PollService(PollRepository pollRepository, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
    }

    public PollDTO savePoll(PollDTO pollDTO) {
        try {
            setPollUuidIfNull(pollDTO);
            setNumberOfVotesToZero(pollDTO);
            setNewPollAsCurrent(pollDTO);
            PollEntity savedPoll = pollRepository.save(pollDTOToEntity(pollDTO));
            return pollEntityToDTO(savedPoll);
        } catch (Exception e) {
            log.error("Saving new poll failed", e);
            throw e;
        }
    }

    private void setPollUuidIfNull(PollDTO dto) {
        if (dto.getUuid() == null) {
            dto.setUuid(UUID.randomUUID());
        }
    }

    private void setNumberOfVotesToZero(PollDTO dto) {
        if (dto.getNumberOfVotes() != 0) {
            dto.setNumberOfVotes(0);
        }
    }

    private void setNewPollAsCurrent(PollDTO pollDTO) {
        PollEntity lastPoll = pollRepository.findByCurrent(true);
        if (lastPoll != null) {
            lastPoll.setCurrent(false);
            pollRepository.save(lastPoll);
        }
        pollDTO.setCurrent(true);
    }

    public VoteDTO saveVote(VoteDTO voteDTO) {
        try {
            updateVoteCount(voteDTO);
            voteRepository.save(DataTransformer.voteDTOToEntity(voteDTO));
            return voteDTO;
        } catch (Exception e) {
            log.error("Saving new vote failed", e);
            throw e;
        }
    }

    private void updateVoteCount(VoteDTO voteDTO) {
        PollEntity poll = pollRepository.findByName(voteDTO.getPollName());
        if (poll != null) {
            int newNumberOfVotes = poll.getNumberOfVotes() + 1;
            poll.setNumberOfVotes(newNumberOfVotes);
            voteDTO.setVoteNumber(newNumberOfVotes);
            pollRepository.save(poll);
        } else {
            String message = String.format("Poll %s not found", voteDTO.getPollName());
            log.error(message);
            throw new PollNotFoundException(message);
        }
    }

    public PollDTO getCurrentPoll() {
        return pollEntityToDTO(pollRepository.findByCurrent(true));
    }

    public PollDTO getPollByName(String name) {
        return pollEntityToDTO(pollRepository.findByName(name));
    }

    public PollDTO getPollByUUID(UUID uuid) {
        return pollEntityToDTO(pollRepository.findByUUID(uuid));
    }

    public PollResultDTO getResultsForCurrentPoll() {
        PollEntity poll = pollRepository.findByCurrent(true);

        if (poll == null) {
            String message = "No current poll found";
            log.error(message);
            throw new PollNotFoundException(message);
        }

        return createPollResultDTO(poll);
    }

    public PollResultDTO getResultsForPoll(String pollName) {
        PollEntity poll = pollRepository.findByName(pollName);

        if (poll == null) {
            String message = String.format("Poll %s not found", pollName);
            log.error(message);
            throw new PollNotFoundException(message);
        }

        return createPollResultDTO(poll);
    }

    public PollResultDTO getResultsForPollByUUID(UUID uuid) {
        PollEntity poll = pollRepository.findByUUID(uuid);

        if (poll == null) {
            String message = String.format("Poll %s not found", uuid);
            log.error(message);
            throw new PollNotFoundException(message);
        }

        return createPollResultDTO(poll);
    }

    private PollResultDTO createPollResultDTO(PollEntity poll) {
        List<VoteEntity> votesForPoll = voteRepository.findByPollName(poll.getName());
        Map<String, Long> votesByOption = votesForPoll.stream().collect(Collectors.groupingBy(VoteEntity::getOption, Collectors.counting()));

        return new PollResultDTO(poll.getName(), votesByOption, poll.getNumberOfVotes());
    }

    public VoteDTO getVoteForPoll(String pollName, int voteNumber) {
        return voteEntityToDTO(voteRepository.findByPollNameAndVoteNumber(pollName, voteNumber));
    }

    public VoteDTO getVoteForUUID(UUID uuid) {
        return voteEntityToDTO(voteRepository.findByUUID(uuid));
    }

}
