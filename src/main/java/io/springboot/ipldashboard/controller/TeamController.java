package io.springboot.ipldashboard.controller;


import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.springboot.ipldashboard.model.Team;
import io.springboot.ipldashboard.repository.MatchRepository;
import io.springboot.ipldashboard.repository.TeamRepository;

/*
    1. Firts API: this API takes teamName and returns team information. We have team table - looks it up and returns data
        A: its a restController - will contain one method
*/
@RestController
@CrossOrigin //Annotation for permitting cross-origin requests on specific handler classes and/or handler methods. Processed if an appropriate HandlerMapping is configured
public class TeamController {

    //Injection of dependencies 
    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    //Dependecy injection: public constructor 
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }
    

    //this method will be mapped to the url based on the API endPoint [teams/<teamName>]
    @GetMapping("/team/{teamName}") //map is paramterized!
    public Team getTeam(@PathVariable String teamName){ // and will need to annotate with PathVariable; meaning whatever is in the mapping teamName will need to be passed to the paramter teamName

        //eventaully will return team istance 
       //return this.teamRepository.findByTeamName(teamName);
       
        Team team =  this.teamRepository.findByTeamName(teamName);
        //In the teamController - Iam going to specify the options return top 4 since they've already been order by date
       // Pageable pageable = PageRequest.of(0, 4); //kCreates a new unsorted PageRequest.Parameters:page zero-based page index, must not be negative.size the size of the page to be returned, must be greater than 0.
       //here now get me the teamName and last 4matches 
       team.setMatches(matchRepository.findLatestMatchesbyTeam(teamName, 4));




        return team;
       
    }



  
    
}