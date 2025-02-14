package com.example.pollwebsite;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class VoteDTO {

    private UUID uuid;
    private String pollName;
    private String option;
    private int voteNumber;
    private ZonedDateTime timestamp;

    public VoteDTO(UUID uuid, String pollName, String option, int voteNumber, ZonedDateTime timestamp) {
        this.uuid = uuid;
        this.pollName = pollName;
        this.option = option;
        this.voteNumber = voteNumber;
        this.timestamp = timestamp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(int voteNumber) {
        this.voteNumber = voteNumber;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VoteDTO voteDTO = (VoteDTO) o;
        return voteNumber == voteDTO.voteNumber && Objects.equals(uuid, voteDTO.uuid) && Objects.equals(pollName, voteDTO.pollName) && Objects.equals(option, voteDTO.option) && Objects.equals(timestamp, voteDTO.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, pollName, option, voteNumber, timestamp);
    }

    @Override
    public String toString() {
        return "VoteDTO{" +
                "uuid=" + uuid +
                ", pollName='" + pollName + '\'' +
                ", option='" + option + '\'' +
                ", voteNumber=" + voteNumber +
                ", timestamp=" + timestamp +
                '}';
    }
}

