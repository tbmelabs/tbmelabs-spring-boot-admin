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
  loader: () => import('./container/app/clients/ClientList.container'),
  loading: () => <div>Loading...</div>
});

const AsyncClientDetailsModal = Loadable({
  loader:()=>import('./container/app/clients/ClientDetails.container'),
  loading: () => <div>Loading...</div>
});

const AsyncClientEditModal = Loadable({
  loader: () => import ('./container/app/clients/EditClient.container'),
  loading: () => <div>Loading...</div>
});

const AsyncClientDeleteDialog = Loadable({
  loader: () => import ('./container/app/clients/DeleteClient.container'),
  loading: () => <div>Loading...</div>
})

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
                     component={accessWithAuthority(AsyncClientEditModal,
                         SERVER_ADMIN)}/>
              <Route exact path='/clients/:clientId'
                     component={accessWithAuthority(AsyncClientDetailsModal,
                         SERVER_ADMIN)}/>
              <Route path='/clients/:clientId/edit'
                     component={accessWithAuthority(AsyncClientEditModal,
                         SERVER_ADMIN)}/>
              <Route path='/clients/:clientId/delete'
                     component={accessWithAuthority(AsyncClientDeleteDialog,
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
