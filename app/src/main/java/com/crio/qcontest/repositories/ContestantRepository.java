package com.crio.qcontest.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.qcontest.entities.Contestant;

public class ContestantRepository implements IContestantRepository {

    // Create a storage map to hold Contestants with their unique IDs
    private final Map<String,Contestant> storage = new HashMap<>();

    @Override
    public Contestant save(Contestant contestant) {
        String contestantId = getContestantId(contestant.getContestId(),contestant.getUserId());
        storage.put(contestantId,contestant);
        return contestant;
    }

    @Override
    public Optional<Contestant> findById(Long contestId, Long userId) {
        // Find a Contestant in the storage map based on contestId and userId.
        return storage.values().stream()
        .filter(c -> c.getContestId() == contestId && c.getUserId().equals(userId)).findFirst();
    }

    @Override
    public List<Contestant> findByContestId(Long contestId) {
        // Find all the Contestants for a given contest Id.
        return storage.values().stream().filter(c -> c.getContestId() == contestId).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long contestId, Long userId) {
        String contestantId = getContestantId(contestId,userId);
        // System.out.println("Asuuuuuuuuuuuuuuuuuuuuuuuuuuuu" + contestantId);
        storage.remove(contestantId);        
    }

    @Override
    public Boolean exists(Long contestId, Long userId) {
        // Generate the ID for the BoardMember to be searched.
        String contestantId = getContestantId(contestId, userId);
        return storage.values().stream()
        .anyMatch(contestant -> getContestantId(contestant.getContestId(),contestant.getUserId()).equals(contestantId));
    }

    private String getContestantId(Long contestId, Long userId){
        // Generate a new ID in this format:- "User[id] Contest[id]"
        // Above representation of ID makes a Contestant unique. 
        return new StringBuilder().append("User[").append(contestId).append("] Contest[").append(userId).append("]").toString();
    }   
}