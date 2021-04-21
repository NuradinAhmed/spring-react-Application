
/*
    Here we are going to write our processor class-
 */


package io.springboot.ipldashboard.data;

import io.springboot.ipldashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;


//this our class MatchProcessor that implements it ItemProcessor from springframework itemProcessor. it take an input and gives an output

    public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

        private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

        @Override//the process is going to take the matchinput as Paramater and going to return the MatchOutput data.
        public Match process(final MatchInput matchInput) throws Exception {

            Match match = new Match();
            match.setId(Long.parseLong(matchInput.getId()));
            match.setCity(matchInput.getCity());

            match.setDate(LocalDate.parse(matchInput.getDate()));

            match.setPlayerOfMatch(matchInput.getPlayer_of_match());
            match.setVenue(matchInput.getVenue());

            // Set Team 1 and Team 2 depending on the innings order
            String firstInningsTeam, secondInningsTeam;
             /* 1. first, am going to check what the toss decision is ?

                if the toss-decision is bat, then first innings team is toss winner.
                if the toss-decision is field, then second innings team is toss winner.
             */

            if ("bat".equals(matchInput.getToss_decision())) {
                // if the toss-winner is equal to team1 then(?)the 2nd innings team is team2 else(:)
                firstInningsTeam = matchInput.getToss_winner();
                secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                        ? matchInput.getTeam2() : matchInput.getTeam1();

            } else {
                secondInningsTeam = matchInput.getToss_winner();
                firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                        ? matchInput.getTeam2() : matchInput.getTeam1();
            }
            match.setTeam1(firstInningsTeam);
            match.setTeam2(secondInningsTeam);

            match.setTossWinner(matchInput.getToss_winner());
            match.setTossDecision(matchInput.getToss_decision());
            match.setMatchWinner(matchInput.getWinner());
            match.setResult(matchInput.getResult());
            match.setResultMargin(matchInput.getResult_margin());
            match.setUmpire1(matchInput.getUmpire1());
            match.setUmpire2(matchInput.getUmpire2());

            return match;
        }

    }









