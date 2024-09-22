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

public class ListQuestionsCommandTest {
    
    private QuestionService questionService;
    private ListQuestionsCommand listQuestionsCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        questionService = mock(QuestionService.class);
        listQuestionsCommand = new ListQuestionsCommand(questionService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Set up
        String level = "LOW";
        List<String> tokens = List.of("ListQuestions", level);

        List<Question> questionList = List.of(
            new Question("Question1", DifficultyLevel.LOW, 10)
        );
        when(questionService.listQuestions(DifficultyLevel.valueOf(level))).thenReturn(questionList);

        // Call method
        listQuestionsCommand.invoke(tokens);

        // Verify
        verify(questionService).listQuestions(DifficultyLevel.valueOf(level));
        assertEquals(questionList.toString(), outContent.toString().trim());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}
