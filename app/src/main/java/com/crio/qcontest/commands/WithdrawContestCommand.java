package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.services.ContestService;

public class WithdrawContestCommand implements ICommand {

    private final ContestService contestService;

    public WithdrawContestCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {
        Long contestId = Long.parseLong(tokens.get(1));
        Long userId = Long.parseLong(tokens.get(2));
        Boolean result = contestService.withdrawContest(contestId, userId);
        if (result){
            System.out.println("User with an id "+userId+ "withdrawn from contest with an id "+ contestId);
        }
    }
}
