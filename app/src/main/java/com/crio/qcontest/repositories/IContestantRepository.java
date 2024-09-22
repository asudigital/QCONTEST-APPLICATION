package com.crio.qcontest.repositories;

import java.util.List;
import java.util.Optional;

import com.crio.qcontest.entities.Contestant;

public interface IContestantRepository {
    Contestant save(Contestant contestant);
    Optional<Contestant> findById(Long contestId, Long userId);
    List<Contestant> findByContestId(Long contestId);
    void deleteById(Long contestId, Long userId);
    Boolean exists(Long contestId, Long userId);
}