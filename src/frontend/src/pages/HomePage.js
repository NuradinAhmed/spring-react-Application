import {React, useEffect, useState} from 'react';

import './HomePage.scss';
import { TeamTile } from '../components/TeamTile';




export const HomePage = () => {
    /*
        to maake the data available - we need to make the data stateful by using the state hook in react.
        jsx can refer to the state and show that data.
        Returns a stateful value, and a function to update it.
        During the initial render, the returned state (state) is the same as the value passed as the first argument (initialState).
    */
        //creating a state, teams is state and setTeams is a way to populate that state
    const [teams, setTeams] = useState([]); //the default will be an array for 


    /*By using this Hook -useEffect, we tell React that our component needs to do something after render. 
        React will remember the function we passed (we’ll refer to it as our “effect”), and call it later after performing the DOM updates. 
        In this effect, we are performing data fetching or call some other imperative API.
    */
    useEffect(
        () => {
            //async/await syntax fits great with fetch() because it simplifies the work with promises.
            const fetchAllTeams = async () => { //2. I want fetchMatches to be called-
                const response = await fetch(`${process.env.REACT_APP_API_ROOT_URL}/team`); //3.And the fetchMatches is making call to hardcoded uri 
                const data = await response.json();
                //console.log(data);
                setTeams(data); //4. the response is being set to this state setTeam.

            };
            fetchAllTeams();
            
            //1. when the component loads which is this empty array 
        },[] //this is referd to as dependcy list: basically its an empty array and tells to only return first page when you load rather than 
            //rather than doing an infinit loop! 
    )

 

        //5. In my jsx, in this html, I am using team.teamName which is and rendering 
            //matchdetailcard -the first of the matches in the team object 

            //slice: slice(start?: number, end?: number): any[] The beginning of the specified portion of the array. Returns a section of an array.

    return (
    <div className="HomePage">

        <div className="header-section">

            <h1 className="app-name">Nuradin Enterprise IPL Dashboard</h1> 

        </div>
        
        
        <div className="team-grid"> 
            {teams.map(team => <TeamTile key={team.id} teamName= {team.teamName} /> )}
        </div>

    </div>
  );
}


