package io.springboot.ipldashboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.springboot.ipldashboard.model.Team;
import io.springboot.ipldashboard.repository.TeamRepository;

/*
    1. Firts API: this API takes teamName and returns team information. We have team table - looks it up and returns data
        A: its a restController - will contain one method
*/
@RestController
public class TeamController {

    private TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    //this method will be mapped to the url based on the API endPoint [teams/<teamName>]
    @GetMapping("/team/{teamName}") //map is paramterized!
    public Team getTeam(@PathVariable String teamName){ // and will need to annotate with PathVariable; meaning whatever is in the mapping teamName will need to be passed to the paramter teamName

        //eventaully will return team istance 
        return this.teamRepository.findByTeamName(teamName);
    }
    
}