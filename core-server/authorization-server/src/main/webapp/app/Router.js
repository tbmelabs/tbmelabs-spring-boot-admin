// @flow
'use strict';

import React, {Component} from 'react';

import {HashRouter, Route, Switch} from 'react-router-dom';

import accessWithAuthority from './utils/accessWithAuthority';

import {SERVER_ADMIN, SERVER_SUPPORT} from '../common/contants/AuthorityConstants';

import App from './container/app';
import Clients from './container/app/Clients';
import Profile from './container/app/Profile';
import Users from './container/app/Users';

class Router extends Component<Router.propTypes> {
  render() {
    return (
      <HashRouter>
        <Switch>
          <App>
            <Route path='/clients' component={accessWithAuthority(Clients, SERVER_ADMIN)}/>
            <Route path='/profile' component={Profile}/>
            <Route path='/users' component={accessWithAuthority(Users, SERVER_SUPPORT)}/>
          </App>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;