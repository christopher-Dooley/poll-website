package com.example.pollwebsite;

import com.example.pollwebsite.dtos.PollDTO;
import com.example.pollwebsite.dtos.VoteDTO;
import com.example.pollwebsite.entities.PollEntity;
import com.example.pollwebsite.entities.VoteEntity;
import com.example.pollwebsite.exceptions.PollNotFoundException;
import com.example.pollwebsite.repository.PollRepository;
import com.example.pollwebsite.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PollServiceTests {

    private final PollRepository mockPollRepository = mock(PollRepository.class);
    private final VoteRepository mockVoteRepository = org.mockito.Mockito.mock(VoteRepository.class);
    private final PollService pollService = new PollService(mockPollRepository, mockVoteRepository);


    @Test
    void testPollLengthTooShort() {
        PollDTO pollDTO = new PollDTO(null, "SamplePoll", "Pick one", Collections.singletonList("Option1"), 0, false);
        assertFalse(pollService.isPollLengthValid(pollDTO));
    }

    @Test
    void testPollLengthWithinRange() {
        PollDTO pollDTO = new PollDTO(null, "SamplePoll", "Pick one", Arrays.asList("Option1", "Option2", "Option3"), 0, false);
        assertTrue(pollService.isPollLengthValid(pollDTO));
    }

    @Test
    void testPollLengthTooLong() {
        PollDTO pollDTO = new PollDTO(null, "SamplePoll", "Pick one", Arrays.asList("Option1", "Option2", "Option3", "Option4", "Option5", "Option6", "Option7", "Option8"), 0, false);
        assertFalse(pollService.isPollLengthValid(pollDTO));
    }

    @Test
    void testSaveVoteSuccessfully() {
        // Arrange
        String pollName = "SamplePoll";
        VoteDTO voteDTO = new VoteDTO(null, pollName, "Option1", 0, null);
        VoteEntity voteEntity = new VoteEntity(null, pollName, "Option1", 6, null);
        PollEntity mockPoll = new PollEntity(null, pollName, "Pick one", Arrays.asList("Option1", "Option2", "Option3"), 5, true);
        mockPoll.setName(pollName);
        mockPoll.setNumberOfVotes(5);

        when(mockPollRepository.findByName(pollName)).thenReturn(Optional.of(mockPoll));
        when(mockVoteRepository.save(voteEntity)).thenReturn(voteEntity);

        // Act
        VoteDTO savedVote = pollService.saveVote(voteDTO);

        // Assert
        assertEquals(6, savedVote.getVoteNumber());
        verify(mockPollRepository).save(mockPoll);
        verify(mockVoteRepository).save(org.mockito.Mockito.any());
    }

    @Test
    void testSaveVotePollNotFound() {
        // Arrange
        String nonExistentPollName = "NonExistentPoll";
        VoteDTO voteDTO = new VoteDTO(null, nonExistentPollName, "Option1", 0, null);

        when(mockPollRepository.findByName(nonExistentPollName)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(PollNotFoundException.class, () -> pollService.saveVote(voteDTO));
    }
}
