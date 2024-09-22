package com.crio.qcontest.repositories;

import java.util.List;
import java.util.Optional;

import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;

public interface IQuestionRepository {
    Question save(Question question);
    List<Question> findAll();
    Optional<Question> findById(Long id);
    List<Question> findByDifficultyLevel(DifficultyLevel level);
    Integer count();
}