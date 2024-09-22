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

public class ListContestsCommandTest {
    
    private ContestService contestService;
    private ListContestsCommand listContestsCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        contestService = mock(ContestService.class);
        listContestsCommand = new ListContestsCommand(contestService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Set up
        List<Contest> contests = List.of(
            new Contest("diwali_contest", DifficultyLevel.LOW, new User("Ross"), List.of(
                new Question("Question1", DifficultyLevel.LOW, 10),
                new Question("Question2", DifficultyLevel.LOW, 10)
            ))
        );

        when(contestService.listContests(DifficultyLevel.LOW)).thenReturn(contests);

        // Call method
        listContestsCommand.invoke(List.of("ListContests", "LOW"));

        // Verify
        verify(contestService).listContests(DifficultyLevel.LOW);
        assertEquals(contests.toString(), outContent.toString().trim());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}

