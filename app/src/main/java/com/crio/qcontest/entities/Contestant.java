package com.crio.qcontest.entities;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Contestant {
    private final User user;
    private final Contest contest;
    private final Set<Question> solvedQuestions;
    private Integer totalScore;

    public Contestant(User user, Contest contest) {
        this.user = user;
        this.contest = contest;
        this.solvedQuestions = new HashSet<Question>();
        this.totalScore = 0;
    }

    public User getUser(){
        return user;
    }

    public Contest getContest(){
        return contest;
    }

    public List<Question> getSolvedQuestions() {
        return new ArrayList<>(solvedQuestions);
    }  

    public Long getUserId() {
        return user.getId();
    }

    public Long getContestId() {
        return contest.getId();
    }

    public Integer getTotalScore() {
        return totalScore;
    }
    
    public void addQuestion(Question question){
    // Add the Questions solved by the Contestant.
        solvedQuestions.add(question);
    // Add the score obtained for the question solved.
        totalScore += question.getScore();
    }

    @Override
    public String toString() {
        return "Contestant [user=" + user.getId() + ", contest=" + contest.getId() + 
        ", totalScore=" + totalScore + ", solvedQuestions=" + solvedQuestions + "]";
    }
}