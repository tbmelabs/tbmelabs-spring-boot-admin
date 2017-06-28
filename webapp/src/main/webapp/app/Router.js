'use strict';

import React from 'react';

import {Route, Switch} from 'react-router-dom';

import App from './components/App';
import Login from './containers/login/LoginForm';
import Register from './containers/registration/RegistrationForm';
import Home from './components/Home';

class Router extends React.Component {
  render() {
    return (
      <Switch>
        <Route path="/" component={App}>
          <Route exact path='/' component={Home}/>
          <Route path="login" component={Login}/>
          <Route path="register" component={Register}/>
        </Route>
      </Switch>
    );
  }
}

export default Router;