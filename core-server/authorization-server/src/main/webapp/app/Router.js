'use strict';

import React, {Component} from 'react';

import {Route, Switch, HashRouter} from 'react-router-dom';

import {anonymousUsersOnly, authenticatedUsersOnly} from './utils/authUtils';

import App from './container/app';
import Signin from './container/app/Signin';
import Signup from './container/app/Signup';
import Select from './container/app/Select';

class Router extends Component {
  render() {
    return (
      <HashRouter>
        <Switch>
          <App>
            <Route exact path="/" component={anonymousUsersOnly(Signin)}/>
            <Route path="/signup" component={anonymousUsersOnly(Signup)}/>
            <Route path="/select" component={authenticatedUsersOnly(Select)}/>
          </App>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;