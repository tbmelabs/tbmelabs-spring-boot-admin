'use strict';

import React, {Component} from 'react';

import {Route, Switch, HashRouter} from 'react-router-dom';

import App from './container/app';
import Signin from './container/app/Signin';
import Signup from './container/app/Signup';

class Router extends Component {
  render() {
    return (
      <HashRouter>
        <Switch>
          <App>
            <Route exact path="/" component={Signin}/>
            <Route path="/signup" component={Signup}/>
          </App>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;