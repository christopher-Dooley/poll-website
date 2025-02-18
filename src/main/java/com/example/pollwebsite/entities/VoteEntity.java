package com.example.pollwebsite.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class VoteEntity {

    @Id
    private UUID uuid;
    private String pollName;
    private String option;
    private Integer voteNumber;
    private ZonedDateTime timestamp;

    protected VoteEntity() {}

    public VoteEntity(UUID uuid, String pollName, String option, Integer voteNumber, ZonedDateTime timestamp) {
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

    public Integer getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(Integer voteNumber) {
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
        VoteEntity that = (VoteEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(pollName, that.pollName) && Objects.equals(option, that.option) && Objects.equals(voteNumber, that.voteNumber) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, pollName, option, voteNumber, timestamp);
    }

    @Override
    public String toString() {
        return "VoteEntity{" +
                "uuid=" + uuid +
                ", pollName='" + pollName + '\'' +
                ", option='" + option + '\'' +
                ", voteNumber=" + voteNumber +
                ", time=" + timestamp +
                '}';
    }
}
