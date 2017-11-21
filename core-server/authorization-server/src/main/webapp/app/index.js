'use strict';

import React from 'react';
import {render} from 'react-dom';

import {createStore, applyMiddleware, compose} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import reducers from './reducers';

import {CookiesProvider} from 'react-cookie';

import {Route, Switch, HashRouter} from 'react-router-dom';

import {anonymousUsersOnly, authenticatedUsersOnly} from './utils/authUtils';

import Login from './container/Login';
import Select from './container/Select';

const store = createStore(
  reducers,
  compose(
    applyMiddleware(thunk),
    window.devToolsExtension ? window.devToolsExtension() : f => f
  )
);

require('./styles/tbme-tv.css');

class App extends React.Component {
  render() {
    return (
      <Provider store={store}>
        <CookiesProvider>
          <HashRouter>
            <Switch>
              <Route exact path="/" component={anonymousUsersOnly(Login)}/>
              <Route path="/select" component={authenticatedUsersOnly(Select)}/>
            </Switch>
          </HashRouter>
        </CookiesProvider>
      </Provider>
    );
  }
}

render(<App/>, document.getElementById('app'));