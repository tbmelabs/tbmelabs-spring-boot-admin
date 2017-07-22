'use strict';

import React from 'react';

import {Route, Switch} from 'react-router-dom';

import App from './containers/App';
import Login from './containers/login/index';
import Register from './containers/registration/index';
import LandingPage from './components/LandingPage';
import Account from './containers/account';
import Administration from './containers/administration';

import requireAuth from './utils/security/requireAuth';
import {ROLE_CONTENT_ADMIN} from './utils/security/roles';
import requireRole from './utils/security/requireRole';

const Router = () => {
  return (
    <App>
      <Switch>
        <Route exact path='/' component={LandingPage}/>
        <Route path='/login' component={Login}/>
        <Route path='/register' component={Register}/>
        <Route path='/account' component={requireAuth(Account)}/>
        <Route path='/admin' component={requireRole(Administration, ROLE_CONTENT_ADMIN)}/>
      </Switch>
    </App>
  );
}

export default Router;