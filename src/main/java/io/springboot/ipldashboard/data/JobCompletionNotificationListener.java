package io.springboot.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.springboot.ipldashboard.model.Team;




/*
    The last bit of batch configuration is a way to get notified when the job completes
*/
    @Component
    public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

        private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

        //private final JdbcTemplate jdbcTemplate;
        private final EntityManager em;  //EntityManager is the JPA way of interacting with Database - NO need for the jdbctemplate above

        @Autowired //Autowiring the Entitymanager here istead of the jdbctemplate that were being used erlier
        public JobCompletionNotificationListener(EntityManager em) {
            this.em = em;
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("!!! JOB FINISHED! Time to verify the results");

            //     jdbcTemplate.query("SELECT team1, team2, date FROM match",
            //             (rs, row) -> "Team 1" + rs.getString(1) +  "Team 2" + rs.getString(2) + " Date" + rs.getString(3)
            //   //  ).forEach(str -> log.info("Found <" + person + "> in the database.")); //this will log into logfacotry but dont need it now.
            //     ).forEach(str -> System.out.println(str)); //just print it!
            // ---- the above works fine for testin ---
            // Create a HashMap to map distinct teamNames to team instances. 

                Map<String, Team> teamData = new HashMap<>();

            }
        }
    }


