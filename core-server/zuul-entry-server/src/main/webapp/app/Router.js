// @flow
'use strict';

import React, {Component} from 'react';

import {HashRouter, Switch} from 'react-router-dom';
import PreConditionalRoute from './utils/PreConditionalRoute';

import Loadable from 'react-loadable';

import {requestAuthentication} from './state/queries/authentication';

const AsyncApp = Loadable({
  loader: () => import('./container/app'),
  loading: () => <div>Loading...</div>
});

class Router extends Component<Router.propTypes> {
  render() {
    return (
        <HashRouter>
          <Switch>
            <PreConditionalRoute path='/' component={AsyncApp}
                                 onEnter={requestAuthentication}/>
          </Switch>
        </HashRouter>
    );
  }
}

export default Router;
