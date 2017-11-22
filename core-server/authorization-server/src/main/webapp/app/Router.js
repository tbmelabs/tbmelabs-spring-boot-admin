'use strict';

import React, {Component} from 'react';

import {Route, Switch, HashRouter} from 'react-router-dom';

import {anonymousUsersOnly, authenticatedUsersOnly} from './utils/authUtils';

import Signin from './container/Signin';
import Signup from './container/Signup';
import Select from './container/Select';

class Router extends Component {
  render() {
    return (
      <HashRouter>
        <Switch>
          <Route exact path="/" component={anonymousUsersOnly(Signin)}/>
          <Route exact path="/signup" component={anonymousUsersOnly(Signup)}/>
          <Route path="/select" component={authenticatedUsersOnly(Select)}/>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;