package com.crio.qcontest.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.services.ContestService;

import java.util.List;

import static org.mockito.Mockito.*;

public class RunContestCommandTest {
    
    private ContestService contestService;
    private RunContestCommand runContestCommand;

    @BeforeEach
    void setUp() {
        contestService = mock(ContestService.class);
        runContestCommand = new RunContestCommand(contestService);
    }

    @Test
    void testInvoke() {
        // Set up
        Long contestId = 1L;
        Long contestCreator = 2L;

        // Call method
        runContestCommand.invoke(List.of("RunContest", contestId.toString(), contestCreator.toString()));

        // Verify
        verify(contestService).runContest(contestId, contestCreator);
    }
}
