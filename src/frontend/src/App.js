import './App.css';

import {BrowserRouter as Router, Route} from 'react-router-dom';


import { TeamPage } from './pages/TeamPage';

function App() {
  return (
    <div className="App">

      {/* This is our RouterTag that will allows us to define route that will contain a path */}
      <Router>

        <Route path="/teams/:teamName">
          <TeamPage/>
        </Route>
        
      </Router>

      <TeamPage/>

    </div>
  );
}

export default App;
