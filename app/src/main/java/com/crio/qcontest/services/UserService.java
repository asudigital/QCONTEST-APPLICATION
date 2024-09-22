package com.crio.qcontest.services;

 import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.crio.qcontest.entities.User;
import com.crio.qcontest.repositories.IUserRepository;

public class UserService{

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user with the specified name.
     * @param name Name of the user.
     * @return Created User object.
     */
    public User createUser(String name) {
        User newUser = new User(name);
        User savedUser =  userRepository.save(newUser);
      return savedUser;
    }

    /**
     * Retrieves a list of users sorted by their score.
     * @param order Sorting order ("ASC" for ascending, "DESC" for descending).
     * @return List of users sorted by score as per the specified order.
     */
    public List<User> showLeaderBoard(String order) {
       
        List<User> allUsers = userRepository.findAll();
        
        if("ASC".equalsIgnoreCase(order)){        
          Collections.sort(allUsers , new ShortByScoreAscending()); 
        } else if("DESC".equalsIgnoreCase(order)){
          Collections.sort(allUsers , new ShortByScoreDescending());
        }
        return allUsers;
    }     
}

// Ascending Order Comparator for Users by score
class ShortByScoreAscending implements Comparator<User>{

    @Override
    public int compare(User arg0, User arg1) {
        
        return arg0.getScore() - arg1.getScore() ;
    }
    
}
  // Descending Order Comparator for Users by score
class ShortByScoreDescending implements Comparator<User>{

    @Override
    public int compare(User arg0, User arg1) {
        
        return arg1.getScore() - arg0.getScore() ;
    }   
}