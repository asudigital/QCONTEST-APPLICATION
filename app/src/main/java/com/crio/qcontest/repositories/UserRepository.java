package com.crio.qcontest.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.crio.qcontest.entities.User;

public class UserRepository implements IUserRepository{

    private final Map<Long, User> storage = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(0);

    @Override
    public User save(User entity) {
        User user = new User(idCounter.incrementAndGet(),entity);
        storage.putIfAbsent(user.getId(),user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    } 
}