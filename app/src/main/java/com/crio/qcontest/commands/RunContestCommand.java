package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.services.ContestService;

public class RunContestCommand implements ICommand {

    private final ContestService contestService;

    public RunContestCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {
        Long contestId = Long.parseLong(tokens.get(1));
        Long contestCreator = Long.parseLong(tokens.get(2));
        contestService.runContest(contestId, contestCreator);
    }
    
}
