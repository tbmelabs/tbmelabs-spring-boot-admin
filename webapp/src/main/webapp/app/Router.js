'use strict';

import React from 'react';

import {Route, Switch} from 'react-router-dom';

import App from './containers/App';
import Login from './containers/login/index';
import Register from './containers/registration/index';
import Home from './components/Home';

const Router = () => {
  return (
    <App>
      <Switch>
        <Route exact path='/' component={Home}/>
        <Route path="/login" component={Login}/>
        <Route path="/register" component={Register}/>
      </Switch>
    </App>
  );
}

export default Router;