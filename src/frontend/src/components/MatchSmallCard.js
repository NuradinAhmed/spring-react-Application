import {React} from 'react';


//Accepting the match as a paramter here in the matchsmallcard
export const MatchSmallCard = ({match}) => {
  return (
    <div className="MatchSmallCard">

      <p>{match.team1} vs {match.team2}</p>

    </div>
  );
}