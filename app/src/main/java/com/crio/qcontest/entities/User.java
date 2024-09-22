package com.crio.qcontest.entities;

import java.util.Comparator;

public class User {
    private final Long id;
    private final String name;
    private Integer score;

    public User(String name) {
        this.id = null;
        this.name = name;
        this.score = 1500;
    }

    public User(Long id, User other){
        this.id = id;
        this.name = other.name;
        this.score = other.score;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public Long getId() {
        return id;
    }

    public void setScore(Integer score){
        this.score = score;
    }

    @Override
    public String toString() {
        return "User [id=" + id + "]";
    }  
}
class SortByScore implements Comparator<User>{
    
    //Sort in ascending Order
    @Override
    public int compare(User arg0, User arg1) {
        
        return arg0.getScore() - arg1.getScore();
    }

    

}