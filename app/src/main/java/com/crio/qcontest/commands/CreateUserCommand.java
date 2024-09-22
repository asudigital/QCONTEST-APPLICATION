package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.User;
import com.crio.qcontest.services.UserService;

public class CreateUserCommand implements ICommand{

    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void invoke(List<String> tokens) {
        String userName = tokens.get(1);
        User user = userService.createUser(userName);
        System.out.println("User Id: " +user.getId()); 
    }
    
}

