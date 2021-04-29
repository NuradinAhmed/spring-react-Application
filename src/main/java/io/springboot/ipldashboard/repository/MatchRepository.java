package io.springboot.ipldashboard.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import io.springboot.ipldashboard.model.Match;


public interface MatchRepository extends CrudRepository <Match, Long> {
    

    //Method to return the type of data - in this case, mathces per team name 
    //Power of JPA is like query - i can query two things by using OR to match whichever paramters being passed 
    //I can query more //this allows me to write sql queries in method names - I can do order too by field
    //There is also a way to return the top 4 or something: 1. by jpql query or pageable where you will pass as parameter and have it return the last or so pages 
                //Abstract interface for pagination information - it allows returning page by page so we have to choose which page or how many to return
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);


    //In the MatchRepository, Java allows us to create an implementation of default method in an interface
    //Iam going to specify the options return top 4 since they've already been order by date

    default List<Match> findLatestMatchesbyTeam(String teamName, int count) {

        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count)); ////kCreates a new unsorted PageRequest.Parameters:page zero-based page index, must not be negative.size the size of the page to be returned, must be greater than 0.

    }
}