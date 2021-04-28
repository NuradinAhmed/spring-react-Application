package io.springboot.ipldashboard.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

//creating a table for teams - to do that just add an entity to the class and jpa would create the table for you
@Entity
public class Team { //now we have a team instance 

    @Id //Specifies the primary key of an entity
    /*
        ERROR: javax.persistence.EntityExistsException: A different object with the same identifier value was already associated with the session : [io.springboot.ipldashboard.model.Team#0]
        We need to tell spring boot to create its own identifiers and to do that ...
    */
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; // telling jpa this id is generated value as identifier. Just Auto generate the identifier

    private String teamName;

    private long totalMatches;

    private long totalWins;


    //Want to return list of matches based on the API to get top team matches 
    //Make this transient - 
    @Transient //Specifies that the property or field is not persistent. It is used to annotate a property or field of an entity class, mapped superclass, or embeddable class.
    private List<Match> matches;

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



    //Create an Constructor for teamName and totalMatches
    public Team(String teamName, long totalMatches) {
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }



    // to verify create toString method that returns 
    @Override
    public String toString() {
        return "Team [teamName=" + teamName + ", totalMatches=" + totalMatches + ", totalWins=" + totalWins + "]";
    }



      //Here we need a default constructor per jpa does not create 
      //No default constructor for entity: : io.springboot.ipldashboard.model.Team;
    public Team() {
    }



    //Getters and Setters for matches transient - dont want to persist in the database. 
    public List<Match> getMatches() {
        return matches;
    }

  

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
    
    
    
    
}