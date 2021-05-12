import {React, useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';

export const MatchPage = () => {
    

    //going to define a state for the match API call: am saying my state = matches and the method calling to that state = setMatches
    const [matches, setMatches] = useState([]);

    const teamName = "Delhi Capitals";


    useEffect(
      () => {
        //asych/await systnx fits great with fetch() b/c it simplifies the work with promise
        const fetchMatches = async () => {
          const response = await fetch(`http://localhost:8080/team/{teamName}/matches?year=2019`);
          const data = await response.json();
          setMatches(data);
        };

        fetchMatches();

      },[]
    )



    return (
    <div className="MatchPage">

        <h1>Match Page</h1> 

        {
          matches.map(match => <MatchDetailCard teamName = {teamName} match = {match} />)
        }

        
    
    </div>
  );
}



