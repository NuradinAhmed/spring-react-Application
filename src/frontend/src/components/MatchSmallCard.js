import {React} from 'react';

import './MatchSmallCard.scss';

import { Link } from 'react-router-dom'; {/* Link allows us to click teamName and gets its detials card match */}


//Accepting the match as a paramter here in the matchsmallcard
export const MatchSmallCard = ({match, teamName}) => {
  
  if(!match) return null;
  {/* apply same logic for getting only other team rather than bothteams*/}
  const otherTeam = match.team1 === teamName ? match.team2 : match.team1;

  const otherTaamRoute = `/teams/${otherTeam}`;

  const isMatchWon = teamName === match.matchWinner;

  return (
    <div className={isMatchWon ? 'MatchSmallCard won-card' : 'MatchSmallCard lost-card'}>

      <h3> vs <Link to={otherTaamRoute}>  {otherTeam}    </Link>   </h3>

      <p> {match.matchWinner} won by {match.resultMargin} {match.result}</p>

    </div>
  );
}