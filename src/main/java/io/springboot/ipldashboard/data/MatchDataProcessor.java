
/*
    Here we are going to write our processor class-
 */


package io.springboot.ipldashboard.data;

import io.springboot.ipldashboard.model.MatchOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;


//this our class MatchProcessor that implements it ItemProcessor from springframework itemProcessor. it take an iput and gives an output
    public class MatchDataProcessor implements ItemProcessor<MatchInput, MatchOutput> {


        private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);


        @Override //the process is going to take the matchinput as Paramater and going to return the MatchOutput data.
        public MatchOutput process(final MatchInput matchInput) throws Exception {


            //what does it need to do ? well it needs to look into the data values from the matchInput parameter and
                //create new MatchOutput values - which is what we want -
            //its going to populate our matchInput in the parameter into MatchOutput instance here. and return the MatchOutput

            MatchOutput MatchOutput = new MatchOutput();
            //1.we need to copy the values that are same from input to output
            //more like copy constructor except that we are tweaking little bit here!


            MatchOutput.setId(Long.parseLong(matchInput.getId()));
            MatchOutput.setCity(matchInput.getCity());


            MatchOutput.setDate(LocalDate.parse(matchInput.getDate()));

            MatchOutput.setPlayerOfMatch(matchInput.getPlayer_of_match());
            MatchOutput.setVenue(matchInput.getNeutral_venue());



            //set team 1 and team 2 depending their innings order -
            // apply some logic to it on whos 1st inning and 2nd innings using their toss-wing bat or field

            String firstInningsTeam, secondInningsTeam;

            /* 1. first, am going to check what the toss decision is ?

                if the toss-decision is bat, then first innings team is toss winner.
                if the toss-decision is field, then second innings team is toss winner.
             */
            if("bat".equals(matchInput.getToss_decision())) {
                firstInningsTeam = matchInput.getToss_winner();

                // if the toss-winner is equal to team1 then(?)the 2nd innings team is team2 else(:)
                secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                        ? matchInput.getTeam2() : matchInput.getTeam1();

            } else {
                secondInningsTeam = matchInput.getToss_winner();
                // if the toss-winner is equals to team1 then team2 else team team1
                firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                        ? matchInput.getTeam2() : matchInput.getTeam1();

            }


            MatchOutput.setTeam1(firstInningsTeam);
            MatchOutput.setTeam2(secondInningsTeam);


            MatchOutput.setTossWinner(matchInput.getToss_winner());
            MatchOutput.setTossDecision(matchInput.getToss_decision());
            MatchOutput.setResult(matchInput.getResult());
            MatchOutput.setResultMargin(matchInput.getResult_margin());
            MatchOutput.setUmpire1(matchInput.getUmpire1());
            MatchOutput.setUmpire2(matchInput.getUmpire2());


            return MatchOutput;

        }

    }







