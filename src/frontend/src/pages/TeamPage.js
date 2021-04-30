import {React, useEffect, useState} from 'react';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';

export const TeamPage = () => {
    /*
        to maake the data available - we need to make the data stateful by using the state hook in react.
        jsx can refer to the state and show that data.
        Returns a stateful value, and a function to update it.
        During the initial render, the returned state (state) is the same as the value passed as the first argument (initialState).
    */
        //creating a state, team is state and setTeam is a way to populate that state
    const [team, setTeam] = useState({matches: []});


    /*By using this Hook -useEffect, we tell React that our component needs to do something after render. 
        React will remember the function we passed (we’ll refer to it as our “effect”), and call it later after performing the DOM updates. 
        In this effect, we are performing data fetching or call some other imperative API.
    */
    useEffect(
        () => {
            //async/await syntax fits great with fetch() because it simplifies the work with promises.
            const fetchMatches = async () => { //2. I want fetchMatches to be called-
                const response = await fetch("http://localhost:8080/team/Rajasthan%20Royals"); //3.And the fetchMatches is making call to hardcoded uri 
                const data = await response.json();
                //console.log(data);
                setTeam(data); //4. the response is being set to this state setTeam.

            };
            fetchMatches();
            
            //1. when the component loads which is this empty array 
        },[] //this is referd to as dependcy list: basically its an empty array and tells to only return first page when you load rather than 
            //rather than doing an infinit loop! 
    )



        //5. In my jsx, in this html, I am using team.teamName which is and rendering 
            //matchdetailcard -the first of the matches in the team object 

            //slice: slice(start?: number, end?: number): any[] The beginning of the specified portion of the array. Returns a section of an array.

  return (
    <div className="TeamPage">

        <h1>{team.teamName}</h1> 
        
        <MatchDetailCard match = {team.matches[0]}/> 

        {team.matches.slice(1).map(match => <MatchSmallCard match = {match} />)}      
      
    
    </div>
  );
}


