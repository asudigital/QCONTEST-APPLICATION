package com.crio.qcontest.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.services.ContestService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WithdrawContestCommandTest {

     private ContestService contestService;
    private WithdrawContestCommand withdrawContestCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        contestService = mock(ContestService.class);
        withdrawContestCommand = new WithdrawContestCommand(contestService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Set up
        Long contestId = 1L;
        Long userId = 2L;
        when(contestService.withdrawContest(contestId, userId)).thenReturn(true);

        // Call method
        withdrawContestCommand.invoke(List.of("WithdrawContest", contestId.toString(), userId.toString()));

        // Verify
        verify(contestService).withdrawContest(contestId, userId);
        assertEquals("User with an id " + userId + "withdrawn from contest with an id " + contestId, outContent.toString().trim());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}

