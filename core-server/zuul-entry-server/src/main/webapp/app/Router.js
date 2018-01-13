'use strict';

import React, {Component} from 'react';

import {Route, Switch, HashRouter} from 'react-router-dom';

import App from './containers/app';

class Router extends Component {
  render() {
    return (
      <HashRouter>
        <Switch>
          <App>
          </App>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;