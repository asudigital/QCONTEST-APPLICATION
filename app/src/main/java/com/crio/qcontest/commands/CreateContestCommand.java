package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.services.ContestService;

public class CreateContestCommand implements ICommand {

    private final ContestService contestService; 

    public CreateContestCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {
        String contestName = tokens.get(1);
        String level = tokens.get(2);
        Long userId = Long.valueOf(tokens.get(3));
        Integer numQuestion = Integer.parseInt(tokens.get(4));
        Contest contest = contestService.createContest(contestName,DifficultyLevel.valueOf(level), userId,numQuestion);
        System.out.println("Contest Id: " +contest.getId());
    }
    
}
