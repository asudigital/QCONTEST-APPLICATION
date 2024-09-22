package com.crio.qcontest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.User;
import com.crio.qcontest.repositories.UserRepository;

@DisplayName("UserService Tests")
public class UserServiceTest{

    // TODO: WARNING!!!
    //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
    //  Any modifications in this file may result in Assessment failure!

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    public void testCreateUser(){
        // Arrange
        // Act
        User createdUser = userService.createUser("John Doe");
        // Assert
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals(1, createdUser.getId()); // ID should be auto-incremented
    }

    @Test
    public void testShowLeaderBoardAsc(){
        // Arrange
        User user1 = new User("Alice");
        user1.setScore(1500);
        User user2 = new User("Bob");
        user2.setScore(1400);
        User user3 = new User("Charlie");
        user3.setScore(1600);
       
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        
       
        // Act
        List<User> leaderboard = userService.showLeaderBoard("ASC");
        // Assert
        assertEquals("Bob", leaderboard.get(0).getName());
        assertEquals("Alice", leaderboard.get(1).getName());
        assertEquals("Charlie", leaderboard.get(2).getName());
    }


    @Test
    public void testShowLeaderBoardDesc(){
        // Arrange
        User user1 = new User("Alice");
        user1.setScore(1500);
        User user2 = new User("Bob");
        user2.setScore(1400);
        User user3 = new User("Charlie");
        user3.setScore(1600);
       
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        
       
        // Act
        List<User> leaderboard = userService.showLeaderBoard("DESC");
        // Assert
        assertEquals("Charlie", leaderboard.get(0).getName());
        assertEquals("Alice", leaderboard.get(1).getName());
        assertEquals("Bob", leaderboard.get(2).getName());
    }

    @Test
    void testShowLeaderBoardEmptyList() {
        // Call method
        List<User> leaderboard = userService.showLeaderBoard("ASC");

        // Assert empty list
        assertTrue(leaderboard.isEmpty());
    }
}