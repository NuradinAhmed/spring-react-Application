package io.springboot.ipldashboard.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.springboot.ipldashboard.model.Match;


public interface MatchRepository extends CrudRepository <Match, Long> {
    

    //Method to return the type of data - in this case, mathces per team name 
    //Power of JPA is like query - i can query two things by using OR to match whichever paramters being passed 
    //I can query more //this allows me to write sql queries in method names - I can do order too by field
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2);
}