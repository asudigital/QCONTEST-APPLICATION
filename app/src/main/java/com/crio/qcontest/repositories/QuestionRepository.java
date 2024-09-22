package com.crio.qcontest.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;

public class QuestionRepository implements IQuestionRepository{

    private final Map<Long, Question> storage = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Question save(Question entity) {
        Question question = new Question(idCounter.incrementAndGet(), entity);
        storage.putIfAbsent(question.getId(), question);
        return question;
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Question> findByDifficultyLevel(DifficultyLevel level) {
        return storage.values().stream().filter(q -> q.getLevel() == level).collect(Collectors.toList());
    }

    @Override
    public Integer count() {
        return storage.size();
    }
}