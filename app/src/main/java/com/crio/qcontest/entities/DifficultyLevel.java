package com.crio.qcontest.entities;

public enum DifficultyLevel {
    LOW(50),MEDIUM(30),HIGH(0);

    private final int weight;
    private DifficultyLevel(int weight){
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
     } 
}
