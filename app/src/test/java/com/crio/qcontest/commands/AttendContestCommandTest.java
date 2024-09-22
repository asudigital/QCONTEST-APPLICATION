package com.crio.qcontest.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.Contestant;
import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.entities.User;
import com.crio.qcontest.services.ContestService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AttendContestCommandTest {
  
    private ContestService contestService;
    private AttendContestCommand attendContestCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        contestService = mock(ContestService.class);
        attendContestCommand = new AttendContestCommand(contestService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Set up
        Long contestId = 1L;
        Long userId = 2L;
        String contestName = "diwali_contest";
        User newUser = new User("Ross");
        User user = new User(userId, newUser);
        Contest newContest = new Contest(contestName, DifficultyLevel.LOW, user , List.of(
            new Question("Question1", DifficultyLevel.LOW, 10),
            new Question("Question2", DifficultyLevel.LOW, 10),
            new Question("Question3", DifficultyLevel.LOW, 10)
        ));
        Contest contest = new Contest(contestId, newContest);
        Contestant contestant = new Contestant(new User("Monica"),contest);
        when(contestService.attendContest(contestId, userId)).thenReturn(contestant);

        // Call method
        attendContestCommand.invoke(List.of("AttendContest", contestId.toString(), userId.toString()));

        // Verify
        verify(contestService).attendContest(contestId, userId);
        assertEquals(contestant.toString(), outContent.toString().trim());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}
