package com.example.pollwebsite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
public class PollEntity {

    @Id
    private UUID uuid;
    private String name;
    private String question;
    private Collection<String> options;
    private int numberOfVotes;
    private boolean current;

    protected PollEntity() {}

    public PollEntity(UUID uuid, String name, String question, Collection<String> options, int numberOfVotes, boolean current) {
        this.uuid = uuid;
        this.name = name;
        this.question = question;
        this.options = options;
        this.numberOfVotes = numberOfVotes;
        this.current = current;
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

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PollEntity that = (PollEntity) o;
        return numberOfVotes == that.numberOfVotes && current == that.current && Objects.equals(uuid, that.uuid) && Objects.equals(name, that.name) && Objects.equals(question, that.question) && Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, question, options, numberOfVotes, current);
    }

    @Override
    public String toString() {
        return "PollEntity{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", numberOfVotes=" + numberOfVotes +
                ", current=" + current +
                '}';
    }

    
}
