package io.springboot.ipldashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.springboot.ipldashboard.model.Match;


public interface MatchRepository extends CrudRepository <Match, Long> {
    

    //Method to return the type of data - in this case, mathces per team name 
    //Power of JPA is like query - i can query two things by using OR to match whichever paramters being passed 
    //I can query more //this allows me to write sql queries in method names - I can do order too by field
    //There is also a way to return the top 4 or something: 1. by jpql query or pageable where you will pass as parameter and have it return the last or so pages 
                //Abstract interface for pagination information - it allows returning page by page so we have to choose which page or how many to return
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

            //here we are telling spring jpa that am providing the exact query am going to run in this query
    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :dateStart and :dateEnd order by date desc") //this is basically the same as above method except we are using jpa query annotations here 
    List<Match> getMatchesByTeamBetweenDates(
        @Param("teamName") String teamName,            //@Param: Annotation to bind method parameters to a query via a named parameter.
        @Param("dateStart") LocalDate dateStart , 
        @Param("dateEnd") LocalDate dateEnd
        
    );

    //geting match data between two dates instance of each teams due to order of operation precedence 
    //the below method isnt really clean so will use query annotations
    // List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
    //     String team1, LocalDate date1, LocalDate date2,
    //     String teamName2, LocalDate date3, LocalDate date4);






    //In the MatchRepository, Java allows us to create an implementation of default method in an interface
    //Iam going to specify the options return top 4 since they've already been order by date

    default List<Match> findLatestMatchesbyTeam(String teamName, int count) {

        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count)); ////kCreates a new unsorted PageRequest.Parameters:page zero-based page index, must not be negative.size the size of the page to be returned, must be greater than 0.

    }
}