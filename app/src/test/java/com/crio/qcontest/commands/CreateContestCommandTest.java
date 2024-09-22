package com.crio.qcontest.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.entities.User;
import com.crio.qcontest.services.ContestService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateContestCommandTest {
    
    private ContestService contestService;
    private CreateContestCommand createContestCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        contestService = mock(ContestService.class);
        createContestCommand = new CreateContestCommand(contestService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Set up
        String contestName = "diwali_contest";
        String level = "LOW";
        Long userId = 1L;
        Integer numQuestion = 3;
        List<String> tokens = List.of("CreateContest", contestName, level, String.valueOf(userId), String.valueOf(numQuestion));

        Contest newContest = new Contest(contestName, DifficultyLevel.LOW, new User("Ross"), List.of(
            new Question("Question1", DifficultyLevel.LOW, 10),
            new Question("Question2", DifficultyLevel.LOW, 10),
            new Question("Question3", DifficultyLevel.LOW, 10)
        ));
        Contest contest = new Contest(1L, newContest);

        when(contestService.createContest(contestName, DifficultyLevel.valueOf(level), userId, numQuestion)).thenReturn(contest);

        // Call method
        createContestCommand.invoke(tokens);

        // Verify
        verify(contestService).createContest(contestName, DifficultyLevel.valueOf(level), userId, numQuestion);
        assertEquals("Contest Id: " + contest.getId(), outContent.toString().trim());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}
