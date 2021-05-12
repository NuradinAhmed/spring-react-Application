import './App.css';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import { TeamPage } from './pages/TeamPage';
import { MatchPage } from './pages/MatchPage';

function App() {
  return (
    <div className="App">

      {/* This is our RouterTag that will allows us to define route that will contain a path */}
      <Router>

        <Switch>

            <Route path="/teams/:teamName/matches/:year">
              < MatchPage/>

            </Route>

            <Route path="/teams/:teamName">
              <TeamPage/>
            </Route>

        </Switch>

      </Router>

      

    </div>
  );
}

export default App;
