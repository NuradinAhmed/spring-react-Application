
import { React } from 'react';

import {Link} from 'react-router-dom';

import './YearSelector.scss';

export const YearSelector = ({teamName}) => {

    let years = []

    const startYear = process.env.REACT_APP_DATA_START_YEAR;
    const endYear = process.env.REACT_APP_DATA_END_YEAR;

    for (let i = startYear; i <= endYear; i++) {
        years.push(i);  //Appends new elements to an array, and returns the new length of the array.
    }

    //returning a list of the year and to do that we will an html list - semantic list that is clickable from the team name
    return (
        <ol className="YearSelector">
            { years.map(year => (
                <li>

                  <Link to={`/teams/${teamName}/matches/${year}`}> {year} </Link>  
                
                
                </li>

            )) }
        </ol>
    )

}