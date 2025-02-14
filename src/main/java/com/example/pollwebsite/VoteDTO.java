package com.example.pollwebsite;

import java.time.ZonedDateTime;
import java.util.Objects;

public class VoteDTO {

    private String pollName;
    private String option;
    private ZonedDateTime timestamp;

    public VoteDTO(String pollName, String option, ZonedDateTime timestamp) {
        this.pollName = pollName;
        this.option = option;
        this.timestamp = timestamp;
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
        return Objects.equals(pollName, voteDTO.pollName) && Objects.equals(option, voteDTO.option) && Objects.equals(timestamp, voteDTO.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollName, option, timestamp);
    }

    @Override
    public String toString() {
        return "VoteDTO{" +
                "pollName='" + pollName + '\'' +
                ", option='" + option + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
