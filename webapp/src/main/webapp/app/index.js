'use strict';

import React from 'react';
import {render} from 'react-dom';

import {createStore, applyMiddleware, compose} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import reducers from './reducers';

import setAuthorizationToken from './utils/setAuthorizationToken';

import jwtDecode from 'jwt-decode';
import {setCurrentUser} from './actions/authActions';

import {HashRouter} from 'react-router-dom';

import Router from './Router';

const store = createStore(
  reducers,
  compose(
    applyMiddleware(thunk),
    window.devToolsExtension ? window.devToolsExtension() : f => f
  )
);

if (localStorage.auth_token) {
  setAuthorizationToken(localStorage.auth_token);
  store.dispatch(setCurrentUser(jwtDecode(localStorage.auth_token)));
}

render(
  <Provider store={store}>
    <HashRouter >
      <Router/>
    </HashRouter>
  </Provider>, document.getElementById('app')
);