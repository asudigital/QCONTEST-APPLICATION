package com.crio.qcontest.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.User;
import com.crio.qcontest.services.UserService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateUserCommandTest {

    private UserService userService;
    private CreateUserCommand createUserCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        createUserCommand = new CreateUserCommand(userService);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInvoke() {
        // Set up
        String userName = "Ross";
        List<String> tokens = List.of("CreateUser", userName);

        User newUser = new User(userName);
        User user = new User(1L,newUser);
        when(userService.createUser(userName)).thenReturn(user);

        // Call method
        createUserCommand.invoke(tokens);
        // Verify
        verify(userService).createUser(userName);
        assertEquals("User Id: 1", outContent.toString().trim());
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}

