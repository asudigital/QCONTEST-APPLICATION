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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ContestHistoryCommandTest {

    private ContestService contestService;
    private ContestHistoryCommand contestHistoryCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        contestService = mock(ContestService.class);
        contestHistoryCommand = new ContestHistoryCommand(contestService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvokeWithValidContestId() {
        // Arrange
        Long contestId = 1L;
        User newUser1 = new User("User1");
        User user1 = new User(contestId, newUser1);
        User newUser2 = new User("User2");
        User user2 = new User(contestId, newUser2);
        Question newQuestion1 = new Question("Question1", DifficultyLevel.LOW, 10);
        Question question1 = new Question(1L, newQuestion1);
        Question newQuestion2 = new Question("Question2", DifficultyLevel.LOW, 20);
        Question question2 = new Question(2L, newQuestion2);
        Contest newContest = new Contest("Contest1", DifficultyLevel.LOW, user1, List.of(question1,question2));
        Contest contest = new Contest(contestId, newContest);

        Contestant contestant1 = new Contestant(user1, contest);
        Contestant contestant2 = new Contestant(user2, contest);

        contestant1.addQuestion(question1);
        contestant2.addQuestion(question2);

        List<Contestant> contestants = Arrays.asList(contestant2, contestant1);
        when(contestService.contestHistory(contestId)).thenReturn(contestants);

        // Act
        contestHistoryCommand.invoke(List.of("ContestHistory", contestId.toString()));

        // Assert
        String expectedOutput = "User2 : 20 [2]\nUser1 : 10 [1]";
        verify(contestService).contestHistory(contestId);
        assertEquals(expectedOutput, outContent.toString().trim());
    }


    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}

