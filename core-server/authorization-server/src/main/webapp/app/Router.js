// @flow
'use strict';

import React, {Component} from 'react';

import {Route, Switch, HashRouter} from 'react-router-dom';

import App from './container/app';
import Profile from './container/app/Profile';

class Router extends Component<Router.propTypes> {
  render() {
    return (
      <HashRouter>
        <Switch>
          <App>
            <Route path='/' component={Profile}/>
          </App>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;