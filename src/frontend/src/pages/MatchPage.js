import {React, useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';

export const MatchPage = () => {
    

    //going to define a state for the match API call: am saying my state = matches and the method calling to that state = setMatches
    const [matches, setMatches] = useState([]);



    const {teamName, year } = useParams();



    useEffect(
      () => {
        //asych/await systnx fits great with fetch() b/c it simplifies the work with promise
        const fetchMatches = async () => {

          const response = await fetch(`http://localhost:8080/team/${teamName}/matches?year=${year}`); //to read the param add $ to be read

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



