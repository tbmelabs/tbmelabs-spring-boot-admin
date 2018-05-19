// @flow
'use strict';

import React, {Component} from 'react';

import {HashRouter, Route, Switch} from 'react-router-dom';

import Loadable from 'react-loadable';

import accessWithAuthority from './utils/auth/accessWithAuthority';

import {
  SERVER_ADMIN,
  SERVER_SUPPORT
} from '../common/contants/AuthorityConstants';

const AsyncApp = Loadable({
  loader: () => import('./container/app'),
  loading: () => <div>Loading...</div>
});

const AsyncClients = Loadable({
  loader: () => import('./container/app/clients'),
  loading: () => <div>Loading...</div>
});

const AsyncClientDialog = Loadable({
  loader: () => import ('./container/app/clients/ClientDialog'),
  loading: () => <div>Loading...</div>
});

const AsyncProfile = Loadable({
  loader: () => import('./container/app/profile'),
  loading: () => <div>Loading...</div>
});

const AsyncUsers = Loadable({
  loader: () => import('./container/app/users'),
  loading: () => <div>Loading...</div>
});

class Router extends Component<Router.propTypes> {
  render() {
    return (
        <HashRouter>
          <Switch>
            <AsyncApp>
              <Route path='/clients'
                     component={accessWithAuthority(AsyncClients,
                         SERVER_ADMIN)}/>
              <Route path='/clients/new'
                     component={accessWithAuthority(AsyncClientDialog,
                         SERVER_ADMIN)}/>
              <Route path='/profile' component={AsyncProfile}/>
              <Route path='/users' component={accessWithAuthority(AsyncUsers,
                  SERVER_SUPPORT)}/>
            </AsyncApp>
          </Switch>
        </HashRouter>
    );
  }
}

export default Router;
