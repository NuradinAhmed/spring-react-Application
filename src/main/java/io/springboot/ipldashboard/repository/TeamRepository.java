package io.springboot.ipldashboard.repository;

import org.springframework.data.repository.CrudRepository;

import io.springboot.ipldashboard.model.Team;

/*
    This is an interface that takes in two generic types <Team>, the thing we are fetching and an Id <long> type
    CrudRepository allows 
*/
public interface TeamRepository extends CrudRepository<Team, Long> {

    //Declaring An interface method: how does work ? spring jpa will look into the method to find out 
        //what the implemenation look like - it will ingore the findBy and will query TeamName and return an instance of Team
    //Spring JPA will query the Team instance and return 
    Team findByTeamName(String teamName);
    
}