package com.example.pollwebsite;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class PollDTO {

    private UUID uuid;
    private String name;
    private String question;
    private Collection<String> options;
    private int numberOfVotes;

    public PollDTO(UUID uuid, String name, String question, Collection<String> options, int numberOfVotes) {
        this.uuid = uuid;
        this.name = name;
        this.question = question;
        this.options = options;
        this.numberOfVotes = numberOfVotes;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Collection<String> getOptions() {
        return options;
    }

    public void setOptions(Collection<String> options) {
        this.options = options;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PollDTO pollDTO = (PollDTO) o;
        return numberOfVotes == pollDTO.numberOfVotes && Objects.equals(uuid, pollDTO.uuid) && Objects.equals(name, pollDTO.name) && Objects.equals(question, pollDTO.question) && Objects.equals(options, pollDTO.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, question, options, numberOfVotes);
    }

    @Override
    public String toString() {
        return "PollDTO{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", numberOfVotes=" + numberOfVotes +
                '}';
    }
}
