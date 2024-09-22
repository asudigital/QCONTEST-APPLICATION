package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.services.QuestionService;

public class ListQuestionsCommand implements ICommand{

    private final QuestionService questionService;

    public ListQuestionsCommand(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void invoke(List<String> tokens) {
        List <Question> qList = null;
        if(tokens.size() == 1){
            qList = questionService.listQuestions(null);
        }else if(tokens.size() == 2){
            String level = tokens.get(1);
            qList = questionService.listQuestions(DifficultyLevel.valueOf(level));
        }
        System.out.println(qList); 
    }
    
}
