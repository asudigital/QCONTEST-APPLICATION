package com.crio.qcontest.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.services.QuestionService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateQuestionCommandTest {
    
    private QuestionService questionService;
    private CreateQuestionCommand createQuestionCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        questionService = mock(QuestionService.class);
        createQuestionCommand = new CreateQuestionCommand(questionService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Set up
        String name = "Question1";
        String level = "LOW";
        Integer score = 10;
        List<String> tokens = List.of("CreateQuestion", name, level, String.valueOf(score));
        
        Question newQuestion = new Question(name, DifficultyLevel.LOW, score);
        Question question = new Question(1L, newQuestion);
        when(questionService.createQuestion(name, DifficultyLevel.LOW, score)).thenReturn(question);
        
        // Call method
        createQuestionCommand.invoke(tokens);
        
        // Verify
        verify(questionService).createQuestion(name, DifficultyLevel.LOW, score);
        assertEquals("Question Id: 1", outContent.toString().trim());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}
