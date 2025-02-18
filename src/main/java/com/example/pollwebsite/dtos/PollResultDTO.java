package com.example.pollwebsite.dtos;

import java.util.Map;
import java.util.Objects;

public class PollResultDTO {

    private String pollName;
    private Map<String, Long> results;
    private int totalVotes;

    public PollResultDTO(String pollName, Map<String, Long> results, int totalVotes) {
        this.pollName = pollName;
        this.results = results;
        this.totalVotes = totalVotes;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public Map<String, Long> getResults() {
        return results;
    }

    public void setResults(Map<String, Long> results) {
        this.results = results;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PollResultDTO that = (PollResultDTO) o;
        return totalVotes == that.totalVotes && Objects.equals(pollName, that.pollName) && Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollName, results, totalVotes);
    }

    @Override
    public String toString() {
        return "PollResultDTO{" +
                "pollName='" + pollName + '\'' +
                ", results=" + results +
                ", totalVotes=" + totalVotes +
                '}';
    }
}
