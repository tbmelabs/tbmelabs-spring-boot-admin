// @flow
'use strict';

import React, {Component} from 'react';

import {HashRouter, Route, Switch} from 'react-router-dom';
import PreConditionalRoute from './utils/PreConditionalRoute';

import {requestAuthentication} from "./queries/authentication";

import App from './container/app';

class Router extends Component<Router.propTypes> {
  render() {
    return (
      <HashRouter>
        <Switch>
          <PreConditionalRoute path='/' component={App} onEnter={requestAuthentication} />
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;