package com.crio.qcontest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.Contestant;
import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.entities.User;
import com.crio.qcontest.repositories.ContestRepository;
import com.crio.qcontest.repositories.ContestantRepository;
import com.crio.qcontest.repositories.QuestionRepository;
import com.crio.qcontest.repositories.UserRepository;

@DisplayName("ContestService Tests")
public class ContestServiceTest {

    // TODO: WARNING!!!
    //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
    //  Any modifications in this file may result in Assessment failure!
    
    private ContestService contestService;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private ContestRepository contestRepository;
    private ContestantRepository contestantRepository;


    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        questionRepository = new QuestionRepository();
        contestRepository = new ContestRepository();
        contestantRepository = new ContestantRepository();
        contestService = new ContestService(contestantRepository, contestRepository, questionRepository, userRepository);
    }

    @Test
    void testCreateContest() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Question q1 = new Question("Q1", DifficultyLevel.LOW, 10);
        Question q2 = new Question("Q2", DifficultyLevel.LOW, 10);
        questionRepository.save(q1);
        questionRepository.save(q2);

        // Call method
        Contest contest = contestService.createContest("Contest1", DifficultyLevel.LOW, 1L, 2);

        // Verify
        assertNotNull(contest);
        assertEquals("Contest1", contest.getName());
        assertEquals(DifficultyLevel.LOW, contest.getLevel());
        assertEquals(1, contest.getCreator().getId());
        assertEquals(2, contest.getQuestions().size());
    }

    @Test
    void testCreateContestThrowsExceptionWhenUserNotFound() {
        // Call method and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contestService.createContest("Contest1", DifficultyLevel.LOW, 999L, 2);
        });
        assertEquals("User with an id: 999 not found!", exception.getMessage());
    }

    @Test
    void testCreateContestThrowsExceptionWhenNotEnoughQuestions() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Question q1 = new Question("Q1", DifficultyLevel.LOW, 10);
        questionRepository.save(q1);

        // Call method and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contestService.createContest("Contest1", DifficultyLevel.LOW, 1L, 2);
        });
        assertEquals("Requested number of questions: 2 cannot be fulfilled!", exception.getMessage());
    }

    @Test
    void testListContestsWithDifficultyLevel() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Contest contest1 = new Contest("Contest1", DifficultyLevel.LOW, user, new ArrayList<>());
        Contest contest2 = new Contest("Contest2", DifficultyLevel.MEDIUM, user, new ArrayList<>());
        contestRepository.save(contest1);
        contestRepository.save(contest2);

        // Call method
        List<Contest> lowContests = contestService.listContests(DifficultyLevel.LOW);

        // Verify
        assertEquals(1, lowContests.size());
        assertEquals("Contest1", lowContests.get(0).getName());
    }

    @Test
    void testListContestsWithoutDifficultyLevel() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Contest contest1 = new Contest("Contest1", DifficultyLevel.LOW, user, new ArrayList<>());
        Contest contest2 = new Contest("Contest2", DifficultyLevel.MEDIUM, user, new ArrayList<>());
        contestRepository.save(contest1);
        contestRepository.save(contest2);

        // Call method
        List<Contest> allContests = contestService.listContests(null);

        // Verify
        assertEquals(2, allContests.size());
    }

    @Test
    void testAttendContest() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Contest contest = new Contest("Contest1", DifficultyLevel.LOW, user, new ArrayList<>());
        contestRepository.save(contest);

        User attendee = new User("Monica");
        userRepository.save(attendee);

        // Call method
        Contestant contestant = contestService.attendContest(1L, 1L);

        // Verify
        assertNotNull(contestant);
        assertEquals(1L, contestant.getContest().getId());
        assertEquals(1L, contestant.getUser().getId());
    }

    @Test
    void testAttendContestThrowsExceptionWhenContestNotFound() {
        // Call method and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contestService.attendContest(999L, 1L);
        });
        assertEquals("Contest with an id 999 not found!", exception.getMessage());
    }

    @Test
    void testAttendContestThrowsExceptionWhenUserNotFound() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Contest newContest = new Contest("Contest1", DifficultyLevel.LOW, user, new ArrayList<>());
        Contest contest = contestRepository.save(newContest);

        // Call method and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contestService.attendContest(contest.getId(), 999L);
        });
        assertEquals("User with an id 999 not found!", exception.getMessage());
    }

    @Test
    void testWithdrawContest() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Contest contest = new Contest("Contest1", DifficultyLevel.LOW, user, new ArrayList<>());
        contestRepository.save(contest);

        User attendee = new User("Monica");
        userRepository.save(attendee);

        Contestant contestant = new Contestant(userRepository.findById(1L).get(),contestRepository.findById(1L).get());
        contestantRepository.save(contestant);

        // Call method
        Boolean result = contestService.withdrawContest(1L, 1L);

        // Verify
        assertTrue(result);
        assertFalse(contestantRepository.exists(1L, 1L));
    }

    @Test
    void testWithdrawContestThrowsExceptionWhenContestantNotFound() {
        // Call method and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contestService.withdrawContest(1L, 1L);
        });
        assertEquals("Contestant not found!", exception.getMessage());
    }

    @Test
    void testContestHistory() {
        // Set up
        User user = new User("Ross");
        userRepository.save(user);

        Contest contest = new Contest("Contest1", DifficultyLevel.LOW, user, new ArrayList<>());
        contestRepository.save(contest);

        User attendee1 = new User("Monica");
        User attendee2 = new User("Joey");
        userRepository.save(attendee1);
        userRepository.save(attendee2);

        Contestant contestant1 = new Contestant(userRepository.findById(1L).get(),contestRepository.findById(1L).get());
        Contestant contestant2 = new Contestant(userRepository.findById(2L).get(),contestRepository.findById(1L).get());
        contestantRepository.save(contestant1);
        contestantRepository.save(contestant2);

        // Call method
        List<Contestant> history = contestService.contestHistory(1L);

        // Verify
        assertEquals(2, history.size());
        assertEquals(1L, history.get(0).getContest().getId());
        assertEquals(1L, history.get(1).getContest().getId());
    }

    @Test
    void testContestHistoryThrowsExceptionWhenContestNotFound() {
        // Call method and verify exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contestService.contestHistory(999L);
        });
        assertEquals("Contest with an id 999 not found!", exception.getMessage());
    }
}
