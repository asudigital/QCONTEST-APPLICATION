package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.Contestant;
import com.crio.qcontest.services.ContestService;

public class AttendContestCommand implements ICommand {

    private final ContestService contestService;

    public AttendContestCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {
        Long contestId = Long.parseLong(tokens.get(1));
        Long userId = Long.parseLong(tokens.get(2));
        Contestant contestant = contestService.attendContest(contestId, userId);
        System.out.println(contestant);
    }
}
