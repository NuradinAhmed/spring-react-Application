import {React, useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';


import './MatchPage.scss';
import { YearSelector } from '../components/YearSelector';


export const MatchPage = () => {
    

    //going to define a state for the match API call: am saying my state = matches and the method calling to that state = setMatches
    const [matches, setMatches] = useState([]);



    const {teamName, year } = useParams();



    useEffect(
      () => {
        //asych/await systnx fits great with fetch() b/c it simplifies the work with promise
        const fetchMatches = async () => {

          const response = await fetch(`${process.env.REACT_APP_API_ROOT_URL}/team/${teamName}/matches?year=${year}`); //to read the param add $ to be read

          const data = await response.json();
          setMatches(data);
        };

        fetchMatches();

      },[teamName, year] //when either of them change this has to update and make a call for the list year selector
    )



    return (
    <div className="MatchPage">

      <div className="YearSelector">
          <h3> Select Year </h3>
          <YearSelector teamName= {teamName}/>
      </div>


      {/* Create a div here to move the whole matches as one large column together to the right side.  */}
      <div> 

      <h1 className="page-heading">{teamName} matches in {year}</h1> 


        {
          matches.map(match => <MatchDetailCard key={match.id}   teamName = {teamName} match = {match} />)
        }

      </div>
    
    </div>
  );
}



