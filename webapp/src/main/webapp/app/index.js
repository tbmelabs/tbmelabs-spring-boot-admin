'use strict';

import React from 'react';
import {render} from 'react-dom';

import {createStore, applyMiddleware, compose} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import reducers from './reducers';

import axios from 'axios';
import setAuthorizationToken from './utils/setAuthorizationToken';
import {setCurrentUser, logout} from './actions/authActions';

import {HashRouter} from 'react-router-dom';
import Router from './Router';

const store = createStore(
  reducers,
  compose(
    applyMiddleware(thunk),
    window.devToolsExtension ? window.devToolsExtension() : f => f
  )
);

const renderApplication = () => {
  render(
    <Provider store={store}>
      <HashRouter >
        <Router/>
      </HashRouter>
    </Provider>, document.getElementById('app'));
}

setAuthorizationToken(localStorage.auth_token);
if (localStorage.auth_token) {
  axios.get('/profile').then(
    response => store.dispatch(setCurrentUser(response.data)).then(renderApplication)
  ).catch(
    error => store.dispatch(logout()).then(renderApplication)
  );
} else {
  renderApplication();
}