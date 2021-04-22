package io.springboot.ipldashboard.model;

import javax.persistence.Entity;
import javax.persistence.Id;

//creating a table for teams - to do that just add an entity to the class and jpa would create the table for you
@Entity
public class Team { //now we have a team instance 

    @Id //Specifies the primary key of an entity
    private long id;

    private String teamName;

    private long totalMatches;

    private long totalWins;


//  Getters and Setters for the field names// So, a setter is a method that updates value of a variable. And a getter is a method that reads value of a variable.
// since the above varialbes are private the only way to access and update is through the getter and setter method. 
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(long totalMatches) {
        this.totalMatches = totalMatches;
    }

    public long getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(long totalWins) {
        this.totalWins = totalWins;
    }

    
    
}