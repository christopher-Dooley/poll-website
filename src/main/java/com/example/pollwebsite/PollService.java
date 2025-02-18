package com.example.pollwebsite;

import com.example.pollwebsite.dtos.PollDTO;
import com.example.pollwebsite.dtos.PollResultDTO;
import com.example.pollwebsite.dtos.VoteDTO;
import com.example.pollwebsite.entities.PollEntity;
import com.example.pollwebsite.entities.VoteEntity;
import com.example.pollwebsite.exceptions.OptionNotFoundException;
import com.example.pollwebsite.exceptions.PollNotFoundException;
import com.example.pollwebsite.exceptions.VoteNotFoundException;
import com.example.pollwebsite.repository.PollRepository;
import com.example.pollwebsite.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.pollwebsite.DataTransformer.*;

@Service
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

    public boolean isPollLengthValid(PollDTO pollDTO) {
        return pollDTO.getOptions().size() >= 2 && pollDTO.getOptions().size() <= 7;
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
        pollRepository.findByCurrent(true).ifPresent((pollEntity) -> {
            pollEntity.setCurrent(false);
            pollRepository.save(pollEntity);
        });

        pollDTO.setCurrent(true);
    }

    public VoteDTO saveVote(VoteDTO voteDTO) {
        try {
            String pollName = voteDTO.getPollName();
            PollEntity poll = getPollEntityByName(pollName);
            checkOptionExists(poll, voteDTO.getOption());
            setVoteUuidIfNull(voteDTO);
            updateVoteCount(voteDTO, poll);
            voteRepository.save(DataTransformer.voteDTOToEntity(voteDTO));
            return voteDTO;
        } catch (Exception e) {
            log.error("Saving new vote failed", e);
            throw e;
        }
    }

    private void checkOptionExists(PollEntity poll, String option) {
        if (!poll.getOptions().contains(option)) {
            String message = String.format("Option %s does not exist in poll %s", option, poll.getName());
            throw new OptionNotFoundException(message);
        }
    }

    private void setVoteUuidIfNull(VoteDTO dto) {
        if (dto.getUuid() == null) {
            dto.setUuid(UUID.randomUUID());
        }
    }

    private void updateVoteCount(VoteDTO voteDTO, PollEntity poll) {
        int newNumberOfVotes = poll.getNumberOfVotes() + 1;
        poll.setNumberOfVotes(newNumberOfVotes);
        voteDTO.setVoteNumber(newNumberOfVotes);
        pollRepository.save(poll);
    }

    public PollDTO getCurrentPoll() {
        return pollEntityToDTO(pollRepository.findByCurrent(true).orElseThrow(() -> {
            String message = "Current poll not found";
            log.error(message);
            return new PollNotFoundException(message);
        }));
    }

    public PollDTO getPollByName(String name) {
        return pollEntityToDTO(pollRepository.findByName(name)
                .orElseThrow(() -> {
                    String message = String.format("Poll %s not found", name);
                    log.error(message);
                    return new PollNotFoundException(message);
                }));
    }

    public PollDTO getPollByUUID(UUID uuid) {
        return pollEntityToDTO(pollRepository.findById(uuid)
                .orElseThrow(() -> new PollNotFoundException(String.format("Poll %s not found", uuid))));
    }

    public PollResultDTO getResultsForCurrentPoll() {
        PollEntity poll = pollRepository.findByCurrent(true).orElseThrow(() -> {
            String message = "Current poll not found";
            log.error(message);
            return new PollNotFoundException(message);
        });

        return createPollResultDTO(poll);
    }

    private PollEntity getPollEntityByName(String pollName) {
        return pollRepository.findByName(pollName).orElseThrow(() -> {
            String message = String.format("Poll %s not found", pollName);
            log.error(message);
            return new PollNotFoundException(message);
        });
    }

    public PollResultDTO getResultsForPoll(String pollName) {
        PollEntity poll = pollRepository.findByName(pollName)
                .orElseThrow(() -> {
                    String message = String.format("Poll %s not found", pollName);
                    log.error(message);
                    return new PollNotFoundException(message);
                });

        return createPollResultDTO(poll);
    }

    public PollResultDTO getResultsForPollByUUID(UUID uuid) {
        PollEntity poll = pollRepository.findById(uuid)
                .orElseThrow(() -> {
                    String message = String.format("Poll %s not found", uuid);
                    log.error(message);
                    return new PollNotFoundException(message);
                });

        return createPollResultDTO(poll);
    }

    private PollResultDTO createPollResultDTO(PollEntity poll) {
        List<VoteEntity> votesForPoll = voteRepository.findByPollName(poll.getName());
        Map<String, Long> votesByOption = votesForPoll.stream().collect(Collectors.groupingBy(VoteEntity::getOption, Collectors.counting()));

        return new PollResultDTO(poll.getName(), votesByOption, poll.getNumberOfVotes());
    }

    public VoteDTO getVoteForPoll(String pollName, int voteNumber) {
        return voteEntityToDTO(voteRepository.findByPollNameAndVoteNumber(pollName, voteNumber)
                .orElseThrow(() -> {
            String message = String.format("Vote %s for poll %s not found", voteNumber, pollName);
            log.error(message);
            return new VoteNotFoundException(message);
        }));
    }

    public VoteDTO getVoteForUUID(UUID uuid) {
        return voteEntityToDTO(voteRepository.findById(uuid).orElseThrow(() -> {
            String message = String.format("Vote %s not found", uuid);
            log.error(message);
            return new VoteNotFoundException(message);
        }));
    }

}
