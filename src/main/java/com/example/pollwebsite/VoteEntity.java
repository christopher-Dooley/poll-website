package com.example.pollwebsite;

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
    private ZonedDateTime time;

    protected VoteEntity() {}

    public VoteEntity(String pollName, String option, Integer voteNumber, ZonedDateTime time) {
        this.uuid = UUID.randomUUID();
        this.pollName = pollName;
        this.option = option;
        this.voteNumber = voteNumber;
        this.time = time;
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

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VoteEntity that = (VoteEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(pollName, that.pollName) && Objects.equals(option, that.option) && Objects.equals(voteNumber, that.voteNumber) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, pollName, option, voteNumber, time);
    }

    @Override
    public String toString() {
        return "VoteEntity{" +
                "uuid=" + uuid +
                ", pollName='" + pollName + '\'' +
                ", option='" + option + '\'' +
                ", voteNumber=" + voteNumber +
                ", time=" + time +
                '}';
    }
}
