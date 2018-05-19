// @flow
'use strict';

import React, {Component} from 'react';

import {Route} from 'react-router';
import {HashRouter, Switch} from 'react-router-dom';

import Loadable from 'react-loadable';

const AsyncApp = Loadable({
  loader: () => import('./container/app'),
  loading: () => <div>Loading...</div>
});

class Router extends Component<Router.propTypes> {
  render() {
    return (
        <HashRouter>
          <Switch>
            <Route path='/' component={AsyncApp}/>
          </Switch>
        </HashRouter>
    );
  }
}

export default Router;
