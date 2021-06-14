package io.springboot.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.springboot.ipldashboard.model.Team;




/*
    The last bit of batch configuration is a way to get notified when the job completes
*/
    @Component
    public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

        private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

        private final EntityManager em;  //EntityManager is the JPA way of interacting with Database - NO need for the jdbctemplate above

        @Autowired //Autowiring the Entitymanager here istead of the jdbctemplate that were being used erlier
        public JobCompletionNotificationListener(EntityManager em) {
            this.em = em;
        }
        /* you get below error when you dont set transactino b/c spring boot would want to set transaction when running shared entity manager
            to set it up in spring boot you annotate to start transaction and when the process ends it stops the transaction
            javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
        */
        @Override
        @Transactional
        public void afterJob(JobExecution jobExecution) {
            if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("!!! JOB FINISHED! Time to verify the results");

            //     jdbcTemplate.query("SELECT team1, team2, date FROM match",
            //             (rs, row) -> "Team 1" + rs.getString(1) +  "Team 2" + rs.getString(2) + " Date" + rs.getString(3)
            //   //  ).forEach(str -> log.info("Found <" + person + "> in the database.")); //this will log into logfacotry but dont need it now.
            //     ).forEach(str -> System.out.println(str)); //just print it!
            // ---- the above works fine for testin ---
            // Create a HashMap to map distinct teamNames to team instances. I need to get the unique team 1 and team 2 

           /* select distinct team1 from Match m union select distinct team2 from Match m 
                well union does not work in jpa so will have to do this twice! b/c distinct teams could be in team1 and team2 so need both
            */ //getting team1 and their count of matches 
            Map<String, Team> teamData = new HashMap<>();
                                                                                        //this is type of an object arry which is what am expecting for element 
                em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class ) //jpql allows me to specify query(allowing me to model object/class to infer what table you lookin for) using jpa query language whic is called jpql 
                    .getResultList() //Execute a SELECT query and return the query results as an untyped List.
                    .stream() //Returns a sequential Stream with this collection as its source.
                    .map(e -> new Team((String) e[0], (long)e[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));


                    //here for team2 
                    em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
                    });


                    // here is to get the occurance of MatchWinner which is counting the occurance of teamwinner in csv
                    // all the times a particular team shows in the matchWinner column and the count
                    em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        if(team != null) team.setTotalWins((long) e[1]); // we will add an if condition in case there is NA of team win cause theres not setting to a win for team when there is NA in that column and in our cvs it does exit 
                    });



                    //Now we persist all the team values gotten from each instance above
                    //we will get all the values which is team instance, for each value(which is team instance)
                        //am going to do em..persist of team and 
                    teamData.values().forEach(team -> em.persist(team));


                    // here i will do this to print out the result instead of persisting it like above for verification printout
                    teamData.values().forEach(team -> System.out.println(team));


            }
        }
    }


