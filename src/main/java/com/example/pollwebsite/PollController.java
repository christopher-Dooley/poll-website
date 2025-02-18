package com.example.pollwebsite;

import com.example.pollwebsite.dtos.PollDTO;
import com.example.pollwebsite.dtos.PollResultDTO;
import com.example.pollwebsite.dtos.VoteDTO;
import com.example.pollwebsite.exceptions.OptionNotFoundException;
import com.example.pollwebsite.exceptions.PollNotFoundException;
import com.example.pollwebsite.exceptions.VoteNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
public class PollController {

    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/poll/current")
    public PollDTO getCurrentPoll() {
        return pollService.getCurrentPoll();
    }

    @GetMapping("/poll/name/{name}")
    public PollDTO getPollByName(@PathVariable String name) {
        return pollService.getPollByName(name);
    }

    @GetMapping("/poll/uuid/{uuid}")
    public PollDTO getPollByUUID(@PathVariable UUID uuid) {
        return pollService.getPollByUUID(uuid);
    }

    @PostMapping("/poll/save")
    public ResponseEntity<PollDTO> savePoll(@RequestBody PollDTO pollDTO, HttpServletRequest request) {
        try {
            if(!pollService.isPollLengthValid(pollDTO)) {
                return ResponseEntity.badRequest().build();
            }

            String requestUri = request.getRequestURI();
            PollDTO savedPoll = pollService.savePoll(pollDTO);
            URI location = getNewLocationUri(requestUri, savedPoll.getUuid());
            return ResponseEntity.created(location).body(savedPoll);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private URI getNewLocationUri(String requestUri, UUID uuid) {
        return URI.create(
                requestUri.substring(0, requestUri.length() -4)
                        .concat("uuid/")
                        .concat(uuid.toString())
        );
    }

    @GetMapping("/poll/current/results")
    public PollResultDTO getResultsForCurrentPoll() {
        return pollService.getResultsForCurrentPoll();
    }

    @GetMapping("/poll/name/{name}/results")
    public PollResultDTO getResultsForPollByName(@PathVariable String name) {
        return pollService.getResultsForPoll(name);
    }

    @GetMapping("/poll/uuid/{uuid}/results")
    public PollResultDTO getResultsForPollByUUID(@PathVariable UUID uuid) {
        return pollService.getResultsForPollByUUID(uuid);
    }

    @PostMapping("/poll/vote/save")
    public VoteDTO saveVote(@RequestBody VoteDTO voteDTO) {
        return pollService.saveVote(voteDTO);
    }

    @GetMapping("/poll/vote/name/{pollName}/number/{voteNumber}")
    public VoteDTO getVoteForPoll(@PathVariable String pollName, @PathVariable int voteNumber) {
        return pollService.getVoteForPoll(pollName, voteNumber);
    }

    @GetMapping("/poll/vote/uuid/{uuid}")
    public VoteDTO getVoteForUUID(@PathVariable UUID uuid) {
        return pollService.getVoteForUUID(uuid);
    }

    @ExceptionHandler(PollNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Poll not found")
    public void handlePollNotFoundException() {}

    @ExceptionHandler(VoteNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Vote not found")
    public void handleVoteNotFoundException() {}

    @ExceptionHandler(OptionNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Option not found")
    public void handleOptionNotFoundException() {}
}
