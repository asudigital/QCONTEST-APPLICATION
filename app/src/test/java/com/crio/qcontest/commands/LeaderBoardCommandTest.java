package com.crio.qcontest.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.User;
import com.crio.qcontest.services.UserService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LeaderBoardCommandTest {

    private UserService userService;
    private LeaderBoardCommand leaderBoardCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        leaderBoardCommand = new LeaderBoardCommand(userService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Arrange
        User user1 = new User("User1");
        user1.setScore(1500);
        User user2 = new User("User2");
        user2.setScore(1600);
        User user3 = new User("User3");
        user3.setScore(1400);
        List<User> userList = Arrays.asList(user2,user1,user3);

        when(userService.showLeaderBoard("DESC")).thenReturn(userList);

        // Act
        leaderBoardCommand.invoke(Arrays.asList("LeaderBoard", "DESC"));

        // Assert
        String expectedOutput = "User2 : 1600\nUser1 : 1500\nUser3 : 1400\n";
        assertEquals(expectedOutput, outContent.toString());
        verify(userService).showLeaderBoard("DESC");
    }

    
    
    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}

