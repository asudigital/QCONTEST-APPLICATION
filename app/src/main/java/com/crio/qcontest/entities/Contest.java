package com.crio.qcontest.entities;

import java.util.List;

public class Contest {
    private final Long id;
    private final String name;
    private final DifficultyLevel level;
    private final User creator;
    private final List<Question> questions;

    public Contest(String name, DifficultyLevel level, User creator, List<Question> questions) {
        this.id = null;
        this.name = name;
        this.level = level;
        this.creator = creator;
        this.questions = questions;
    }

    public Contest(Long id, Contest other) {
        this.id = id;
        this.name = other.name;
        this.level = other.level;
        this.creator = other.creator;
        this.questions = other.questions;
    }


    public String getName() {
        return name;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public User getCreator() {
        return creator;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Contest [id=" + id + "]";
    }
}