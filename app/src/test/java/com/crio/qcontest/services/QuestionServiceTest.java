package com.crio.qcontest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.repositories.QuestionRepository;

@DisplayName("QuestionService Tests")
public class QuestionServiceTest {

    // TODO: WARNING!!!
    //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
    //  Any modifications in this file may result in Assessment failure!

    private QuestionService questionService;
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp(){
        questionRepository = new QuestionRepository();
        questionService = new QuestionService(questionRepository);
    }

   @Test
    void testCreateQuestion() {
        // Call method
        Question createdQuestion = questionService.createQuestion("Sample Question", DifficultyLevel.LOW, 10);

        // Verify created question
        assertNotNull(createdQuestion);
        assertEquals("Sample Question", createdQuestion.getName());
        assertEquals(DifficultyLevel.LOW, createdQuestion.getLevel());
        assertEquals(10, createdQuestion.getScore());
        assertEquals(1, createdQuestion.getId()); // ID should be auto-incremented
    }

    @Test
    void testListQuestionsWithDifficultyLevel() {
        // Setup
        Question question1 = new Question("Question 1", DifficultyLevel.LOW, 10);
        Question question2 = new Question("Question 2", DifficultyLevel.MEDIUM, 20);
        Question question3 = new Question("Question 3", DifficultyLevel.LOW, 15);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        // Call method
        List<Question> lowQuestions = questionService.listQuestions(DifficultyLevel.LOW);

        // Verify results
        assertEquals(2, lowQuestions.size());
        assertTrue(lowQuestions.stream().anyMatch(q -> q.getName().equals("Question 1")));
        assertTrue(lowQuestions.stream().anyMatch(q -> q.getName().equals("Question 3")));
    }

    @Test
    void testListQuestionsWithoutDifficultyLevel() {
        // Setup
        Question question1 = new Question("Question 1", DifficultyLevel.LOW, 10);
        Question question2 = new Question("Question 2", DifficultyLevel.MEDIUM, 20);
        Question question3 = new Question("Question 3", DifficultyLevel.LOW, 15);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        // Call method
        List<Question> allQuestions = questionService.listQuestions(null);

        // Verify results
        assertEquals(3, allQuestions.size());
        assertTrue(allQuestions.stream().anyMatch(q -> q.getName().equals("Question 1")));
        assertTrue(allQuestions.stream().anyMatch(q -> q.getName().equals("Question 2")));
        assertTrue(allQuestions.stream().anyMatch(q -> q.getName().equals("Question 3")));
    }

    @Test
    void testListQuestionsWithNoQuestions() {
        // Call method
        List<Question> questions = questionService.listQuestions(null);

        // Verify results
        assertTrue(questions.isEmpty());
    }

    @Test
    void testListQuestionsWithNoMatchingDifficultyLevel() {
        // Setup
        Question question1 = new Question("Question 1", DifficultyLevel.LOW, 10);
        Question question2 = new Question("Question 2", DifficultyLevel.MEDIUM, 20);

        questionRepository.save(question1);
        questionRepository.save(question2);

        // Call method
        List<Question> highQuestions = questionService.listQuestions(DifficultyLevel.HIGH);

        // Verify results
        assertTrue(highQuestions.isEmpty());
    }
}
