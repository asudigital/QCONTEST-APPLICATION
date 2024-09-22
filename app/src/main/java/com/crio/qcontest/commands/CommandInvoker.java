package com.crio.qcontest.commands;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CommandInvoker{

    private static final Map<String, ICommand> commands = new HashMap<>();
 
    // Register the command into the HashMap
    public void register(String commandName, ICommand command){
        commands.putIfAbsent(commandName,command);
    }
 
    // Get the registered Command
    public ICommand get(String commandName){
        return commands.get(commandName);
    }

    private List<String> parse(String input){
        return Arrays.asList(input.split(","));
    }
 
    // Invoke the registered Command
    public void invoke(String input){
        List<String> tokens = parse(input);
        ICommand command = get(tokens.get(0));
        if(command == null){
            throw new RuntimeException("INVALID_COMMAND");
        }
        command.invoke(tokens);
    }
 }
 
