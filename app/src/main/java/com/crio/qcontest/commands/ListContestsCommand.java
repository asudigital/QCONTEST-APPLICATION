package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.services.ContestService;

public class ListContestsCommand implements ICommand {

    private final ContestService contestService;

    public ListContestsCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {
        List <Contest> cList = null;
        if(tokens.size() == 1){
            cList = contestService.listContests(null);
        }else if(tokens.size() == 2){
            String level = tokens.get(1);
            cList = contestService.listContests(DifficultyLevel.valueOf(level));            
        }
        System.out.println(cList);
    }
    
}
